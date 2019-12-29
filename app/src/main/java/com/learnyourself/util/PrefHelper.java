package com.learnyourself.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.learnyourself.grewords.R;

/**
 * Created by Nha on 11/30/2015.
 */
public class PrefHelper {
    public static final String PREF_IS_DB_READY = "is_db_ready";
    public static final String CURRENT_SECTION = "current_section";
    public static final String CURRENT_PART = "current_part";
    public static final String IS_TABLET = "is_tablet";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PrefHelper(Context context) {
        String prefsFile = context.getString(R.string.pref_name);
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }

    public void savePref(String key, Object value) {
        delete(key);
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean isPrefExists(String key) {
        return sharedPreferences.contains(key);
    }
}
