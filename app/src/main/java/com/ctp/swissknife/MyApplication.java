package com.ctp.swissknife;

import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;

/**
 * Created by CTP on 2/5/2017.
 */

public class MyApplication extends Application {
    private static Context context;
    private static Context ContactsContext;
    private static String name;
    private static String number;
    private static FragmentManager contactsFM;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static Context getContactsContext() {
        return ContactsContext;
    }

    public static void setContactsContext(Context contactsContext) {
        ContactsContext = contactsContext;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        MyApplication.name = name;
    }

    public static String getNumber() {
        return number;
    }

    public static void setNumber(String number) {
        MyApplication.number = number;
    }

    public static FragmentManager getContactsFM() {
        return contactsFM;
    }

    public static void setContactsFM(FragmentManager contactsFM) {
        MyApplication.contactsFM = contactsFM;
    }
}
