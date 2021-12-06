package org.techtown.northkorean_memorization;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.Serializable;


public class Study extends AppCompatActivity {

    private final String tableName = "Words";

    Test_DatabaseAdapter.DatabaseHelper helper;
    Test_DatabaseAdapter databaseAdapter;
    String[] item = {"1. 일상생활어","2. IT용어","3. 은어"};
    String[] checkitem = new String[999];
    int[] record = new int[999];

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }

        setContentView(R.layout.study_activity_main);

        PreCreateDB.copyDB(this);



        databaseAdapter = new Test_DatabaseAdapter(this);
       final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.studyListViewFromDB();

        ListView listvContact = findViewById(R.id.lvContact);

       listvContact.setAdapter(simpleCursorAdapter);


        listvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int [] mark=  new int[1];
                int [] memo=  new int[1];
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);

                checkitem[position] = cursor.getString(cursor.getColumnIndex("_id"));
                AlertDialog.Builder dlg = new AlertDialog.Builder(Study.this);
                dlg.setTitle("선택");
                final String[] versionArray = new String[] {"즐겨찾기","암기"};
                final boolean[] checkArray = new boolean[]{false,false};

;
                record[position] = cursor.getInt(cursor.getColumnIndex("BookMark"));


                dlg.setMultiChoiceItems(versionArray, checkArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
// 재우가 볼 곳
                    if(record[position]==1){
                            if (record[position]==1) {
                                for (int i = 0; i < mark.length; i++) {
                                    mark[i] = Integer.valueOf(checkitem[position]); //메모에다가 클릭시 받은 index값 int 값으로 넣기
                                }
                            } else {
                                for (int i = 0; i < memo.length; i++) {
                                    memo[i] = Integer.valueOf(checkitem[position]); //메모에다가 클릭시 받은 index값 int 값으로 넣기
                                }
                            }updateDB1(mark, memo); }
                        else if(record[position]==0) {
                          if (which == 0) {
                              for (int i = 0; i < mark.length; i++) {
                                  mark[i] = Integer.valueOf(checkitem[position]); //메모에다가 클릭시 받은 index값 int 값으로 넣기
                              }
                          } else {
                              for (int i = 0; i < memo.length; i++) {
                                  memo[i] = Integer.valueOf(checkitem[position]); //메모에다가 클릭시 받은 index값 int 값으로 넣기
                              }
                          }
                          updateDB(mark, memo); // test 용으로 update db 에 memo 두개넣은거
                      }

                    }

                });
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.show();

            }

        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position ==0 ){
                    final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.studyListViewFromDB();
                    ListView listvContact = findViewById(R.id.lvContact);
                    listvContact.setAdapter(simpleCursorAdapter);
                }
                if(position == 1){
                    final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.combobox2ListViewFromDB();
                    ListView listvContact = findViewById(R.id.lvContact);
                    listvContact.setAdapter(simpleCursorAdapter);
                }
                else if(position==2){
                        final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.combobox3ListViewFromDB();
                        ListView listvContact = findViewById(R.id.lvContact);
                        listvContact.setAdapter(simpleCursorAdapter);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        TextView hide = (TextView) findViewById(R.id.hide);
        TextView hide2 = (TextView) findViewById(R.id.hide2);
        Button but1 = (Button) findViewById(R.id.but1);
        Button but2 = (Button) findViewById(R.id.but2);
        but1.setOnClickListener(new View.OnClickListener() {
            boolean btn = true;
            @Override
            public void onClick(View v) {
                if (btn == true) {
                    hide.setVisibility(v.VISIBLE);
                    but1.setText("INVISIBLE");
                    btn = false;
                } else {
                    hide.setVisibility(v.INVISIBLE);
                    but1.setText("VISIBLE");
                    btn = true;
                }
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            boolean btn2 = true;

            @Override
            public void onClick(View v) {
                if (btn2 == true) {
                    hide2.setVisibility(v.VISIBLE);
                    but2.setText("INVISIBLE");
                    btn2 = false;
                } else {
                    hide2.setVisibility(v.INVISIBLE);
                    but2.setText("VISIBLE");
                    btn2 = true;
                }
            }
        });


    }


    public Boolean updateDB(int[] bookMark, int[] memorized) {

        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db != null) {

            for (int i = 0; i < 1; ++i) {
                updateSQL(db, tableName, "BookMark", bookMark[i], 1);
                Log.d("Test_Test_updateDB", bookMark[i] + "th BookMark change into 1");
            }
            for (int i = 0; i < 1; ++i) {
                updateSQL(db, tableName, "Memorized", memorized[i], 1);
                Log.d("Test_Test_updateDB", memorized[i] + "th Memorized change into 1");
            }
            db.close();
        }
        else
            return false;
        return true;
    }
    public Boolean updateDB1(int[] bookMark, int[] memorized) {

        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db != null) {

            for (int i = 0; i < 1; ++i) {
                updateSQL(db, tableName, "BookMark", bookMark[i], 0);
                Log.d("Test_Test_updateDB", bookMark[i] + "th BookMark change into 0");
            }
            for (int i = 0; i < 1; ++i) {
                updateSQL(db, tableName, "Memorized", memorized[i], 0);
                Log.d("Test_Test_updateDB", memorized[i] + "th Memorized change into 0");
            }
            db.close();
        }
        else
            return false;
        return true;
    }
    /**
     * SQL문을 함수형태로 좀더 쉽게 만든건데 쉽게 느껴지려나..? 나중에도 쓸것같아서 만들어 둠
     * db의 inputTableName에서 id번째 행의 what 속성을 Value로 바꿈
     */
    private void updateSQL(SQLiteDatabase db, String inputTableName, String what, int id, int value) {
        db.execSQL("UPDATE " + tableName + " SET " + what +  "= "+value+" where _id = " + id);
    }






}


