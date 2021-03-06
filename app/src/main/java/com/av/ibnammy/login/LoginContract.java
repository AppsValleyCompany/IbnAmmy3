package com.av.ibnammy.login;

import android.content.Context;
import android.os.Bundle;

import com.av.ibnammy.networkUtilities.GetCallback;

/**
 * Created by Mina on 3/15/2018.
 */

public interface LoginContract {

     interface LoginView {
        void showProgress();
        void hideProgress();
        void showError(String error);
        void moveToHomeScreen(Bundle bundle);
        void moveToSignupScreen();
        void setUsernameError(String e);
        void setPasswordError(String e);
        void saveCredentials(Bundle b);
        void loadCredentials();
        void dismissForgetPopup();
        void showForgetPopup(String phone);
        void enableSignInButton();
        void disableSignInButton();
        String getDeviceToken();
    }

     interface LoginModel {
        void requestLogin(String userName, String password,String deviceToken, final GetCallback.onLoginFinish listener);
        void requestForgetPassword(String phone, String email, GetCallback.onResetPasswordFinish listener);
    }

     interface LoginPresenter {
        void onLoginClicked(String userName, String password,String deviceToken);
        boolean isValidFormat(String userName, String password);
        boolean validatePhone(String phone);
        boolean validatePass(String pass);
        public void requestLoginFromModel(String userName, String password,String deviceToken);
        void goToSignupClicked();
        void onAttach(Context context);
        void onDetach();
        void requestForgetPasswordFromModel(String phone, String email);
    }
}
