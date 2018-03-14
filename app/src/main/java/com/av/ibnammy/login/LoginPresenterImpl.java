package com.av.ibnammy.login;


/**
 * Created by lenovo on 20/02/2018.
 */

public class LoginPresenterImpl implements LoginPresenter,GetCallback.onLoginFinish  {

    private LoginView loginView;
    private LoginModelImpl loginModel;

    public LoginPresenterImpl(LoginView loginView){ //take an object of a class implements the model view
        this.loginView=loginView;
        this.loginModel=new LoginModelImpl();
    }

    @Override
    public void onLoginClicked(String userName, String password){
        loginView.showProgress();
        if(validatePhone(userName)&& validatePass(password)){
            requestLoginFromModel(userName,password);
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
        if(phone.length()!=11){
            loginView.setUsernameError("خطأ في رقم الهاتف");
            loginView.setPasswordError(null);
            return false;
        }
        else{
            loginView.setUsernameError(null);
            return true;}
     }

    @Override
    public boolean validatePass(String pass) {
        if(pass.length()<5){
            loginView.setPasswordError("خطأ في كلمة المرور.");
            return false;
        }
        else{
            loginView.setPasswordError(null);
            return true;}
    }

    @Override
     public void requestLoginFromModel(String userName, String password){
        loginModel.requestLogin(userName,password,this);
    }

    @Override
    public void goToSignupClicked() {
        loginView.moveToSignupScreen();
    }

    @Override
    public void onSuccess(String s) {
        loginView.hideProgress();
        loginView.showError("Login success. your id is: " + s);
        loginView.saveCredentials();
        loginView.moveToHomeScreen();
    }

    @Override
    public void onFailure(String s) {
        loginView.hideProgress();
        loginView.showError(s);
    }
}
