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
    private Problem[] problemSet;

    private final String tableName = "Words";
    private final String databaseName = "Words.db";

    TextView problems;
    Button ans1, ans2, ans3, ans4;
    Button bookMark;
    Button passBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_test);
        test_initValue();

        PreCreateDB.copyDB(this);

        // View 객체 획득
        test_getView();

        problemSet = test_buildProblem(problemsNum, 1, false);

        // Button 이벤트 등록
        test_setClick();
        test_setActivity(0);
    }

    private void test_initValue() {
        chance = true;
        problemsNum = 20;
        solvedNum = 0;
        collectNum = 0;
        problemSet = null;
    }

    private Problem[] test_buildProblem(int problemSize, int section, boolean memExclude) {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String conditionWhere = " where Field = " + section;
        if (memExclude)
            conditionWhere += "Memorized = 0";

        //Cursor cursor = db.rawQuery("select * from " + tableName + conditionWhere, null);
        Cursor cursor = db.rawQuery("select * from Words", null);
        int loadedSize = cursor.getCount();

        Log.d("Test_Test", "Loaded " + loadedSize + " rows");

        if (loadedSize < problemSize)
            problemSize = loadedSize;

        int mid = loadedSize / 2;

        int[] indexSet = new int[problemSize];
        test_rand_noDuple(indexSet, problemSize, loadedSize);

        Problem[] problemset = new Problem[problemSize];

        final int NORTH = 1;
        final int SOUTH = 2;

        for (int i = 0; i < problemSize; ++i) {
            cursor.moveToPosition(indexSet[i]);

            int Problem_Filed = cursor.getInt(4);

            if (Problem_Filed == 3 || Math.random() > 0.5)
                problemset[i] = test_makeProblem(cursor, indexSet[i], loadedSize, SOUTH, NORTH);
            else
                problemset[i] = test_makeProblem(cursor, indexSet[i], loadedSize, NORTH, SOUTH);
        }


        for (int i = 0; i < problemSize; ++i)
            problemset[i].printAll();

        db.close();
        return problemset;
    }

    private Problem test_makeProblem(Cursor cursor, int index, int loadedSize, int from, int to) {
        int id = cursor.getInt(0);
        String problem = cursor.getString(from);
        String correct = cursor.getString(to);

        if (index <= loadedSize / 2)
            cursor.moveToNext();
        else
            cursor.moveToPrevious();
        String unCorrect1 = cursor.getString(to);

        cursor.moveToPosition((int) (Math.random() * loadedSize));
        String unCorrect2 = cursor.getString(to);

        cursor.moveToPosition((int) (Math.random() * loadedSize));
        String unCorrect3 = cursor.getString(to);

        return (new Problem(id, problem, correct, unCorrect1, unCorrect2, unCorrect3));
    }

    private void test_rand_noDuple(int[] arr, int arrSize, int range) {
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

    void test_setActivity(int problemNumber) {
        Log.d("Test_Test", "ProblemSet " + problemNumber + " loading : ");
        problems.setText(problemSet[problemNumber].problem);
        ans1.setText(problemSet[problemNumber].correct);
        ans2.setText(problemSet[problemNumber].unCorrect1);
        ans3.setText(problemSet[problemNumber].unCorrect2);
        ans4.setText(problemSet[problemNumber].unCorrect3);
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

    @Override
    public void onClick(View view) {
        Toast testToast = Toast.makeText(this.getApplicationContext(), "Touch!", Toast.LENGTH_SHORT);
        testToast.show();

        if (chance == true) {
            chance = false;
        } else if (++solvedNum < problemsNum) {
            chance = true;
            Log.d("Test_Test", "Next ProblemSet load : ");
            test_setActivity(solvedNum);
        } else {
            Log.d("Test_Test", "all ProblemSet is done");
        }
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

    public void printAll() {
        Log.d("Test_Test", id + " " + problem + " " + correct + " " + unCorrect1 + " " + unCorrect2 + " " + unCorrect3);
    }
}