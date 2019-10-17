package www.sumanmyon.com.fitnessapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MapDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitnewss3.db";   //not case sensetive
    public static final String TABLE_NAME = "map_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "map";
    public static final String COL_6 = "UID";

    public MapDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create db
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, map TEXT, UID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //TODO Insert data in database
    public boolean insert(String map, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, map);
        contentValues.put(COL_6, uid);

        long result = db.insert(TABLE_NAME, null, contentValues);
        //db.close();
        if (result == -1) return false;
        else return true;
    }

    //TODO Show/Get data from database
    public Cursor getAllData(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where uid=" + uid, null);
        return cursor;
    }
}
