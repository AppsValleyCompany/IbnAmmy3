package com.av.ibnammy.changePassword;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.utils.CommonUtils;
import com.av.ibnammy.utils.Constants;

import java.net.PasswordAuthentication;

/**
 * Created by Maiada on 5/30/2018.
 */

public class ChangePasswordPresenter  implements ChangePasswordImplementerPresenter {

    private  ChangePasswordView changePasswordView;
    private  ChangePasswordModel changePasswordModel;


    public ChangePasswordPresenter(ChangePasswordView changePasswordView) {
       this.changePasswordView = changePasswordView;
       this.changePasswordModel = new ChangePasswordModel();
 }

    @Override
    public void onUpdatedButtonClicked() {
        if(validateData()) {

            onUpdatePassword();
        }
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) changePasswordView.activity().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
               changePasswordView.activity().getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public void onUpdatePassword() {

        invisibleUI();
        Bundle bundle = CommonUtils.loadCredentials(changePasswordView.context());
        String phoneNumber = bundle.getString(Constants.PHONE_KEY);

        changePasswordModel.changePasswordData(phoneNumber, changePasswordView.getCurrentPassword(), changePasswordView.getConfirmPassword()
                , new GetCallback.onUpdatePassword() {
                    @Override
                    public void onUpdatePasswordSuccess() {
                        visibleUI();
                        changePasswordView.showOnUpdatePasswordSuccess();

                    }

                    @Override
                    public void onUpdatePasswordError() {
                        visibleUI();
                        changePasswordView.showOnUpdatePasswordError();
                    }

                    @Override
                    public void onUpdatePasswordFailure() {
                        visibleUI();
                        changePasswordView.showOnUpdatePasswordNetworkFailure();

                    }
                });
    }

    @Override
    public void visibleUI() {
        changePasswordView.enableUpdateButton();
        changePasswordView.hideProgressBar();

    }

    @Override
    public void invisibleUI() {
        hideSoftKeyboard();
        changePasswordView.disableUpdateButton();
        changePasswordView.showProgressBar();
    }



    @Override
    public boolean validateData() {
        if(changePasswordView.getCurrentPassword().length()<3){
            changePasswordView.showErrorCurrentPassword();

            changePasswordView.hideErrorNewPassword();
            changePasswordView.hideErrorConfirmPassword();
            return false;
        }else {
            if (changePasswordView.getNewPassword().length() < 3) {
                changePasswordView.showErrorNewPassword();

                changePasswordView.hideErrorCurrentPassword();
                changePasswordView.hideErrorConfirmPassword();
                return false;
            } else {
                    if (!changePasswordView.getConfirmPassword().equals(changePasswordView.getNewPassword())) {
                        changePasswordView.showErrorNotMatch();

                        changePasswordView.hideErrorCurrentPassword();
                        changePasswordView.hideErrorNewPassword();

                        return false;
                    } else{
                        changePasswordView.hideErrorCurrentPassword();
                        changePasswordView.hideErrorNewPassword();
                        changePasswordView.hideErrorConfirmPassword();
                        return true;
                    }
                }
            }



    }


}
