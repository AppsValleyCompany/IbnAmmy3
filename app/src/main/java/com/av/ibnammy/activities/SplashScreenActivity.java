package com.av.ibnammy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.av.ibnammy.R;
import com.av.ibnammy.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

       private Handler timer;
       private int splash_timeout=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

          initial();
    }
    public void initial(){

        timer=new Handler();
        timer.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));

                           }catch (Exception e){e.printStackTrace();}
                           finally {
                               finish();
                           }
                       }
                   }
       ,splash_timeout );


    }
}
