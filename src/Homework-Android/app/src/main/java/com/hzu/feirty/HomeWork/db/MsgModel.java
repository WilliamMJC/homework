package com.hzu.feirty.HomeWork.db;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class MsgModel implements Serializable {
    public static final int MSG_FORM = -1;
    public static final int MSG_TO = 1;

    public int type;
    public Object object;
    public MsgModel(int type, Object object) {
        this.type = type;
        this.object = object;
    }
}
