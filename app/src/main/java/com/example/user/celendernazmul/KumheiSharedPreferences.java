package com.example.user.celendernazmul;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NevadaSoft on 12/30/2015.
 */
public class KumheiSharedPreferences {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String PREFS_NAME = "kumhei";

    public static final String LOG_IN_STATUS = "login";
    public static final String STAY_LOG_IN = "stay_log_in";
    public static final String USER_TYPE = "user_type";
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "user_email";
    public static final String CURRENT_TIME	= "current_time";



    public KumheiSharedPreferences() {
        super();
        // TODO Auto-generated constructor stub
    }


    public static boolean getBooleanValue(final Context context, String key){
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(key, false);
    }


    public static void setBooleanValue(final Context context, String key,
                                       Boolean status) {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(key, status);
        editor.commit();
    }

}
