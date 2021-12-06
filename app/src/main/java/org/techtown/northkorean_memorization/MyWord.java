package org.techtown.northkorean_memorization;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MyWord extends AppCompatActivity {

    Test_DatabaseAdapter.DatabaseHelper helper;
    Test_DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myword);
        PreCreateDB.copyDB(this);
        databaseAdapter = new Test_DatabaseAdapter(this);

        final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.MyWordListViewFromDB();

        ListView listvContact = findViewById(R.id.lvmyword);

        listvContact.setAdapter(simpleCursorAdapter);
    }



}
