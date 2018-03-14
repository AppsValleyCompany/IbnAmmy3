package com.av.ibnammy.login.SignUp;

/**
 * Created by Mina on 3/1/2018.
 */

public interface SignUpPresenter {
    boolean validatePhone(String phone);
    boolean validatePass(String pass, String repeat);
    void onSignUpClicked(String phone, String password, String repeat, int family);
    void requestSignUpFromModel(String phone, String password, int family);

}
