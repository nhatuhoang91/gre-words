package com.learnyourself.grewords;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.learnyourself.db.DBHelper;
import com.learnyourself.util.PrefHelper;

/**
 * Created by Nha on 11/30/2015.
 */
public class App extends MultiDexApplication {

    private static final String TAG = App.class.getSimpleName();
    private static App instance;

    private PrefHelper prefsHelper;
    private DBHelper dbHelper;
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    public PrefHelper getPrefsHelper() {
        return prefsHelper;
    }
    public DBHelper getDbHelper(){return dbHelper;}
    private void initApplication(){
        instance = this;
        prefsHelper = new PrefHelper(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());
        dbHelper.initDB();
    }
}
