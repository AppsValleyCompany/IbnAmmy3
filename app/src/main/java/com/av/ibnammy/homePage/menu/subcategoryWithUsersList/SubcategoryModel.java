package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;



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
 * Created by Aya Mahmoud on 3/20/2018.
 */

public class SubcategoryModel {

    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
    public static void GetAllSubCategory(final GetCallback.SubCategoryCallBack subCategoryCallBack, String categoryId){
        final Call<String> subCategoryCall =  apiInterface.subCategoryApi(categoryId);
        subCategoryCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray subCategories = jsonObject.getJSONArray("classService_SubCategories");
                        ArrayList<SubCategory>  subCategoryArrayList = new ArrayList<>();
                        for(int i=0;i<subCategories.length();i++){
                            JSONObject subCategoryJObject = subCategories.getJSONObject(i);
                            SubCategory subCategory = new SubCategory();
                            subCategory.setSubCategoryName(subCategoryJObject.getString("Name").replace("\r\n",""));
                            JSONArray classAccounts = subCategoryJObject.getJSONArray("classAccounts");
                            ArrayList<CousinAccount>  accountArrayList = new ArrayList<>();
                            if(classAccounts.length()!=0){
                                for(int j=0;j<classAccounts.length();j++){
                                    CousinAccount cousinAccount = new CousinAccount();
                                    JSONObject accountObject = classAccounts.getJSONObject(j);
                                    cousinAccount.setHasAccount(true);
                                    if(!accountObject.getString("First_Name").equals("")&&
                                            !accountObject.getString("Second_Name").equals("")){

                                        cousinAccount.setCousinName(accountObject.getString("First_Name")
                                                .concat(" "+accountObject.getString("Second_Name")));
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



                                    accountArrayList.add(cousinAccount);

                                }
                                subCategory.setCousinAccounts(accountArrayList);
                                subCategoryArrayList.add(subCategory);

                            }else{
                                CousinAccount cousinAccount = new CousinAccount();
                                cousinAccount.setHasAccount(false);
                                accountArrayList.add(cousinAccount);
                                subCategory.setCousinAccounts(accountArrayList);
                                subCategoryArrayList.add(subCategory);
                            }


                        }
                        subCategoryCallBack.onSuccess(subCategoryArrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                subCategoryCallBack.onFailure(t.getMessage());
            }
        });

    }





}
