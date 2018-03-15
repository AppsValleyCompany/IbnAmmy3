package com.av.ibnammy.login;

import android.content.Context;

/**
 * Created by Mina on 3/15/2018.
 */

public interface LoginContract {

     interface LoginView {
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

     interface LoginModel {
        void requestLogin(String userName, String password, final GetCallback.onLoginFinish listener);
    }

     interface LoginPresenter {
         void onLoginClicked(String userName, String password);
         boolean isValidFormat(String userName, String password);
        boolean validatePhone(String phone);
        boolean validatePass(String pass);
        public void requestLoginFromModel(String userName, String password);
        void goToSignupClicked();
        void onAttach(Context context);
        void onDetach();
    }
}
