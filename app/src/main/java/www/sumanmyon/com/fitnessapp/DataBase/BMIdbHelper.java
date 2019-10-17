package www.sumanmyon.com.fitnessapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BMIdbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitnewss4.db";   //not case sensetive
    public static final String TABLE_NAME = "BMI_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "height";
    public static final String COL_3 = "weight";
    public static final String COL_4 = "BMI";
    public static final String COL_6 = "UID";

    public BMIdbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create db
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, height TEXT, weight TEXT, BMI TEXT, UID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //TODO Insert data in database
    public boolean insert(String height, String weight, String BMI, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, height);
        contentValues.put(COL_3, weight);
        contentValues.put(COL_4, BMI);
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
