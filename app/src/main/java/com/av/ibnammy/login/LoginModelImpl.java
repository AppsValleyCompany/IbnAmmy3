package com.av.ibnammy.login;

import android.os.Bundle;

import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;

import org.json.JSONException;
import org.json.JSONObject;

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
                if(s!=null) {
                    try {
                        JSONObject object = new JSONObject(s);
                        if (object.has("AccountID")) {
                            Bundle bundle=new Bundle();
                            bundle.putString("id",object.getString("AccountID"));
                            bundle.putString("fName",object.getString("FirstName"));
                            bundle.putString("sName",object.getString("SecondName"));
                            bundle.putString("img",object.getString("ProfileIMG"));
                            listener.onSuccess(bundle);
                        } else {
                            listener.onFailure(object.getString("Status"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else listener.onFailure(response.message());
               // "{"AccountID":"3","UpdateDate":"False"}"
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void requestForgetPassword(String phone, String email, final GetCallback.onResetPasswordFinish listener) {
        String data="{'Mobile':"+phone+",'Email':'"+email+"'}";
        Call<String> call = apiInterface.resetPasswordApi(data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String status=object.getString("Status");
                    if(status.equals("Success")){
                        listener.onResetPasswordSuccess("تم ارسال كلمة المرور بنجاح.");
                    }
                    else if (status.equals("This Mobile Is Not Registered")) {
                        listener.onResetPasswordFailure("هذا الرقم غير مسجل. الرجاء التأكد و اعد المحاولة.");
                    }
                    else if(status.equals("Email Not Found For This Account")){
                        listener.onResetPasswordFailure("هذا الايميل غير مسجل. الرجاء التأكد و اعد المحاولة.");
                    }
                    else listener.onResetPasswordFailure(status);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
              //  listener.onResetPasswordSuccess(response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onResetPasswordSuccess(t.getMessage());

            }
        });
    }
}
