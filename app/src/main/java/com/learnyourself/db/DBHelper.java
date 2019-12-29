package com.learnyourself.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.learnyourself.grewords.App;
import com.learnyourself.object.StatsObject;
import com.learnyourself.object.Word;
import com.learnyourself.util.PrefHelper;

import java.util.ArrayList;

/**
 * Created by Nha on 11/30/2015.
 *
 * Always remember _id in sqlite
 */
public class DBHelper {

    Context context;
    SQLiteDatabase db;
    AssetsDBOpenHelper assetDB;
    public DBHelper(Context context){
        this.context= context;
        assetDB =  new AssetsDBOpenHelper(context);
    }

    public SQLiteDatabase initDB(){
        Boolean is_db_ready = App.getInstance().getPrefsHelper().getPref(PrefHelper.PREF_IS_DB_READY, false);
        if(!is_db_ready){
            db = assetDB.storeDatabase();
            if(db != null){
                App.getInstance().getPrefsHelper().savePref(PrefHelper.PREF_IS_DB_READY, true);
            }
        }else{
            db = assetDB.openDB();
        }
        return db;
    }

    private ArrayList<String> getName(String sql,String...selectionArgs){
        ArrayList<String> contents = new ArrayList<String>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while(c.moveToNext()){
            contents.add(c.getString(2));
        }
        c.close();
        return contents;
    }
    public ArrayList<String> danhSach() {
        String sql = "select * from words ";
        return getName(sql);
    }

    public int count(String sect){
        Log.d("DBHELPER","count(): select _id from words where is_remember = 1 and _id >='\"+begin+\"' and _id <= '\"+end+\"';");
        String sql = "select _id from words where is_remember = 1 and sect= '"+sect+"';";

        Cursor c = db.rawQuery(sql, null);
        Log.d("DBHelper","count : "+c.getCount());
        return c.getCount();
    }

    public ArrayList<String> getWordName(String section){
        ArrayList<String> result = new ArrayList<>();
        String name;
        String sql;
        if(section.equals("a")){
            sql = "select name from words where sect ='"+section+"';";
            Log.d("DBHelper","sql : "+ sql);
        }else{
            sql = "select name from words where sect ='"+section+"';";
            Log.d("DBHelper","sql : "+ sql);
        }

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            name = c.getString(c.getColumnIndex("name"));
            result.add(name);
        }

        return result;
    }

   public ArrayList<Word> getWord(String section){
        ArrayList<Word> result = new ArrayList<>();
        String name;
        String meaning;
        String example;
        String synonym;
        boolean isRemember;
       String sql;
       if(section.equals("a")){
           sql = "select * from words where sect ='"+section+"' and not(is_remember) ;";
           Log.d("DBHelper","sql : "+ sql);
       }else{
           sql = "select * from words where sect ='"+section+"' and not(is_remember) ;";
           Log.d("DBHelper","sql : "+ sql);
       }

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            name = c.getString(c.getColumnIndex("name"));
            meaning = c.getString(c.getColumnIndex("description"));
            example = c.getString(c.getColumnIndex("example"));
            synonym = c.getString(c.getColumnIndex("synonym"));
            isRemember = false;
            Word word = new Word();
            word.setName(name);
            word.setMeaning(meaning);
            word.setExample(example);
            word.setSynonym(synonym);
            word.setIsRemember(isRemember);
            result.add(word);
        }

       return result;
    }

    public void setRemember(final String wordName){
       // new Thread(new Runnable() {
         ///   @Override
           // public void run() {
                String sql = "update words set is_remember = 1 where name = '"+wordName+"'";
                db.execSQL(sql);
            //}
        //}).start();
    }
    public void resetPart(final String sect){
        //new Thread(new Runnable() {
          //  @Override
           // public void run() {
                String sql = "update words set is_remember = 0 where sect = '"+sect+"'";
                db.execSQL(sql);
            //}
        //}).start();
    }

    public Cursor getSearchWord(String nameSearch){
        String sql = "select * from words where name = '"+nameSearch+"';";
        Cursor c = db.rawQuery(sql, null);
       /* name = c.getString(c.getColumnIndex("name"));
        meaning = c.getString(c.getColumnIndex("description"));
        example = c.getString(c.getColumnIndex("example"));
        synonym = c.getString(c.getColumnIndex("synonym"));
        isRemember = c.getInt(c.getColumnIndex("is_remember")) >0;
        Word word = new Word(name,meaning,example,synonym,isRemember);
*/
        return c;
    }
    public Cursor getSuggestWord(String suggestWord){
        Log.d("GetSuggestWord",suggestWord);
        String sql = "select * from words where name like '"+suggestWord+"%';";
        Cursor c = db.rawQuery(sql, null);
        if(!c.moveToFirst())
        {
            Log.d("GetSuggestWord","empty");
        }
        return c;
    }

    public void updateView(final String wordName){
        //Log.d("DBHelper", "increase view : " + wordName);
     //   new Thread(new Runnable() {
     //       @Override
       //     public void run() {
                String sql = "update words set view = view+1 where name = '"+wordName+"'";
                db.execSQL(sql);
       //     }
       // }).start();
    }

    public void updateStats(String date, long elapseTime , int numView){
        String sql;
        String check = "select _id from stats where _date = '"+date+"'";
        Cursor c = db.rawQuery(check, null);
        if(!c.moveToFirst())
        {
           // Log.d("updateStats()","insert");
            sql = "insert into stats(_date,_time,num_view) values('"+date+"', "+elapseTime+", "+numView+");";
        }else{
          //  Log.d("updateStats()","update");
            sql = "update stats set _time = _time + "+elapseTime+", num_view = num_view + "+numView+" where _date = '"+date+"';";
        }
        db.execSQL(sql);
    }

    public ArrayList<StatsObject> getStats(){
        String date;
        int time, view;
        ArrayList<StatsObject> result= new ArrayList<>();
        String sql = "select * from stats order by _id desc limit 5;";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()){
            date = c.getString(c.getColumnIndex("_date"));
            time = c.getInt(c.getColumnIndex("_time"));
            view = c.getInt(c.getColumnIndex("num_view"));
            StatsObject statsObject = new StatsObject();
            statsObject.setDate(date);
            statsObject.setTime(time);
            statsObject.setView(view);
            result.add(statsObject);
        }

        return result;
    }

   /* public void getStatsDemo(){
        String date;
        int time, view;
        ArrayList<StatsObject> result= new ArrayList<>();
        String sql = "select * from stats ;";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()){
            date = c.getString(c.getColumnIndex("_date"));
            time = c.getInt(c.getColumnIndex("_time"));
            view = c.getInt(c.getColumnIndex("num_view"));
            Log.d("bhjhaa","date : "+date);
            Log.d("bhjhaa","time : "+time);

        }
    }*/

    public int getViewCount(String wordName){
        int result=0;
        String sql;
        sql = "select view from words where name = '"+wordName+"'";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()){
            result = c.getInt(c.getColumnIndex("view"));
        }
        return result;
    }
}

