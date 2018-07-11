package com.av.ibnammy.login;


import android.content.Context;
import android.os.Bundle;

import com.av.ibnammy.networkUtilities.GetCallback;

/**
 * Created by lenovo on 20/02/2018.
 */


public class LoginPresenterImpl implements LoginContract.LoginPresenter,GetCallback.onLoginFinish,GetCallback.onResetPasswordFinish  {

    private LoginContract.LoginView loginView;
    private LoginModelImpl loginModel;

 //   public boolean isSavePassChecked;

    public LoginPresenterImpl(LoginContract.LoginView loginView){ //take an object of a class implements the model view
        this.loginView=loginView;
        this.loginModel=new LoginModelImpl();
    }

    @Override
    public void onLoginClicked(String userName, String password,String deviceToken){
        loginView.showProgress();
        if(validatePhone(userName)&& validatePass(password)){
            loginView.disableSignInButton();
            requestLoginFromModel(userName,password,deviceToken);

        }else{
            loginView.hideProgress();
        }
    }

    @Override
     public boolean isValidFormat(String phone, String password){
        if(phone.isEmpty()||phone.length()!=10){
            loginView.setUsernameError("خطأ في رقم الهاتف .");
            return false;
        }
         if(password.isEmpty()){
            loginView.setPasswordError("خطأ في كلمة المرور.");
            return false;
        }
        else return true;
    }

    @Override
    public boolean validatePhone(String phone) {
        if(phone.length()>18|| phone.length()<10 || !phone.startsWith("0")){
            loginView.setUsernameError("خطأ في رقم الهاتف.");
            loginView.setPasswordError(null);
            return false;
        }
        else{
            loginView.setUsernameError(null);
            return true;}
     }

    @Override
    public boolean validatePass(String pass) {
        if(pass.length()<3){
            loginView.setPasswordError("خطأ في كلمة المرور.");
            return false;
        }
        else{
            loginView.setPasswordError(null);
            return true;}
    }

    @Override
     public void requestLoginFromModel(String userName, String password,String deviceToken){
        loginModel.requestLogin(userName,password,deviceToken,this);
    }

    @Override
    public void goToSignupClicked() {
        loginView.moveToSignupScreen();
    }

    @Override
    public void onAttach(Context context) {
        loginView.loadCredentials();
    }

    @Override
    public void onDetach() {
        loginView=null;
    }

    @Override
    public void requestForgetPasswordFromModel(String phone, String email) {
      //  if(CommonUtils.isEmailValid(email)){
           // loginView.showProgress();
            loginModel.requestForgetPassword(phone,email,this);
   //     }
      //  else
         //   loginView.showError(email+" is not a valid email.");

    }

    @Override
    public void onSuccess(Bundle b) {
        if(b!=null&&loginView!=null) {
            loginView.hideProgress();
            loginView.showError("تم تسجيل الدخول بنجاح.");
      /*      String id = b.getString("id");
            String img=b.getString("img");*/
            loginView.saveCredentials(b);
            loginView.enableSignInButton();
            loginView.moveToHomeScreen(b);
        }
    }

    @Override
    public void onFailure(String s) {
        if(s!=null&&loginView!=null) {
            loginView.hideProgress();
            loginView.showError(s);
            loginView.enableSignInButton();
        }
    }


    @Override
    public void onResetPasswordSuccess(String status) {
        loginView.showError(status);
        loginView.dismissForgetPopup();
       // loginView.hideProgress();
    }

    @Override
    public void onResetPasswordFailure(String status) {
        loginView.showError("خطأ: "+status);
        loginView.dismissForgetPopup();
       // loginView.hideProgress();
    }
}
