package com.av.ibnammy.homePage.menu;


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
 * Created by Maiada on 3/17/2018.
 */

public class MenuModel {

    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    public static void GetAllCategory(final GetCallback.CategoryCallBack categoryCallBack){

        Call<String> categoryListCall  = apiInterface.categoryApi();
        categoryListCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String getResponse = response.body();
                    try {
                        JSONObject categoriesObject = new JSONObject(getResponse);
                        JSONArray classCategoryTypes = categoriesObject.getJSONArray("ClassCategory_Types");
                        ArrayList<CategoryType>  categoryTypeArrayList = new ArrayList<>();
                        ArrayList<CategoryList>  categoryListArrayList = new ArrayList<>();
                        for(int i=0;i<classCategoryTypes.length();i++){
                            JSONObject categoryTypeObject = classCategoryTypes.getJSONObject(i);
                            CategoryType categoryType = new CategoryType();
                            String categoryTypeName =categoryTypeObject.getString("Name");
                            categoryType.setCategoryType(categoryTypeName);
                            categoryTypeArrayList.add(categoryType);
                            JSONArray categories = categoryTypeObject.getJSONArray("classService_Categories");
                            ArrayList<Category> categoryArrayList = new ArrayList<>();
                            CategoryList categoryList = new CategoryList();
                            for(int j=0;j<categories.length();j++){
                                JSONObject categoryObject = categories.getJSONObject(j);
                                Category category = new Category();
                                category.setService_CategoryID(categoryObject.getString("Service_CategoryID"));
                                category.setNumberOfMembers_ServiceCategory(categoryObject.getString("NumberOfMembers_ServiceCategory"));
                                category.setCategoryName(categoryObject.getString("Name"));
                                categoryArrayList.add(category);
                            }
                            categoryList.setCategoryArrayList(categoryArrayList);
                            categoryListArrayList.add(categoryList);
                        }

                         categoryCallBack.onSuccess(categoryTypeArrayList,categoryListArrayList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                categoryCallBack.onFailure(t.getMessage());
            }
        });


    }

}
