package com.gamereview.util;

import com.gamereview.model.Profile;

public class UserSession {

    private static UserSession instance;
    private Profile user;


    private UserSession(Profile user) {
        this.user = user;
    }


    public static void initSession(Profile user) {
        if (instance == null) {
            instance = new UserSession(user);
        } else {
            instance.user = user;
        }
    }


    public static UserSession getInstance() {
        return instance;
    }

    public Profile getUser() {
        return user;
    }

    public static void cleanSession() {
        instance = null;
    }
}