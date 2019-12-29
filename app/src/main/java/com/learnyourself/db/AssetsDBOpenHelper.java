package com.learnyourself.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nha on 11/30/2015.
 */
public class AssetsDBOpenHelper {

    private static final String TAG = "AssetsDBOpenHelper";
    private static final String DB_NAME = "greword.db3";
    private Context context;

    public AssetsDBOpenHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase storeDatabase() {
        String path = "/data/data/com.learnyourself.grewords/databases";
        File pathDb = new File(path);
        try {
            if (!pathDb.exists()){
                pathDb.mkdir();
            }
            if (!new File(path + "/" + DB_NAME).exists()) {
                copy(path);
            }
        } catch (IOException e) {
           // Log.d("IOException", e.getMessage());
        }
        return SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    public SQLiteDatabase openDB(){
        return SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getPath(),
                null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copy(String path) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);

        FileOutputStream fos = new FileOutputStream(path + "/" + DB_NAME);
        byte buffer[] = new byte[1024];
        int length;

        while ((length = is.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        is.close();
        fos.flush();
        fos.close();
    }
}
