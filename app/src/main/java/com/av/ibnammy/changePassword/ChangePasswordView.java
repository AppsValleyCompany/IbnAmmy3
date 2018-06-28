package com.av.ibnammy.changePassword;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Maiada on 5/30/2018.
 */

interface ChangePasswordView {

    String getCurrentPassword();
    String getNewPassword();
    String getConfirmPassword();


    void showErrorCurrentPassword();
    void showErrorNewPassword();
    void showErrorNotMatch();

    void hideErrorCurrentPassword();
    void hideErrorNewPassword();
    void hideErrorConfirmPassword();


    void showErrorSpaceCurrentPassword();
    void showErrorSpaceNewPassword();
    void showErrorSpaceConfirmPassword();

    Context context();
    Activity activity();

    void enableUpdateButton();
    void disableUpdateButton();

    void showProgressBar();
    void hideProgressBar();

    void showOnUpdatePasswordSuccess();
    void showOnUpdatePasswordError();
    void showOnUpdatePasswordNetworkFailure();



    void hideSoftKeyboard();



}
