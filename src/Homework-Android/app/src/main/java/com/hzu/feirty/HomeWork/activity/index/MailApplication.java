
package com.hzu.feirty.HomeWork.activity.index;

import android.app.Application;
import android.content.Context;
import com.hzu.feirty.HomeWork.fragment.FunctionFragment.MyHandler;
import java.io.InputStream;
import java.util.ArrayList;

import javax.mail.Store;

public class MailApplication extends Application {
    private static Context mContext;
    private Store store;
    private MyHandler handler = null;
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
    // set方法
    public void setHandler(MyHandler handler) {
        this.handler = handler;
    }

    // get方法
    public MyHandler getHandler() {
        return handler;
    }

}
