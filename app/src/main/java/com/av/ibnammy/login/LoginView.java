package com.av.ibnammy.login;

/**
 * Created by lenovo on 20/02/2018.
 */

public interface LoginView {
    void showProgress();
    void hideProgress();
    void showError(String error);
    void moveToHomeScreen();
    void moveToSignupScreen();
    void setUsernameError(String e);
    void setPasswordError(String e);
    void saveCredentials();
    void loadCredentials();
}
