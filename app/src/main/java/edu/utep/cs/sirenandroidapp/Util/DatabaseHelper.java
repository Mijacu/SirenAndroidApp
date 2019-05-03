package edu.utep.cs.sirenandroidapp.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "SirenDB";
    private static final String TODO_TABLE = "items";

    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";

    public DatabaseHelper(Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TODO_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_DATE + " NUMERIC" + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(database);
    }

    public void addVideo(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName()); //item name
        values.put(KEY_URL, item.getUrl()); // item url
        values.put(KEY_CURRENTPRICE, item.getCurrentPrice()); // item current price
        values.put(KEY_INITIALPRICE, item.getInitialPrice()); // item initial price
        values.put(KEY_DATE, item.getDate()); // item date
        long id = db.insert(TODO_TABLE, null, values);
        item.setId((int) id);
        db.close();
    }

    public List<Item> allItems() {
        List<Item> items=new ArrayList<Item>();
        String selectQuery = "SELECT * FROM " + TODO_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String url = cursor.getString(2);
                double currentPrice = cursor.getDouble(3);
                double initialPrice = cursor.getDouble(4);
                String date=cursor.getString(5);
                Item item = new Item(name, url, initialPrice);
                item.setId(id);
                item.setCurrentPrice(currentPrice);
                item.getChange();
                item.setDate(date);
                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }


    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, null, new String[]{});
        db.close();
    }

    public void removeItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, KEY_ID + " = ?", new String[] { Integer.toString(id) } );
        db.close();
    }

    public void update(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName()); //item name
        values.put(KEY_URL, item.getUrl()); // item url
        values.put(KEY_CURRENTPRICE, item.getCurrentPrice()); // item current price
        values.put(KEY_INITIALPRICE, item.getInitialPrice()); // item initial price
        values.put(KEY_DATE, item.getDate()); // item date
        db.update(TODO_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

}