package org.techtown.northkorean_memorization;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Study extends AppCompatActivity {

    Test_DatabaseAdapter databaseAdapter;
    String[] item = {"1. 일상생활어","2. IT용어","3. 은어"};
    ArrayList<String> listItem;

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
                String name = cursor.getString(0);

            }
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int poistion, long id) {
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




