package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.view.View;

import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maiada on 7/20/2018.
 */

public class CousinProfileModel {

    protected  ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    protected void addAccountFavourite(final GetCallback.AddFavourite addFavourite, String phoneNumber, String password, String favouriteAccountID){
       // "{'Mobile':0101234567,'Password':12345,'FavouriteAccountID':4156}"

         String requestBody="{'Mobile':"+phoneNumber+",'Password':'"+password+"'"+",'FavouriteAccountID':'"+favouriteAccountID+"'}";

        Call<String> addFavouriteCall  = apiInterface.addFavourite(requestBody);
        addFavouriteCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                       if(response.isSuccessful()){
                           try {
                               JSONObject jsonObject = new JSONObject(response.body());
                               String responseBody ="Account Added To Your Favourite";
                               if(jsonObject.getString("Status").equals(responseBody))
                                   addFavourite.onAddFavouriteSuccess();




                           } catch (JSONException e) {
                               e.printStackTrace();
                           }


                       }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
           addFavourite.onAddFavouriteFailure();
            }
        });



    }

    public void deleteAccountFavourite(final GetCallback.DeleteFavourite deleteFavourite, String phoneNumber, String password, String favouriteAccountID){
        // "{'Mobile':0101234567,'Password':12345,'FavouriteAccountID':4156}"

        String requestBody="{'Mobile':"+phoneNumber+",'Password':'"+password+"'"+",'FavouriteAccountID':'"+favouriteAccountID+"'}";

        Call<String> addFavouriteCall  = apiInterface.deleteFavourite(requestBody);
        addFavouriteCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String responseBody ="Favourite Account Deleted";
                        if(jsonObject.getString("Status").equals(responseBody))
                            deleteFavourite.onDeleteFavouriteSuccess();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                deleteFavourite.onDeleteFavouriteFailure();
            }
        });



    }

    public void getAllFavourite(final GetCallback.GetAllFavouriteAccount getAllFavouriteAccount, String phoneNumber, String password){
        // "{'Mobile':0101234567t,'Password':12345,'FavouriteAccountID':4156}"

        String requestBody="{'Mobile':"+phoneNumber+",'Password':'"+password+"'}";
        Call<String> addFavouriteCall  = apiInterface.getAllFavourite(requestBody);
        addFavouriteCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                         ArrayList<CousinAccount> cousinAccountList = new ArrayList<>();
                         JSONArray jsonArray = new JSONArray(response.body());
                         for(int i=0;i<jsonArray.length();i++){
                             JSONObject accountObject= jsonArray.getJSONObject(i).getJSONObject("FavoriteAccount");
                             CousinAccount cousinAccount = new CousinAccount();

                             if(!accountObject.getString("AccountID").equals("null"))
                                 cousinAccount.setCousinId(accountObject.getString("AccountID").replace("\r\n",""));

                             if(!accountObject.getString("First_Name").equals("null")&&
                                     !accountObject.getString("Second_Name").equals("null"))
                                 cousinAccount.setCousinName(accountObject.getString("First_Name").replaceAll("\n","")
                                         .concat(" "+accountObject.getString("Second_Name").replaceAll("\n","")));


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

                             if(!accountObject.getString("Category_TypeID").equals("null"))
                                 cousinAccount.setCategoryTypeID(accountObject.getString("Category_TypeID").replace("\r\n",""));

                             if(!accountObject.getString("Raty").equals("null"))
                                 cousinAccount.setRaty(accountObject.getString("Raty").replace("\r\n",""));



                             cousinAccountList.add(cousinAccount);
                         }

                        getAllFavouriteAccount.onGetAllFavouriteSuccess(cousinAccountList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getAllFavouriteAccount.onGetAllFavouriteFailure();
            }
        });



    }

    public void getAllFavouriteID(final GetCallback.GetAllFavouriteIdAccount getAllFavouriteAccount, String phoneNumber, String password){
        // "{'Mobile':0101234567t,'Password':12345,'FavouriteAccountID':4156}"

        String requestBody="{'Mobile':"+phoneNumber+",'Password':'"+password+"'}";
        Call<String> addFavouriteCall  = apiInterface.getAllFavourite(requestBody);
        addFavouriteCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        ArrayList<String> favoriteAccountIdList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(response.body());
                        for(int i=0;i<jsonArray.length();i++){
                            String favoriteAccountId= jsonArray.getJSONObject(i).getJSONObject("FavoriteAccount").getString("AccountID");
                            favoriteAccountIdList.add(favoriteAccountId);
                        }

                        getAllFavouriteAccount.onGetAllFavouriteIdSuccess(favoriteAccountIdList);







                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getAllFavouriteAccount.onGetAllFavouriteIdFailure();
            }
        });



    }

    public void UpdateAccountRate(final GetCallback.UpdateAccountRate updateAccountRate, String phoneNumber, String password, String accountId, float raty){
        // "{'Mobile':0101234567t,'Password':12345,'FavouriteAccountID':4156}"

        String requestBody="{'Mobile':"+phoneNumber+",'Password':'"+password+"'"+",'AccountID':'"+accountId+"','Raty':'"+raty+"'}";
        Call<String> updateAccountRateCall  = apiInterface.updateRate(requestBody);
        updateAccountRateCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        if(jsonObject.getString("Status").equals("Success"))
                         updateAccountRate.onUpdateRateSuccess();
                        else
                         updateAccountRate.onUpdateRateFailure();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                updateAccountRate.onUpdateRateFailure();
            }
        });



    }


}
