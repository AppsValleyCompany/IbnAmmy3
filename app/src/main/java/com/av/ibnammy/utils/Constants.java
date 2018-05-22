package com.av.ibnammy.utils;

import java.util.HashMap;

/**
 * Created by Mina on 3/26/2018.
 */

public final class Constants {
    public static final String PHONE_KEY="phone";
    public static final String PASSWORD_KEY="password";
    public static  final  String PREF_NAME="MyPref";
    public static  final  String USER_ID="user_id";
    public static  final String SAVE_CREDS_BOOL="save";

    public static  HashMap<String,String> relation_map(){
        HashMap<String,String> relation_map=new HashMap<>();
        relation_map.put("اب" ,"fa");
        relation_map.put("ام" , "mo");
        relation_map.put("زوج","par");
        relation_map.put("زوجة","par");
        relation_map.put("اخ","bro");
        relation_map.put("اخت","sis");
        relation_map.put("ابن","son");
        relation_map.put("ابنة","dau");
        return relation_map;
    }

}
