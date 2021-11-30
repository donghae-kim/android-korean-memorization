package org.techtown.northkorean_memorization;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Test_Test extends AppCompatActivity implements View.OnClickListener {
    private boolean chance;
    private int problemsNum;
    private int solvedNum;
    private int collectNum;

    private final String tableName = "Words";
    private final String datebaseName = "Words.db";

    TextView problems;
    Button ans1, ans2, ans3, ans4;
    Button bookMark;
    Button passBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_test);

        //PreCreateDB.copyDB(this);

        // View 객체 획득
        test_getView();

        test_buildProblem(20, 3, false);

        // Button 이벤트 등록
        test_setClick();
    }

    private int test_buildProblem(int problemSize, int section, boolean memExclude) {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String conditionWhere = " where Filed = " + section;
        if (memExclude)
            conditionWhere += "Memorized = 0";

        //Cursor cursor = db.rawQuery("select * from " + tableName + conditionWhere, null);

        Cursor cursor = db.rawQuery("select * from " + tableName, null);
        int loadedSize = cursor.getCount();

        db.execSQL("UPDATE " + tableName + " SET BookMark = " + 0);
        db.execSQL("UPDATE " + tableName + " SET Memorized = " + 0);


        Log.d("Test_Test", "Loaded " + loadedSize + " rows");


        if (loadedSize < problemSize)
            problemSize = loadedSize;

        int mid = loadedSize / 2;

        int[] arr = new int[problemSize];
        rand_noDuple(arr, problemSize, loadedSize);

        Problem[] Problems = new Problem[problemSize];

        int[] memo = { 1, 3, 5, 7, 9};
        int[] book = { 2 ,4, 6, 8};

        updateDB(book, memo);

        cursor = db.rawQuery("select * from " + tableName + " where BookMark = 1", null);
        loadedSize = cursor.getCount();
        Log.d("Test_Test", "Loaded " + loadedSize + " rows");


        cursor = db.rawQuery("select * from " + tableName + " where Memorized = 1", null);
        loadedSize = cursor.getCount();
        Log.d("Test_Test", "Loaded " + loadedSize + " rows");

        /*
        for (int i = 0; i < problemSize; ++i) {
            cursor.moveToPosition(arr[i]);

            int Problem_Filed = cursor.getInt(4);
            if(Problem_Filed == 3 || Math.random() > 0.5) {
                int id = cursor.getInt(0);
                String problem = cursor.getString(2);
                String correct = cursor.getString(1);
                if (arr[i] <= mid)
                    cursor.moveToNext();
                else
                    cursor.moveToPrevious();
                String unCorrect1 = cursor.getString(1);

                cursor.moveToPosition((int)(Math.random() * loadedSize));
                String unCorrect2 = cursor.getString(1);

                cursor.moveToPosition((int)(Math.random() * loadedSize));
                String unCorrect3 = cursor.getString(1);

                Problems[i] = new Problem(id, problem, correct, unCorrect1, unCorrect2, unCorrect3);
            }
            else {
                int id = cursor.getInt(0);
                String problem = cursor.getString(1);
                String correct = cursor.getString(2);
                if (arr[i] <= mid)
                    cursor.moveToNext();
                else
                    cursor.moveToPrevious();
                String unCorrect1 = cursor.getString(2);

                cursor.moveToPosition((int)(Math.random() * loadedSize));
                String unCorrect2 = cursor.getString(2);

                cursor.moveToPosition((int)(Math.random() * loadedSize));
                String unCorrect3 = cursor.getString(2);

                Problems[i] = new Problem(id, problem, correct, unCorrect1, unCorrect2, unCorrect3);
            }

        }


        */
        db.close();
        return 1;
    }

    private void rand_noDuple(int[] arr, int arrSize, int range) {
        int size = 0;
        int temp = 0;
        while (size < arrSize) {
            temp = (int) (Math.random() * range);
            for (int i = 0; i < size; ++i) {
                if (arr[i] == temp)
                    continue;
            }

            arr[size++] = temp;
        }

        Log.d("Test_Test", arrSize + " Problems are successfully selected  : ");
    }

    /**
     * XML내부 View 객체 참조 획득 in Test_Test <p>
     * problems, ans1 ~ ans4, bookMark, passBtn </p>
     */
    private void test_getView() {
        problems = (TextView) findViewById(R.id.test_problems);

        ans1 = (Button) findViewById(R.id.test_ans1);
        ans2 = (Button) findViewById(R.id.test_ans2);
        ans3 = (Button) findViewById(R.id.test_ans3);
        ans4 = (Button) findViewById(R.id.test_ans4);

        bookMark = (Button) findViewById(R.id.test_bookMark);
        passBtn = (Button) findViewById(R.id.test_passBtn);
    }

    /**
     * View 객체에 온클릭 리스너 in Test_Test <p>
     * problems, ans1 ~ ans4, bookMark, passBtn </p>
     */
    private void test_setClick() {
        problems.setOnClickListener(this);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);

        bookMark.setOnClickListener(this);
        passBtn.setOnClickListener(this);
    }

    private Boolean updateDB(int[] bookMark, int[] memorized) {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db != null) {
            for (int i = 0; i < bookMark.length; ++i) {
                updateSQL(db, tableName, "BookMark", bookMark[i], 1);
                Log.d("Test_Test_updateDB", bookMark[i] + "th BookMark change into 1");
            }
            for (int i = 0; i < memorized.length; ++i) {
                db.execSQL("UPDATE " + tableName + " SET Memorized = 1 where _id = " + memorized[i]);
                Log.d("Test_Test_updateDB", memorized[i] + "th Memorized change into 1");
            }
            db.close();
        }
        else
            return false;

        return true;
    }

    private void updateSQL(SQLiteDatabase db, String inputTableName, String what, int id, int value) {
        db.execSQL("UPDATE " + tableName + " SET " + what + " = 1 where _id = " + id);
    }

    @Override
    public void onClick(View view) {
        Toast testToast = Toast.makeText(this.getApplicationContext(),view + "asd", Toast.LENGTH_SHORT);
        testToast.show();
    }
}

class Problem {
    int id;
    int filed;
    String problem;
    String correct;
    String unCorrect1;
    String unCorrect2;
    String unCorrect3;

    public Problem(int id, String problem, String correct, String unCorrect1, String unCorrect2, String unCorrect3) {
        this.id = id;
        this.problem = problem;
        this.correct = correct;
        this.unCorrect1 = unCorrect1;
        this.unCorrect2 = unCorrect2;
        this.unCorrect3 = unCorrect3;
    }
}