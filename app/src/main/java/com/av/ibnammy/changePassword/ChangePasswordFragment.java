package com.av.ibnammy.changePassword;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentChangePasswordBinding;

/**
 * Created by Maiada on 5/30/2018.
 */

public class ChangePasswordFragment  extends Fragment implements ChangePasswordView{

     FragmentChangePasswordBinding changePasswordBinding;

     ChangePasswordPresenter changePasswordPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        changePasswordBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_change_password, container, false);
        View rootView = changePasswordBinding.getRoot();  // getRoot because onCreateView return view

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
        changePasswordPresenter = new ChangePasswordPresenter(this);

        changePasswordBinding.btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordPresenter.onUpdatedButtonClicked();
            }
        });


        return rootView;
    }



    @Override
    public String getCurrentPassword() {
        return changePasswordBinding.inputLayoutCurrentPassword.getEditText().getText().toString();
    }

    @Override
    public String getNewPassword() {
        return changePasswordBinding.inputLayoutNewPassword.getEditText().getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return changePasswordBinding.inputLayoutConfirmPassword.getEditText().getText().toString();
    }



    @Override
    public void showErrorCurrentPassword() {
        changePasswordBinding.inputLayoutCurrentPassword.setError(getResources().getString(R.string.error_password));
    }

    @Override
    public void showErrorNewPassword() {
        changePasswordBinding.inputLayoutNewPassword.setError(getResources().getString(R.string.error_password));

    }




    @Override
    public void showErrorNotMatch() {
        changePasswordBinding.inputLayoutConfirmPassword.setError(getResources().getString(R.string.error_not_match));
    }

    @Override
    public void hideErrorCurrentPassword() {
        changePasswordBinding.inputLayoutCurrentPassword.setError(null);
    }

    @Override
    public void hideErrorNewPassword() {
        changePasswordBinding.inputLayoutNewPassword.setError(null);

    }

    @Override
    public void hideErrorConfirmPassword() {
        changePasswordBinding.inputLayoutConfirmPassword.setError(null);

    }

    @Override
    public Context context() {

        return getContext();
    }

    @Override
    public Activity activity() {

        return getActivity();
    }
    @Override
    public void showOnUpdatePasswordSuccess() {
        Toast.makeText(getContext(), getResources().getString(R.string.password_success), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOnUpdatePasswordError() {
        Toast.makeText(getContext(), getResources().getString(R.string.password_error), Toast.LENGTH_LONG).show();

    }

    @Override
    public void showOnUpdatePasswordNetworkFailure() {
        Toast.makeText(getContext(), getResources().getString(R.string.internet_error), Toast.LENGTH_LONG).show();

    }

    @Override
    public void enableUpdateButton() {
       changePasswordBinding.btnUpdatePassword.setEnabled(true);
    }

    @Override
    public void disableUpdateButton() {
        changePasswordBinding.btnUpdatePassword.setEnabled(false);
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void showProgressBar()
    {changePasswordBinding.pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        changePasswordBinding.pbLoading.setVisibility(View.INVISIBLE);
    }



    @Override
    public void showErrorSpaceCurrentPassword() {
        changePasswordBinding.inputLayoutCurrentPassword.setError(getResources().getString(R.string.error_input));

    }

    @Override
    public void showErrorSpaceNewPassword() {
        changePasswordBinding.inputLayoutNewPassword.setError(getResources().getString(R.string.error_input));

    }

    @Override
    public void showErrorSpaceConfirmPassword() {
        changePasswordBinding.inputLayoutConfirmPassword.setError(getResources().getString(R.string.error_input));

    }
}
