package com.av.ibnammy.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.av.ibnammy.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mina on 3/15/2018.
 */

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }


    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static void transitFrag_BackStack(Context context,Fragment targetFragment){
        if(context!=null&&targetFragment!=null)
             ((AppCompatActivity)context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_holder_update_data, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("")
                .commit();
    }

    public static void transitFrag(Context context,Fragment targetFragment){
        ((AppCompatActivity)context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_holder_update_data, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static Bundle loadCredentials(Context context) {
        Bundle bundle=new Bundle();
        SharedPreferences pref = context.getSharedPreferences(Constants.PREF_NAME,  Context.MODE_PRIVATE);
        if(pref!=null){
            String phone=pref.getString(Constants.PHONE_KEY,"null");
            String passsword=pref.getString(Constants.PASSWORD_KEY,"null");
            String id=pref.getString(Constants.USER_ID,"null");
            if(!phone.equals("null"))
               bundle.putString(Constants.PHONE_KEY,phone);
            if(!passsword.equals("null"))
                bundle.putString(Constants.PASSWORD_KEY,passsword);
                 bundle.putString(Constants.USER_ID,id);
        }
        return bundle;
    }


/*    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }*/
}
