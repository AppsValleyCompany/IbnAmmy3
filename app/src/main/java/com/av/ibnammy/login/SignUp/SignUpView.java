package com.av.ibnammy.login.SignUp;

/**
 * Created by Mina on 3/1/2018.
 */

public interface SignUpView {
    void showProgress();
    void hideProgress();
    void showMessage(String error);
    void moveToLoginScreen();
    void setUsernameError(String e);
    void setPasswordError(String e);
    void setRepeatPasswordError(String e);
}

