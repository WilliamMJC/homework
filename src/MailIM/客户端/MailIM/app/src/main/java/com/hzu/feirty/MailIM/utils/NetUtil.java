package com.hzu.feirty.MailIM.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017-6-28.
 */

public class NetUtil {
    private static final String IDENTITY = "saveidentity";
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public static  boolean isIdentity(Context context){
        if(context!=null){
           String str= PreferencesUtil.getSharedStringData(context,IDENTITY);
            if(!str.contains("aaaa")){
                return false;
            }
        }
        return  true;
    }
}
