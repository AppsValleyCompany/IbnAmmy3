package com.av.ibnammy.updateUserData;

import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mina on 3/21/2018.
 */

public class UpdateDataModel {
    private static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
//String first_name,String second_name,String third_name,String forth_name,String birth_date, String sex,String marital_status,String blood_type

    public static void update_user_data(User user , final GetCallback.onUpdateFinish listener){

        Gson gson=new Gson();
        String data =gson.toJson(user);

        Call<String> call= apiInterface.updateAccountApi(data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                        JSONObject res=new JSONObject(response.body());
                        if(res.has("Status")){
                            if(res.getString("Status").equals("Success")){
                                listener.onUpdateSuccess("تم تسجيل بياناتك بنجاح.");
                            }
                } else
                            listener.onUpdateFailure(res.toString());
                } catch (JSONException e) {
                e.printStackTrace();
            }
    }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onUpdateFailure(t.getMessage());
            }
        });
    }

    public static void get_user_data(String data, final GetCallback.onUserDataFetched listener){
        Call<String> call = apiInterface.getUserData(data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Gson gson=new Gson();
                User user=gson.fromJson(response.body(),User.class);
                listener.onGetDataSuccess(user);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onGetDataFailure(t.getMessage());
            }
        });
    }
}
