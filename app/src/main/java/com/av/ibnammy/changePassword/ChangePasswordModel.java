package com.av.ibnammy.changePassword;

import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maiada on 5/30/2018.
 */

public class ChangePasswordModel {

    public  ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    public  void changePasswordData(String phoneNumber, String password, String newPassword, final GetCallback.onUpdatePassword updatePassword){
        String data="{'Mobile':"+phoneNumber+",'Password':'"+password+"','NewPassword':'"+newPassword+"'}";
        Call<String> changePasswordCall  = apiInterface.updatePassword(data);
        changePasswordCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                  if(response.isSuccessful()){
                      try {
                          JSONObject getResponse = new JSONObject(response.body());
                          if(getResponse.getString("Status").equals("Success"))
                              updatePassword.onUpdatePasswordSuccess();
                           else
                              updatePassword.onUpdatePasswordError();

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                updatePassword.onUpdatePasswordFailure();
            }
        });


    }

}
