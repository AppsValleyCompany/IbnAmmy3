package com.av.ibnammy.viewprofile;


import com.av.ibnammy.networkUtilities.ApiClient;
import com.av.ibnammy.networkUtilities.ApiInterface;
import com.av.ibnammy.networkUtilities.GetCallback;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maiada on 4/2/2018.
 */

public class ProfileModel {

    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    public static void GetProfileData(final String phoneNumber, String password, final GetCallback.ProfileCallBack profileCallBack){

        String data="{'Mobile':"+phoneNumber+",'Password':'"+password+"'}";
        Call<String> profileDataCall  = apiInterface.getUserData(data);
        profileDataCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {

                try
                {
                    JSONObject jsonObject=new JSONObject(response.body());
                    Profile profile = new Profile();
                    if(!jsonObject.getString("First_Name").equals("null")||
                            !jsonObject.getString("Second_Name").equals("null")
                            ){
                        profile.setUserName(jsonObject.getString("First_Name").replaceAll("\n","")
                                +jsonObject.getString("Second_Name").replaceAll("\n",""));

                        profile.setFullUserName(jsonObject.getString("First_Name").replaceAll("\n","")+" "
                                +jsonObject.getString("Second_Name").replaceAll("\n","")+" "+jsonObject.getString("Third_Name").replaceAll("\n","")+" "
                                +jsonObject.getString("Forth_Name").replaceAll("\n",""));
                    }else {
                        profile.setUserName("لا يوجد");
                    }

                    profile.setAccountId(jsonObject.getString("AccountID"));

                    if(!jsonObject.getString("Mobile").equals("null"))
                    {
                        profile.setMobile(jsonObject.getString("Mobile"));
                    }
                    else
                    {
                        profile.setMobile("لا يوجد");
                    }
                    //
                    if(!jsonObject.getString("Subscription_status").equals(false))
                    {
                        profile.setSubscriptionStatus("تم التفعيل ");
                    }
                    else
                    {
                        profile.setSubscriptionStatus(" لم يتم التفعيل");
                    }
                    //
                    if(!jsonObject.getString("Active").equals(false))
                    {
                        profile.setActive("تم التفعيل ");
                    }
                    else
                    {
                        profile.setActive("لم يتم التفعيل ");
                    }
                    //
                    if(!jsonObject.getString("Followers_Number").equals("null"))
                    {
                        profile.setFollowersNumber(jsonObject.getString("Followers_Number"));
                    }
                    else
                    {
                        profile.setFollowersNumber("لا يوجد ");
                    }

                    String imageName=jsonObject.getString("Profile_IMG");
                    if(!imageName.equals("null")){
                        profile.setProfileImage(imageName);
                    }

                    if(!jsonObject.getString("Home_Latitude").equals("null"))
                        profile.setHomeLatitude(jsonObject.getString("Home_Latitude"));

                    if(!jsonObject.getString("Home_Longitude").equals("null"))
                        profile.setHomeLongitude(jsonObject.getString("Home_Longitude"));

                   profileCallBack.onSuccess(profile);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                profileCallBack.onFailure(t.getMessage());

            }
        });

    }

}
