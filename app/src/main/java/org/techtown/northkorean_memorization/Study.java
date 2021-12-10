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
    int[] record_mark = new int[999];
    int[] record_memo = new int[999];
    int spinnerposi=0;

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
                final SimpleCursorAdapter simpleCursorAdapter1 = databaseAdapter.studyListViewFromDB();

                final SimpleCursorAdapter simpleCursorAdapter2 = databaseAdapter.combobox2ListViewFromDB();
                final SimpleCursorAdapter simpleCursorAdapter3 = databaseAdapter.combobox3ListViewFromDB();
                System.out.println(spinnerposi+"야야야야ㅑ야양");

                Cursor cursor;
                if(spinnerposi == 0)
                {   listvContact.setAdapter(simpleCursorAdapter1);
                  cursor = (Cursor) simpleCursorAdapter1.getItem(position);
                    System.out.println(spinnerposi+"야야야야ㅑ야양");
                }
                else if( spinnerposi == 1)
                {   listvContact.setAdapter(simpleCursorAdapter2);
                    cursor = (Cursor) simpleCursorAdapter2.getItem(position);
                    System.out.println(spinnerposi+"야야야야ㅑ야양");
                }
                else
                {   listvContact.setAdapter(simpleCursorAdapter3);
                    cursor = (Cursor) simpleCursorAdapter3.getItem(position);
                }

                checkitem[position] = cursor.getString(cursor.getColumnIndex("_id"));
                AlertDialog.Builder dlg = new AlertDialog.Builder(Study.this);
                dlg.setTitle("선택");
                final String[] versionArray = new String[] {"즐겨찾기","암기"};
                final boolean[] checkArray = new boolean[]{false,false};
                record_memo[position]= cursor.getInt(cursor.getColumnIndex("Memorized"));
                if(record_memo[position]==1)
                    checkArray[1]=true;
                else
                    checkArray[1]=false;

               record_mark[position] = cursor.getInt(cursor.getColumnIndex("BookMark"));
                if(record_mark[position]==1)
                    checkArray[0]=true;
                    else
                    checkArray[0]=false;




                dlg.setMultiChoiceItems(versionArray, checkArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {


                        if(which==0 ){
                            if (record_mark[position]==1) {
                                for (int i = 0; i < mark.length; i++) {
                                    mark[i] = Integer.valueOf(checkitem[position]);
                                }                                    updateDB1(mark, 0);

                            }
                            else if(record_mark[position]==0) {
                                for (int i = 0; i < memo.length; i++) {
                                    mark[i] = Integer.valueOf(checkitem[position]);
                                }                                    updateDB1(mark, 1);

                            } return;}

                        else if(which ==1) {
                          if (record_memo[position]==1) {
                              for (int i = 0; i < mark.length; i++) {
                                  memo[i] = Integer.valueOf(checkitem[position]);
                              }                                  updateDB(memo, 0);

                          } else if(record_memo[position]==0) {
                              for (int i = 0; i < memo.length; i++) {
                                  memo[i] = Integer.valueOf(checkitem[position]);

                              }                                  updateDB(memo, 1);
                          }
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
                    spinnerposi=0;

                }
                if(position == 1){
                    final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.combobox2ListViewFromDB();
                    ListView listvContact = findViewById(R.id.lvContact);
                    listvContact.setAdapter(simpleCursorAdapter);
                    spinnerposi=1;


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
                    but1.setText("문화어 보이기");
                    btn = false;
                } else {
                    hide.setVisibility(v.INVISIBLE);
                    but1.setText("문화어 숨기기");
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
                    but2.setText("표준어 보이기");
                    btn2 = false;
                } else {
                    hide2.setVisibility(v.INVISIBLE);
                    but2.setText("표준어 숨기기");
                    btn2 = true;
                }
            }
        });


    }


    public Boolean updateDB(int[] memorized, int k) {

        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db != null) {

            for (int i = 0; i < 1; ++i) {
                updateSQL(db, tableName, "Memorized", memorized[i], k);
                Log.d("Test_Test_updateDB", memorized[i] + "th memo change into "+k);
            }

            db.close();
        }
        else
            return false;
        return true;
    }
    public Boolean updateDB1(int[] bookMark, int k) {

        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db != null) {

            for (int i = 0; i < 1; ++i) {
                updateSQL(db, tableName, "BookMark", bookMark[i], k);
                Log.d("Test_Test_updateDB", bookMark[i] + "th BookMark change into "+k);
            }

            db.close();
        }
        else
            return false;
        return true;
    }

    private void updateSQL(SQLiteDatabase db, String inputTableName, String what, int id, int value) {
        db.execSQL("UPDATE " + tableName + " SET " + what +  "= "+value+" where _id = " + id);
    }






}


