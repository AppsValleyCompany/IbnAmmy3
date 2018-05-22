package com.av.ibnammy.updateUserData.familyData;

import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mina on 3/29/2018.
 */

public class FamilyModel {
    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    public static void addFamily(String phone, String password, ArrayList<Follower> family, final FamilyCallBack.AddFamily listener){

        if(family.size()>0) {
            Gson gson=new Gson();
            String s=gson.toJson(family.toArray());
            JSONObject object=new JSONObject();
            try {
                JSONArray jsonArray=new JSONArray(s);
                object.put("Mobile",phone);
                object.put("password",password);
                object.put("classAccount_Follower",jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String data=object.toString();
            Call<String> call = apiInterface.addFollowers(data); //replace null with the parsed obj above.
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    try {
                        JSONObject object = new JSONObject(response.body());
                        if(object.getString("Status").equals("Success"))
                          listener.onAddFamilySuccess("تم اضافة الاتباع بنجاح.");
                        else
                          listener.onAddFamilyFailure("خطأ فى العملية.");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    listener.onAddFamilyFailure(t.getMessage());
                }
            });
        }
        else listener.onAddFamilySuccess("لم يتم اضافة اتباع جدد.");
    }

    public static void getFamily(String phone, String password, final FamilyCallBack.GetFamily listener)  {
        JSONObject object=new JSONObject();
        try {
            object.put("Mobile",phone);
            object.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = object.toString();//"{'Mobile':"+phone+",'Password':"+password+"}";
        Call<String> call=apiInterface.getFollowers(data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Follower> family=new ArrayList<>();
                Gson gson=new Gson();
                String res=response.body();
                try {
                    JSONArray list=new JSONArray(res);
                    for(int i=0;i<list.length();i++){
                    Follower f=gson.fromJson(list.get(i).toString(),Follower.class);
                    family.add(f);
                    }
                    listener.onGetFamilySuccess(family);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onGetFamilyFailure(t.getMessage());
            }
        });

    }

    public static void deleteFollower(String phone, String password, String id, final FamilyCallBack.DeleteFollower listener){
       //String data = "{'Mobile':"+phone+",'Password':"+password+",'Account_FollowerID':"+id+"}";
        JSONObject object=new JSONObject();
        try {
            object.put("Mobile",phone);
            object.put("password",password);
            object.put("Account_FollowerID",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = object.toString();

       Call<String> call=apiInterface.deleteFollower(data);
       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {

               try {
                   JSONObject object=new JSONObject(response.body());
                   if(object.getString("Status").equals("Success"))
                    listener.onDeleteFollowerSuccess("تم ازالة فرد من العائلة.");
                   else
                       listener.onDeleteFollowerFailure("حدث خطأ اثناء العملية.");

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {
               listener.onDeleteFollowerFailure(t.getMessage());
           }
       });

    }
}
