package com.av.ibnammy.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mina on 20/02/2018.
 */


public class LoginModelImpl implements LoginContract.LoginModel {

   private ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void requestLogin(String userName, String password, final GetCallback.onLoginFinish listener) {
            String data="{'Mobile':"+userName+",'Password':'"+password+"'}";
            Call<String> call = apiInterface.loginApi(data);
            call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
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
                            bundle.putString("lat",object.getString("Home_Latitude"));
                            bundle.putString("lng",object.getString("Home_Longitude"));

                            if(listener!=null)
                            listener.onSuccess(bundle);
                        } else if(object.getString("Status").equals("Invalid Mobile Or Password")){
                                if(listener!=null)
                                listener.onFailure("خطأ في رقم الهاتف او كلمة المرور.");
                        }
                        else listener.onFailure(object.getString("Status"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    if(listener!=null)
                        listener.onFailure(response.message());
                }
               // "{"AccountID":"3","UpdateDate":"False"}"
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                if(listener!=null)
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
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
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
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                if(t.getMessage()!=null)
                    listener.onResetPasswordSuccess(t.getMessage());
            }
        });
    }
}
