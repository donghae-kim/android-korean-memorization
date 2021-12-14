package org.techtown.northkorean_memorization;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

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
        final SimpleCursorAdapter first_graph = databaseAdapter.graphmake(); //1번 data
        double first_language = first_graph.getCount();
        final SimpleCursorAdapter first_graph_total = databaseAdapter.studyListViewFromDB();
        double first_language_total = first_graph_total.getCount();

        final SimpleCursorAdapter second_graph = databaseAdapter.graphmake1(); //2번 data
        double second_language = second_graph.getCount();
        System.out.println(second_language);
        final SimpleCursorAdapter second_graph_total = databaseAdapter.combobox2ListViewFromDB();
        double second_language_total = second_graph_total.getCount();
        System.out.println(second_language_total);

        final SimpleCursorAdapter third_graph = databaseAdapter.graphmake2(); //2번 data
        double third_language = second_graph.getCount();
        final SimpleCursorAdapter third_graph_total = databaseAdapter.combobox3ListViewFromDB();
        double third_language_total = third_graph_total.getCount();


        final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.MyWordListViewFromDB();

        ListView listvContact = findViewById(R.id.lvmyword);

        listvContact.setAdapter(simpleCursorAdapter);
        System.out.println(first_language);
        System.out.println(first_language_total);

        TextView top = (TextView) findViewById(R.id.top);
        double first_result = ((first_language/first_language_total)*100);
        String first_result1 = String.format("%.2f ", first_result);

        double second_result = ((second_language/second_language_total)*100);
        String second_result1 = String.format("%.2f ", second_result);

        double third_result = ((third_language/third_language_total)*100);
        String third_result1 = String.format("%.2f ", third_result);


        top.setText("1. 일반 용어의 암기율   " + first_result1 +"%\n\n"+ "2. IT 용어의 암기율  " + second_result1 +"%\n\n"+ "3. 은어용어의 암기율   " + third_result1 +"%");

    }




}
