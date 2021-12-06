package org.techtown.northkorean_memorization;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.os.Bundle;
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



public class Study extends AppCompatActivity {
    Test_DatabaseAdapter.DatabaseHelper helper;
    Test_DatabaseAdapter databaseAdapter;
    String[] item = {"1. 일상생활어","2. IT용어","3. 은어"};
    String[] checkitem = new String[999];
    int [] memo=  new int[1];
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_activity_main);

        PreCreateDB.copyDB(this);



        databaseAdapter = new Test_DatabaseAdapter(this);
       final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.populateListViewFromDB();

        ListView listvContact = findViewById(R.id.lvContact);

       listvContact.setAdapter(simpleCursorAdapter);


        listvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);

                checkitem[position] = cursor.getString(cursor.getColumnIndex("_id"));

                AlertDialog.Builder dlg = new AlertDialog.Builder(Study.this);
                dlg.setTitle("선택");
                final String[] versionArray = new String[] {"즐겨찾기"};
                final boolean[] checkArray = new boolean[]{false};

                dlg.setMultiChoiceItems(versionArray, checkArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkArray[which]=isChecked;
                        System.out.println(checkitem[position]);

                        for (int i=0; i<memo.length; i++) {
                            memo[i] = Integer.valueOf(checkitem[position]); //메모에다가 클릭시 받은 index값 int 값으로 넣기
                            System.out.println(memo[i]); //logcat

                            CodeConductor cod = new CodeConductor();
                           // cod.updateDB(memo,memo); // test 용으로 update db 에 memo 두개넣은거
//********************************************************************************************************** codeconductor 로 넘기는부분
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

                if(position == 1){

                    Intent intent = new Intent(Study.this, Study.class);
                    startActivity(intent);
                    finish();

                }
                else if(position==2){
                    SQLiteDatabase Db1=helper.getReadableDatabase();

                    Cursor cursor = Db1.rawQuery("SELECT * FROM Words",null);


                }
                else if(position==3)
                { Intent intent = new Intent(Study.this, Study.class);
                startActivity(intent);
                finish();}




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



}




