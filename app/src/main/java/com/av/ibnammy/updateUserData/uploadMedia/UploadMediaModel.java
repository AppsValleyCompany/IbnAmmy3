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

    public static  void UploadMediaImage(final MediaCallBack.uploadMediaImage getCallBack, String id, ArrayList<MultipartBody.Part> list){
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

    public  static  void  DeleteMediaImage(final  MediaCallBack.deleteMediaImage deleteMediaImageCallback,String id,int imgNumber){
        Call<String> deletePhotoCall  = apiInterface.deletePhoto(id,imgNumber);
        deletePhotoCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body());
                        String status = object.getString("Status");
                        deleteMediaImageCallback.onSuccess(status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                deleteMediaImageCallback.onFailure("حدث خطأ اثناء التحميل");
            }
        });


    }

    public  static  void UpdateMediaImage(final MediaCallBack.updateMediaImage updateMediaImage, String id, int imgNumber, MultipartBody.Part part){
        Call<String> updateImageCall  = apiInterface.updatePhoto(id,imgNumber,part);
        updateImageCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body());
                        String status = object.getString("Status");
                        updateMediaImage.onSuccess(status);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                updateMediaImage.onFailure("حدث خطأ اثناء الرفع");

            }
        });


    }

    public  static  void  DeleteMediaVideo(final  MediaCallBack.deleteMediaVideo deleteMediaVideoCallback,String id){
        Call<String> deleteVideoCall  = apiInterface.deleteVideo(id);
        deleteVideoCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body());
                        String status = object.getString("Status");
                        deleteMediaVideoCallback.onSuccess(status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                deleteMediaVideoCallback.onFailure("حدث خطأ اثناء التحميل");
            }
        });


    }

}
