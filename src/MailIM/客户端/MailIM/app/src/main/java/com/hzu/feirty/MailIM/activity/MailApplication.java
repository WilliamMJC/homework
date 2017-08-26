
package com.hzu.feirty.MailIM.activity;

import android.app.Application;
import android.content.Context;

import java.io.InputStream;
import java.util.ArrayList;

import javax.mail.Store;

public class MailApplication extends Application {
    private static Context mContext;
    private Store store;
    private ArrayList<InputStream> attachmentsInputStreams;
    public  void onCreate(){
        super.onCreate();
        mContext=getApplicationContext();
    }
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ArrayList<InputStream> getAttachmentsInputStreams() {
        return attachmentsInputStreams;
    }

    public void setAttachmentsInputStreams(ArrayList<InputStream> attachmentsInputStreams) {
        this.attachmentsInputStreams = attachmentsInputStreams;
    }

    public static  Context getmContext(){
        return mContext;
    }
    public void onLowMemory(){
        super.onLowMemory();
    }

}
