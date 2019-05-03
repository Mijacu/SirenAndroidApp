package edu.utep.cs.sirenandroidapp.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.sirenandroidapp.Model.Video;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG ="SirenApp";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "SirenDB";
    private static final String TODO_TABLE = "Videos";

    private static final String KEY_ID = "_id";
    private static final String KEY_PATH = "path";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";

    public DatabaseHelper(Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TODO_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PATH + " TEXT, "
                + KEY_NAME + " TEXT, "
                + KEY_DATE + " TEXT" + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(database);
    }

    public void addVideo(Video video) {
        Log.d(TAG, "addVideo Method");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PATH, video.getPath()); //item name
        values.put(KEY_NAME, video.getName()); //item name
        values.put(KEY_DATE, video.getDate()); // item date
        long id = db.insert(TODO_TABLE, null, values);
        video.setId((int) id);
        db.close();
    }

    public List<Video> videosList() {
        List<Video> videos=new ArrayList<Video>();
        String selectQuery = "SELECT * FROM " + TODO_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String path = cursor.getString(1);
                String name = cursor.getString(2);
                String date=cursor.getString(3);
                Video video= new Video(path,name, date);
                video.setId(id);
                videos.add(video);
            } while (cursor.moveToNext());
        }
        return videos;
    }


    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, null, new String[]{});
        db.close();
    }

    public void removeVideo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, KEY_ID + " = ?", new String[] { Integer.toString(id) } );
        db.close();
    }

    public void update(Video video) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PATH, video.getPath()); //video name
        values.put(KEY_NAME, video.getName()); //video name
        values.put(KEY_DATE, video.getDate()); //video date
        db.update(TODO_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(video.getId())});
        db.close();
    }
}