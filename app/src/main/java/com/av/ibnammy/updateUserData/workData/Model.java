package com.av.ibnammy.updateUserData.workData;


import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mina on 3/25/2018.
 */

public class Model {

 public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
//String first_name,String second_name,String third_name,String forth_name,String birth_date, String sex,String marital_status,String blood_type
    public static void  getDDLists(final GetCallback.onDDListsFetched listener){
    Call<String> call= apiInterface.getDDListsApi();
    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            Gson gson=new Gson();
            DDListResponse ddl=gson.fromJson(response.body(),DDListResponse.class);
            listener.onDDListsFetchSuccess(ddl);
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            listener.onDDListsFetchFailure(t.getMessage());
        }
    });
}
    public static void getServiceTypes(final GetCallback.onServiceTypesFetched listener){
        Call<String> call= apiInterface.getServiceTypesApi();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ServiceType> serviceTypes=new ArrayList<>();

                try {
                    JSONArray array=new JSONArray(response.body());
                    for(int i=0;i<array.length();i++){
                        Gson gson=new Gson();
                        ServiceType st=gson.fromJson(array.get(i).toString(),ServiceType.class);
                        serviceTypes.add(st);
                    }
                    listener.onTypesFetchSuccess(serviceTypes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //  ArrayList<ServiceTypeSearch> serviceTypes=response.body();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onTypesFetchFailure(t.getMessage());
            }
        });

    }
}
