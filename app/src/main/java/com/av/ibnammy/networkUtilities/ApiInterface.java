package com.av.ibnammy.networkUtilities;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Mina on 1/10/2018.
 */

public interface ApiInterface {
    //@GET("/api/users?")
    @POST("AccountServices/Signin_Account/Login")
    Call<String> loginApi(@Body String data);

    @POST("AccountServices/SignUp_Account/CreateAccount")
    Call<String> signUpApi(@Body String data);
}
