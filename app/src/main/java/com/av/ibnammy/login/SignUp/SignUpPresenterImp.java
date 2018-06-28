package com.av.ibnammy.login.SignUp;


import com.av.ibnammy.networkUtilities.GetCallback;
import com.av.ibnammy.utils.CommonUtils;

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

       // if(phone.length()!=11){
        if(phone.length()>18|| phone.length()<10|| !phone.startsWith("0")){
           // signUpView.setUsernameError("رقم الهاتف غبر صحيح..يجب ان يتكون من 11 رقم.");
            signUpView.setUsernameError("خطأ في رقم الهاتف");

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
        if(pass.length()<3 || pass.contains(" ")){
            signUpView.setPasswordError("كلمة المرور يجب ان لا تحتوي على مسافات او تكون اقل من ٣ احرف/ارقام");
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
    public boolean validateEmail(String email) {
        if(CommonUtils.isEmailValid(email)||email.isEmpty()){
            signUpView.setPasswordError(null);
            signUpView.setRepeatPasswordError(null);
            signUpView.setUsernameError(null);
            signUpView.setEmailError(null);
            return true;
        }
        else {
            signUpView.setEmailError("خطأ فى الايميل.");
            return false;
        }
    }

    @Override
    public void onSignUpClicked(String phone, String password, String repeat,String email, int family) {
        signUpView.showProgress();
        if(validatePhone(phone)&& validatePass(password,repeat)&&validateEmail(email)) {
            requestSignUpFromModel(phone,password,email,family);
        }
        else
            signUpView.hideProgress();
   }

    @Override
    public void requestSignUpFromModel(String phone, String password,String email, int family) {
        signUpModelImp.requestSignUp(phone,password,email,family,this);
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
