package com.av.ibnammy.homePage.map;

import android.widget.AdapterView;

import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.viewprofile.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maiada on 6/14/2018.
 */

public class MapSearchModel {

    protected    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    //Get ProfileData
    protected  void ProfileData(final String phoneNumber, String password, final GetCallback.ProfileData profileDataCallBack){

        String data="{'Mobile':"+phoneNumber+",'Password':'"+password+"'}";
        final Call<String> profileDataCall  = apiInterface.getUserData(data);
        profileDataCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject jsonObject=new JSONObject(response.body());
                        Profile profile = new Profile();

                        if(!jsonObject.getString("AccountID").equals("null"))
                            profile.setAccountId(jsonObject.getString("AccountID"));

                        if(!jsonObject.getString("First_Name").equals("null")||
                                !jsonObject.getString("Second_Name").equals("null")
                                ){
                            profile.setUserName(jsonObject.getString("First_Name")
                                    +jsonObject.getString("Second_Name"));

                            profile.setFullUserName(jsonObject.getString("First_Name")+" "
                                    +jsonObject.getString("Second_Name")+" "+jsonObject.getString("Third_Name")+" "
                                    +jsonObject.getString("Forth_Name"));
                        }else {
                            profile.setUserName("لا يوجد");
                        }


                        String imageName=jsonObject.getString("Profile_IMG");
                        if(!imageName.equals("null")){
                            profile.setProfileImage(imageName);
                        }

                        if(!jsonObject.getString("Home_Latitude").equals("null"))
                            profile.setHomeLatitude(jsonObject.getString("Home_Latitude"));

                        if(!jsonObject.getString("Home_Longitude").equals("null"))
                            profile.setHomeLongitude(jsonObject.getString("Home_Longitude"));


                        profileDataCallBack.onProfileDataSuccess(profile);


                       /* if(profile.getHomeLongitude().equals("")&&profile.getHomeLongitude().equals("")){
                            profileDataCallBack.onProfileDataError();

                        }else {
                            profileDataCallBack.onProfileDataSuccess(profile);

                        }*/


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                profileDataCallBack.onProfileDataFailure();

            }
        });

    }

    //Get ServiceCategorySearch
    protected  void ServiceCategorySearch(final GetCallback.DDServiceCategorySearch categorySearchCallBack){

        Call<String> serviceCategorySearchCall  = apiInterface.DDServiceCategorySearch();
        serviceCategorySearchCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        ArrayList<ServiceCategory> categoryArrayList = new ArrayList<>();
                        categoryArrayList.add(new ServiceCategory("البحث عن  "));
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String serviceCategoryID = jsonObject.getString("Service_CategoryID").replace("\r\n","");
                            String name = jsonObject.getString("Name").replace("\r\n","");
                            ServiceCategory serviceCategory = new ServiceCategory(serviceCategoryID,name);
                            categoryArrayList.add(serviceCategory);
                        }

                        categorySearchCallBack.onDDServiceCategorySearchSuccess(categoryArrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                categorySearchCallBack.onDDServiceCategorySearchFailure();
            }
        });

    }

    //Get All Country
    protected void  DDCountry(final  GetCallback.DDCountry ddCountry){
        Call<String> ddCountryCall  = apiInterface.DDCountry();

        ddCountryCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        ArrayList<String> countryList = new ArrayList<>();
                        countryList.add("الدولة");
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i=0;i<jsonArray.length();i++){
                            countryList.add(jsonArray.get(i).toString());
                        }
                        ddCountry.onDDCountrySuccess(countryList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ddCountry.onDDCountryFailure();

            }
        });

    }

    //Get District of country
    protected void  DistrictsOfCountry(final GetCallback.DistrictsCountry districtsCountry, String country){
        Call<String> districtsOfCountryCall  = apiInterface.districtsOfCountry(country);

        districtsOfCountryCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {

                        ArrayList<String> districtsList = new ArrayList<>();
                        districtsList.add("المدينه");
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i=0;i<jsonArray.length();i++){
                            districtsList.add(jsonArray.get(i).toString());
                        }
                        districtsCountry.onDistrictsCountrySuccess(districtsList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    //Get ServiceCategorySearch
    protected  void ServiceTypeSearch(final GetCallback.DDServiceTypeSearch serviceTypesFetched){

        Call<String> serviceTypesApiCall  = apiInterface.getServiceTypesApi();
        serviceTypesApiCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        ArrayList<ServiceTypeSearch> serviceTypeArrayList = new ArrayList<>();
                        serviceTypeArrayList.add(new ServiceTypeSearch("التخصص"));
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String serviceTypeID = jsonObject.getString("Service_TypeID").replace("\r\n","");
                            String name = jsonObject.getString("Name").replace("\r\n","");
                            ServiceTypeSearch serviceType = new ServiceTypeSearch(serviceTypeID,name);
                            serviceTypeArrayList.add(serviceType);
                        }

                        serviceTypesFetched.onTypesFetchSuccess(serviceTypeArrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                serviceTypesFetched.onTypesFetchFailure();
            }
        });

    }


    //Get All Search result account
    protected  void SearchResultAccounts(final GetCallback.AllSearchResult searchResultCallBack,String searchRequest){

        final Call<String> searchResultCall  = apiInterface.searchResultAccounts(searchRequest);
        searchResultCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {


                    try {

                        String json= response.body();
                        if(json.startsWith("{")){
                            JSONObject jsonObject = new JSONObject(json);
                            if(jsonObject.has("Status"))
                                searchResultCallBack.onSearchResultFailure();
                        }else {
                            JSONArray jsonArray = new JSONArray(response.body());
                            ArrayList<CousinAccount> searchResultArrayList = new ArrayList<>();

                            for(int i=0;i<jsonArray.length();i++){
                                CousinAccount cousinAccount = new CousinAccount();
                                JSONObject accountObject = jsonArray.getJSONObject(i);

                                if(!accountObject.getString("AccountID").equals("null"))
                                    cousinAccount.setCousinId(accountObject.getString("AccountID").replace("\r\n",""));

                                if(!accountObject.getString("First_Name").equals("")&&
                                        !accountObject.getString("Second_Name").equals("")){

                                    cousinAccount.setCousinName(accountObject.getString("First_Name").replaceAll("\n","")
                                            .concat(" "+accountObject.getString("Second_Name").replaceAll("\n","")));
                                }

                                if(!accountObject.getString("Service_Type").equals("null"))
                                    cousinAccount.setCousinJob(accountObject.getString("Service_Type").replace("\r\n",""));

                                String getProfile_IMG = accountObject.getString("Profile_IMG");
                                if(!getProfile_IMG.equals("null"))
                                    cousinAccount.setCousinImage(getProfile_IMG);


                                if(!accountObject.getString("BirthDate").equals("null"))
                                    cousinAccount.setBirthDate(accountObject.getString("BirthDate"));

                                if(!accountObject.getString("Marital_Status").equals("null"))
                                    cousinAccount.setCousinMaritalStatus(accountObject.getString("Marital_Status").replace("\r\n",""));

                                if(!accountObject.getString("Home_Latitude").equals("null"))
                                    cousinAccount.setHomeLatitude(accountObject.getString("Home_Latitude"));

                                if(!accountObject.getString("Home_Longitude").equals("null"))
                                    cousinAccount.setHomeLongitude(accountObject.getString("Home_Longitude"));


                                if(!accountObject.getString("Home_Street").equals("null"))
                                    cousinAccount.setHomeStreet(accountObject.getString("Home_Street"));


                                if(!accountObject.getString("Home_City").equals("null"))
                                    cousinAccount.setHomeCity(accountObject.getString("Home_City"));


                                if(!accountObject.getString("Home_District").equals("null"))
                                    cousinAccount.setHomeDistrict(accountObject.getString("Home_District"));


                                if(!accountObject.getString("Home_Country").equals("null"))
                                    cousinAccount.setHomeCountry(accountObject.getString("Home_Country"));

                                if(!accountObject.getString("Service_Description").equals("null"))
                                    cousinAccount.setServiceDescription(accountObject.getString("Service_Description"));

                                if(!accountObject.getString("Price").equals("null"))
                                    cousinAccount.setPrice(accountObject.getString("Price"));

                                if(!accountObject.getString("Discount").equals("null"))
                                    cousinAccount.setDiscount(accountObject.getString("Discount"));

                                String getWork_IMG1 = accountObject.getString("Work_IMG1");
                                if(!getWork_IMG1.equals("null"))
                                    cousinAccount.setWork_IMG1(getWork_IMG1);

                                String getWork_IMG2 = accountObject.getString("Work_IMG2");
                                if(!getWork_IMG2.equals("null"))
                                    cousinAccount.setWork_IMG2(getWork_IMG2);


                                String getWork_IMG3 = accountObject.getString("Work_IMG3");
                                if(!getWork_IMG3.equals("null"))
                                    cousinAccount.setWork_IMG3(getWork_IMG3);

                                String getWork_IMG4 = accountObject.getString("Work_IMG4");
                                if(!getWork_IMG4.equals("null"))
                                    cousinAccount.setWork_IMG4(getWork_IMG4);

                                String getWork_IMG5 = accountObject.getString("Work_IMG5");
                                if(!getWork_IMG5.equals("null"))
                                    cousinAccount.setWork_IMG5(getWork_IMG5);

                                String getWork_Video = accountObject.getString("Work_Video");
                                if(!getWork_Video.equals("null"))
                                    cousinAccount.setWork_Video(getWork_Video);

                                if(!accountObject.getString("Mobile").equals("null"))
                                    cousinAccount.setCousinMobile(accountObject.getString("Mobile"));


                                if(!accountObject.getString("Service_Category").equals("null"))
                                    cousinAccount.setServiceCategory(accountObject.getString("Service_Category").replace("\r\n",""));

                                if(!accountObject.getString("Service_Subcategory").equals("null"))
                                    cousinAccount.setServiceSubcategory(accountObject.getString("Service_Subcategory").replace("\r\n",""));

                                if(!accountObject.getString("Service_Name").equals("null"))
                                    cousinAccount.setServiceName(accountObject.getString("Service_Name").replace("\r\n",""));

                                if(!accountObject.getString("Gender").equals("null"))
                                    cousinAccount.setGender(accountObject.getString("Gender").replace("\r\n",""));

                                if(!accountObject.getString("distance").equals("null"))
                                    cousinAccount.setCousinDistance(accountObject.getString("distance").replace("\r\n",""));

                                if(!accountObject.getString("Category_TypeID").equals("null"))
                                    cousinAccount.setCategoryTypeID(accountObject.getString("Category_TypeID").replace("\r\n",""));


                                if(!accountObject.getString("Raty").equals("null"))
                                    cousinAccount.setRaty(accountObject.getString("Raty").replace("\r\n",""));


                                searchResultArrayList.add(cousinAccount);

                            }

                            if(searchResultArrayList.size()==0)
                             searchResultCallBack.onSearchResultError();
                            else
                            searchResultCallBack.onSearchResultSuccess(searchResultArrayList);


                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    searchResultCallBack.onSearchResultFailure();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                searchResultCallBack.onSearchResultFailure();
            }
        });

    }

    //Get All Search result account
    protected  void AddRequestHelp(final GetCallback.AddRequestHelp addRequestHelp, final String addHelpRequest){

        final Call<String> addHelpCall  = apiInterface.addRequestHelp(addHelpRequest);
        addHelpCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {


                    try {

                     String addHelpResponse = response.body();
                     JSONObject jsonObject = new JSONObject(addHelpResponse);
                     if(jsonObject.getString("Status").equals("Success"))
                         addRequestHelp.onAddRequestHelpSuccess();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                addRequestHelp.onAddRequestHelpFailure();
            }
        });

    }





}
