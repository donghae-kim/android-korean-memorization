package org.techtown.northkorean_memorization;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Test_ReviewFragment extends Fragment {
    private int[] indexSet;
    private int[] isCorrect;
    private Button exitBtn;

    private final String tableName = "Words";
    private final String databaseName = "Words.db";

    test_Review_DriveAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_review, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.custom_listview);

        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        Bundle bundle = getArguments();
        indexSet = bundle.getIntArray("indexSet");
        isCorrect = bundle.getIntArray("isCorrect");

        ArrayList<Test_DriveVO> data = new ArrayList<>();
        for (int i = 0; i < indexSet.length; ++i) {
            Cursor cursor = db.rawQuery("select * from " + tableName + " where _id = " + indexSet[i], null);
            cursor.moveToNext();
            int size = cursor.getCount();
            Log.d("Test_Test", "ProblemSet " + size );

            Test_DriveVO vo = new Test_DriveVO();
            vo.isCorrect = (isCorrect[i] != 0)? true : false;
            vo.num = i;
            vo.north = cursor.getString(1);
            vo.south = cursor.getString(2);
            vo.isMemory = (cursor.getInt(6) != 0)? true : false;
            vo.isBookMark = (cursor.getInt(5) != 0)? true : false;
            vo.id = cursor.getInt(0);
            data.add(vo);
        }

        db.close();

        adapter = new test_Review_DriveAdapter(getContext(), R.layout.test_review_item, data);
        listView.setAdapter(adapter);
    }
}
