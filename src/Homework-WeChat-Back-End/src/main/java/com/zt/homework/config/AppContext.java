package com.zt.homework.config;

public class AppContext implements AutoCloseable {
    private static final ThreadLocal<Integer> CURRENT_CONSUMER_USER_ID = new ThreadLocal<>();

    public AppContext(Integer userId) {
        CURRENT_CONSUMER_USER_ID.set(userId);
    }

    @Override
    public void close() throws Exception {
        CURRENT_CONSUMER_USER_ID.remove();
    }

    public static Integer getCurrentUserId() {
        return CURRENT_CONSUMER_USER_ID.get();
    }
}
