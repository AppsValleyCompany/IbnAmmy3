package com.av.ibnammy.changePassword;

import android.app.Activity;
import android.content.Context;

import java.util.Collections;

/**
 * Created by Maiada on 6/3/2018.
 */

  interface ChangePasswordImplementerPresenter  {

    boolean validateData();

     void onUpdatedButtonClicked();
     void onUpdatePassword();

     void visibleUI();
     void invisibleUI();

     void hideSoftKeyboard();


}


