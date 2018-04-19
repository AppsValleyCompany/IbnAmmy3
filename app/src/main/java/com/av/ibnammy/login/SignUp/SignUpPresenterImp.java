package com.av.ibnammy.login.SignUp;


import com.av.ibnammy.networkUtilities.GetCallback;

/**
 * Created by Mina on 3/1/2018.
 */

public class SignUpPresenterImp implements SignUpContract.SignUpPresenter,GetCallback.onSignUpFinish {

    SignUpModelImp signUpModelImp;
    SignUpContract.SignUpView signUpView;

    public SignUpPresenterImp(SignUpContract.SignUpView signUpView){
        signUpModelImp= new SignUpModelImp();
        this.signUpView=signUpView;
    }

    @Override
    public boolean validatePhone(String phone) {
        if(phone.length()!=11){
            signUpView.setUsernameError("رقم الهاتف غبر صحيح..يجب ان يتكون من 11 رقم.");
            signUpView.setPasswordError(null);
            signUpView.setRepeatPasswordError(null);
            return false;
        }
        else{
            signUpView.setUsernameError(null);
            return true;
        }
    }

    @Override
    public boolean validatePass(String pass, String repeat) {
        if(pass.length()<5){
            signUpView.setPasswordError("كلمة المرور اقل من 5 احرف!!");
            signUpView.setRepeatPasswordError(null);
            return false;
        }
        else if(!(repeat.equals(pass))){
            signUpView.setRepeatPasswordError("لا تتطابق مع كلمة المرور!");
            signUpView.setPasswordError(null);
            return false;
        }
        else{
            signUpView.setPasswordError(null);
            signUpView.setRepeatPasswordError(null);
            return true;
        }
    }

    @Override
    public void onSignUpClicked(String phone, String password, String repeat, int family) {
        signUpView.showProgress();
        if(validatePhone(phone)&& validatePass(password,repeat)) {
            requestSignUpFromModel(phone,password,family);
        }
        else
            signUpView.hideProgress();
   }

    @Override
    public void requestSignUpFromModel(String phone, String password, int family) {
        signUpModelImp.requestSignUp(phone,password,family,this);
    }

    @Override
    public void onSuccess(String status,String phone) {
        signUpView.hideProgress();
        signUpView.showMessage(status);
        signUpView.moveToLoginScreen(phone);
    }

    @Override
    public void onFailure(String s) {
        signUpView.hideProgress();
        signUpView.showMessage(s);

    }
}
