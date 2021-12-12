package org.techtown.northkorean_memorization;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Test_SavedDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Test_Saved.db";
    private static final String TABLE_NAME = "TestSet";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ROWID = "_id";
    private static final String KEY_CORRECT = "Correct";

    private static final String CREATE_TABLE = "create table "+ TABLE_NAME+
            " ("+ KEY_ROWID+" integer primary key autoincrement, " + KEY_CORRECT + " integer)";
    private static final String DROP_TABLE = "drop table if exists "+ TABLE_NAME;
    private Context context;

    public Test_SavedDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
        }catch (SQLException e){
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}