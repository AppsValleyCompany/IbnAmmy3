package com.av.ibnammy.login;

/**
 * Created by Mina on 2/20/2018.
 */

public abstract class GetCallback {
    public interface onLoginFinish{
        void onSuccess(String s);
        void onFailure(String s);
    }

    public interface onSignUpFinish{
        void onSuccess(String s);
        void onFailure(String s);
    }
}
