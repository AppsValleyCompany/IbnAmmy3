package com.av.ibnammy.login;

import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 20/02/2018.
 */


public class LoginModelImpl implements LoginContract.LoginModel {

   private ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void requestLogin(String userName, String password, final GetCallback.onLoginFinish listener) {
         String data="{'Mobile':"+userName+",'Password':'"+password+"'}";
           Call<String> call = apiInterface.loginApi(data);
            call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s= response.body();
                listener.onSuccess(s);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
