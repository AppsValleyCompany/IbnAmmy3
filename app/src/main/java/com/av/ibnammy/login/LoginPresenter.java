package com.av.ibnammy.login;

/**
 * Created by Mina on 2/21/2018.
 */

public interface LoginPresenter {
    public void onLoginClicked(String userName, String password);
    public boolean isValidFormat(String userName, String password);
    boolean validatePhone(String phone);
    boolean validatePass(String pass);
    public void requestLoginFromModel(String userName, String password);
    void goToSignupClicked();
}
