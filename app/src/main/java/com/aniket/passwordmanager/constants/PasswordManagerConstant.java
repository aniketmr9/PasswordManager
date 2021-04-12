package com.aniket.passwordmanager.constants;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class PasswordManagerConstant {
    private Context applicationContext = null;
    private static Properties properties = null;
    public static String USER_CREATED = null;
    public static String USER_ALREADY_EXIST = null;
    public static String USER_CREATION_ERROR = null;
    public static String INCORRECT_USERNAME = null;
    public static String INCORRECT_PASSWORD = null;
    public static String USER_AUTHENTICATED = null;
    public static String ACCOUNT_NOT_EXIST = null;
    public static String GENERIC_ERROR = null;
    public static String ACCOUNT_ADDED_SUCCESS = null;
    public PasswordManagerConstant(Context applicationContext) {
        this.applicationContext = applicationContext;
        try {
            InputStream is = this.applicationContext.getAssets().open("PasswordManager.properties");
            properties = new Properties();
            properties.load(is);
            String value = properties.getProperty("test", "");
            System.out.println(value);
            is.close();
            USER_CREATED = properties.getProperty("USER_CREATED");
            USER_ALREADY_EXIST = properties.getProperty("USER_ALREADY_EXIST");
            USER_CREATION_ERROR = properties.getProperty("USER_CREATION_ERROR");
            INCORRECT_USERNAME = properties.getProperty("INCORRECT_USERNAME");
            INCORRECT_PASSWORD = properties.getProperty("INCORRECT_PASSWORD");
            USER_AUTHENTICATED = properties.getProperty("USER_AUTHENTICATED");
            ACCOUNT_NOT_EXIST = properties.getProperty("ACCOUNT_NOT_EXIST");
            GENERIC_ERROR = properties.getProperty("GENERIC_ERROR");
            ACCOUNT_ADDED_SUCCESS = properties.getProperty("ACCOUNT_ADDED_SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
