package www.sumanmyon.com.fitnessapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class LoginAndRegisterDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitness.db";   //not case sensetive
    public static final String TABLE_NAME = "login_n_register_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";

    String ID;

    public LoginAndRegisterDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        // SQLiteDatabase db = this.getWritableDatabase(); //we can read and write database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create db
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //TODO Insert data in database
    public boolean insert(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);


        long result = db.insert(TABLE_NAME, null, contentValues);
        //db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    //TODO check
    public boolean check(String name, String pass){
        //array of columns to fetch
        String[] columns = {COL_1};

        SQLiteDatabase db = this.getWritableDatabase();

        //select
        String selection = COL_2 +" = ? "+ " and "+ COL_3 + " = ? ";
        String[] selectionArguments = {name,pass};

        //SELECT id FROM student_table
        // WHERE user_email = 'jack@androidtutorialshub.com' and password = 'sdnjhbhcxgx';
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArguments,null,null,null);
        int cursorCount = cursor.getCount();

        if(cursor.getCount() == 0){  //TODO there is no data in database
            //showMessage("There is no data in database");
            //showAllData("Error", "There is no data in database");
        }else {
            //TODO if cursor is at first of row or not
            if(!cursor.isFirst()){
                cursor.moveToFirst();
            }

            ID = cursor.getString(0);
        }

        cursor.close();
        //db.close();

        if(cursorCount > 0)
            return true;
        else
            return false;

    }

    public String getID() {
        return ID;
    }
}
