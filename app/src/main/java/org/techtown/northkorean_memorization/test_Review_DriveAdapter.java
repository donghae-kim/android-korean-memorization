package org.techtown.northkorean_memorization;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class test_Review_DriveAdapter extends android.widget.ArrayAdapter<Test_DriveVO> {
    Context context;
    int resId;
    ArrayList<Test_DriveVO> data;

    private final String tableName = "Words";
    private final String databaseName = "Words.db";

    public test_Review_DriveAdapter(Context context, int resId, ArrayList<Test_DriveVO> data) {
        super(context, resId);
        this.context = context;
        this.resId = resId;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        if (converView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(resId, null);
            Test_DriveHolder holder = new Test_DriveHolder(converView);
            converView.setTag(holder);
        }

        Test_DriveHolder holder = (Test_DriveHolder) converView.getTag();

        ImageView correctImg = holder.correctImg;
        TextView num = holder.num;
        TextView north = holder.north;
        TextView south = holder.south;
        CheckBox memory = holder.memory;
        CheckBox bookMark = holder.bookMark;

        final Test_DriveVO vo = data.get(position);

        num.setText("" + vo.num);
        north.setText(vo.north);
        south.setText(vo.south);

        Log.d("Test_Review", vo.isMemory + " " + vo.isBookMark);

        sss(vo.id, memory, bookMark);

        if (vo.isCorrect) {
            correctImg.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.correct_symbol, null));
            memory.setChecked(true);
            memoSet(1, vo.id);
        }
        else
            correctImg.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.uncorrect_symbol, null));


        bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = bookMark.isChecked()? 1 : 0;
                memoSet(value, vo.id);
                Log.d("Test_Review", "bookMark is saved : value = " + value);
            }
        });

        memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(getContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                int value = memory.isChecked()? 1 : 0;
                db.execSQL("UPDATE " + tableName + " SET Memorized = " + value + " where _id = " + vo.id);
                db.close();
                Log.d("Test_Review", "Momorized is saved : value = " + value);
            }
        });

        return converView;
    }

    private void memoSet(int value, int id) {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE " + tableName + " SET BookMark = " + value + " where _id = " + id);
        db.close();
    }

    void sss(int id, CheckBox memory, CheckBox bookMark) {
        int a = asd(id);

        if (a == 1) memory.setChecked(true);
        else if (a == 2) bookMark.setChecked(true);
        else if (a == 3) {
            memory.setChecked(true);
            bookMark.setChecked(true);
        }
    }


    private int asd(int id) {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName + " where _id = " + id, null);
        cursor.moveToNext();

        int a = cursor.getInt(6);
        int b = cursor.getInt(5);

        db.close();

        if(a + b == 2)
            return 3;
        else if(a == 1 && b == 0)
            return 1;
        else if(a == 0 && b == 1)
            return 2;

        return 0;
    }
}
