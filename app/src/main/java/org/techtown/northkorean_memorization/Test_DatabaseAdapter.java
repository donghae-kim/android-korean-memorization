package org.techtown.northkorean_memorization;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class Test_DatabaseAdapter {


        DatabaseHelper helper;
        SQLiteDatabase db;
        Context context;

        public Test_DatabaseAdapter(Context context) {
            helper = new DatabaseHelper(context);
            db = helper.getWritableDatabase();
            this.context = context;
        }

    public SimpleCursorAdapter MyWordListViewFromDB() {
        String columns[] = {DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2};

        Cursor cursor = db.rawQuery("SELECT * FROM Words Where BOOKMARK == 1", null);

        String[] fromFieldNames = new String[]{
                DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2

        };

        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_NORTH, R.id.item_SOUTH, R.id.item_FIELD, R.id.item_BOOKMARK, R.id.item_MEMORIZED,R.id.item_TEMP1,R.id.item_TEMP2};


        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.study_single_item,
                cursor,
                fromFieldNames,
                toViewIDs

        );

        return contactAdapter;

    }
        public SimpleCursorAdapter studyListViewFromDB() {
            String columns[] = {DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2};
            Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
            String[] fromFieldNames = new String[]{
                    DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2
            };
            int[] toViewIDs = new int[]{R.id.item_id, R.id.item_NORTH, R.id.item_SOUTH, R.id.item_FIELD, R.id.item_BOOKMARK, R.id.item_MEMORIZED,R.id.item_TEMP1,R.id.item_TEMP2};
            SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                    context,
                    R.layout.study_single_item,
                    cursor,
                    fromFieldNames,
                    toViewIDs
            );
            return contactAdapter;
        }

    public SimpleCursorAdapter combobox2ListViewFromDB() {
        String columns[] = {DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2};

        Cursor cursor = db.rawQuery("SELECT * FROM Words Where Field==2", null);
        String[] fromFieldNames = new String[]{
                DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_NORTH, R.id.item_SOUTH, R.id.item_FIELD, R.id.item_BOOKMARK, R.id.item_MEMORIZED,R.id.item_TEMP1,R.id.item_TEMP2};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.study_single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }
    public SimpleCursorAdapter combobox3ListViewFromDB() {
        String columns[] = {DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2};

        Cursor cursor = db.rawQuery("SELECT * FROM Words Where Field==3", null);
        String[] fromFieldNames = new String[]{
                DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_NORTH, R.id.item_SOUTH, R.id.item_FIELD, R.id.item_BOOKMARK, R.id.item_MEMORIZED,R.id.item_TEMP1,R.id.item_TEMP2};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.study_single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }
    public SimpleCursorAdapter graphmake() {
        String columns[] = {DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2};

        Cursor cursor = db.rawQuery("SELECT * FROM Words Where (Memorized==1 AND Field==3)", null);
        String[] fromFieldNames = new String[]{
                DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NORTH, DatabaseHelper.KEY_SOUTH, DatabaseHelper.KEY_FIELD, DatabaseHelper.KEY_BOOKMARK, DatabaseHelper.KEY_MEMORIZED, DatabaseHelper.KEY_TEMP, DatabaseHelper.KEY_TEMP2
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_NORTH, R.id.item_SOUTH, R.id.item_FIELD, R.id.item_BOOKMARK, R.id.item_MEMORIZED,R.id.item_TEMP1,R.id.item_TEMP2};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.study_single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {

            private static final String DATABASE_NAME = "Words.db";
            private static final String TABLE_NAME = "Words";
            private static final int DATABASE_VERSION = 1;
            private static final String KEY_ROWID = "_id";
            private static final String KEY_NORTH="North";
            private static final String KEY_SOUTH = "South";
            private static final String KEY_TEMP = "Temp1";
            private static final String KEY_FIELD = "Field";
            private static final String KEY_BOOKMARK = "BookMark";
            private static final String KEY_MEMORIZED = "Memorized";
            private static final String KEY_TEMP2= "Temp2";

            private static final String CREATE_TABLE = "create table "+ TABLE_NAME+
                    " ("+ KEY_ROWID+" integer primary key autoincrement, "+ KEY_NORTH + " text, "+ KEY_SOUTH+ " text, "+ KEY_TEMP + " text, "+ KEY_FIELD + " integer, "+ KEY_BOOKMARK + " integer, "+ KEY_MEMORIZED + " integer, "+ KEY_TEMP2 + " integer)";
            private static final String DROP_TABLE = "drop table if exists "+ TABLE_NAME;
            private Context context;

            public DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
                this.context = context;

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
    }

