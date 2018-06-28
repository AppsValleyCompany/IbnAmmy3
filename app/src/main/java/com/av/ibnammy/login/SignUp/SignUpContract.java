package com.av.ibnammy.login.SignUp;

import com.av.ibnammy.networkUtilities.GetCallback;

/**
 * Created by Mina on 3/15/2018.
 */

public interface SignUpContract {
     interface SignUpModel {
        void requestSignUp(String mobile, String password, String email, int familyID, GetCallback.onSignUpFinish listener);

    }

     interface SignUpView {
        void showProgress();
        void hideProgress();
        void showMessage(String error);
        void moveToLoginScreen(String phone);
        void setUsernameError(String e);
        void setPasswordError(String e);
        void setRepeatPasswordError(String e);
        void setEmailError(String e);
    }

     interface SignUpPresenter {
        boolean validatePhone(String phone);
        boolean validatePass(String pass, String repeat);
        boolean validateEmail(String email);
        void onSignUpClicked(String phone, String password, String repeat, String emai, int family);
        void requestSignUpFromModel(String phone, String password, String email, int family);

    }
}
