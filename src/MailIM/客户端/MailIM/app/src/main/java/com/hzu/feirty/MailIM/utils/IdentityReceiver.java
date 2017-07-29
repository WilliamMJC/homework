package com.hzu.feirty.MailIM.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-6-28.
 */

public class IdentityReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            boolean isIdentity = NetUtil.isIdentity(context);
            if (isIdentity) {
                Toast.makeText(context, "已经设置身份", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "还没设置身份", Toast.LENGTH_LONG).show();
            }
        }
    }
}
