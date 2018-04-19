package com.av.ibnammy.login.SignUp;

import com.av.ibnammy.networkUtilities.GetCallback;

/**
 * Created by Mina on 3/15/2018.
 */

public interface SignUpContract {
     interface SignUpModel {
        void requestSignUp(String mobile, String password, int familyID, GetCallback.onSignUpFinish listener);

    }

     interface SignUpView {
        void showProgress();
        void hideProgress();
        void showMessage(String error);
        void moveToLoginScreen(String phone);
        void setUsernameError(String e);
        void setPasswordError(String e);
        void setRepeatPasswordError(String e);
    }

     interface SignUpPresenter {
        boolean validatePhone(String phone);
        boolean validatePass(String pass, String repeat);
        void onSignUpClicked(String phone, String password, String repeat, int family);
        void requestSignUpFromModel(String phone, String password, int family);

    }
}
