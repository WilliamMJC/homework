package com.hzu.feirty.MailIM.utils;

import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017-7-4.
 */

public class CookieUtil {
    private static List<Cookie> cookies;
    public static List<Cookie> getCookies() {
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    public static void setCookies(List<Cookie> cookies) {
        CookieUtil.cookies = cookies;
    }
}
