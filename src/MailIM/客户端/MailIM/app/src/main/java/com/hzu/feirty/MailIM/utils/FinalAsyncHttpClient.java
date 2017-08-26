package com.hzu.feirty.MailIM.utils;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import com.loopj.android.http.AsyncHttpClient;

public class FinalAsyncHttpClient {

    AsyncHttpClient client;

    public FinalAsyncHttpClient() {
        client = new AsyncHttpClient();
        client.setConnectTimeout(5);//5s超时
        if (CookieUtil.getCookies() != null) {//每次请求都要带上cookie
            BasicCookieStore bcs = new BasicCookieStore();
            bcs.addCookies(CookieUtil.getCookies().toArray(
                    new Cookie[CookieUtil.getCookies().size()]));
            client.setCookieStore(bcs);
        }
    }
    public AsyncHttpClient getAsyncHttpClient(){
        return this.client;
    }
}

