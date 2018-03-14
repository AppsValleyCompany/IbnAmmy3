package com.av.ibnammy.login.SignUp;


import com.av.ibnammy.login.GetCallback;

/**
 * Created by Mina on 3/1/2018.
 */

public interface SignUpModel {
    void requestSignUp(String mobile, String password, int familyID, GetCallback.onSignUpFinish listener);

}
