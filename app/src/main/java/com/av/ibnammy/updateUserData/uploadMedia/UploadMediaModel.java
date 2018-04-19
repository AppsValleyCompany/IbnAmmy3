package com.av.ibnammy.updateUserData.uploadMedia;

import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maiada on 4/11/2018.
 */

public class UploadMediaModel {

    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    public static void UploadMedia(final MediaCallBack.uploadMediaImage getCallBack, String id, ArrayList<MultipartBody.Part> list){
        Call<String> profileDataCall  = apiInterface.uploadImagesToServer(id,list);
        profileDataCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        getCallBack.onSuccess(jsonObject.getString("Status"));

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

}
