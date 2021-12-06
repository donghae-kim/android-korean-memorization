package org.techtown.northkorean_memorization;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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
    //Button ans1, ans2, ans3, ans4;
    Button ans[] = new Button[4];
    Button bookMark;
    Button passBtn;
    ImageView correctSymbol;
    ImageView unCorrectSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_test);
        test_initValue();

        PreCreateDB.copyDB(this);

        // View 객체 획득
        int[] a = {1, 2, 3, 4, 5};
        updateDBdd(a, a);

        test_getView();
        int temp = getMemorizedCount();
        Log.d("Test_Test", temp + "Loaded ");

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
        int field = cursor.getInt(4);
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

        int correctPos = (int)(Math.random() * 4);
        return (new Problem(id, field, problem, correct, unCorrect1, unCorrect2, unCorrect3, correctPos));
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

        ans[problemSet[problemNumber].correctPos].setText(problemSet[problemNumber].correct);
        int unCorIndex = 0;
        for (int i = 0; i < 4; ++i) {
            if(i != problemSet[problemNumber].correctPos)
                ans[i].setText(problemSet[problemNumber].unCorrect[unCorIndex++]);
        }
    }

    /**
     * XML내부 View 객체 참조 획득 in Test_Test <p>
     * problems, ans1 ~ ans4, bookMark, passBtn </p>
     */
    private void test_getView() {
        problems = (TextView) findViewById(R.id.test_problems);

        ans[0] = (Button) findViewById(R.id.test_ans1);
        ans[1] = (Button) findViewById(R.id.test_ans2);
        ans[2] = (Button) findViewById(R.id.test_ans3);
        ans[3] = (Button) findViewById(R.id.test_ans4);

        bookMark = (Button) findViewById(R.id.test_bookMark);
        passBtn = (Button) findViewById(R.id.test_passBtn);

        correctSymbol = (ImageView) findViewById(R.id.test_correct_symbol);
        unCorrectSymbol = (ImageView) findViewById(R.id.test_uncorrect_symbol);
    }

    /**
     * View 객체에 온클릭 리스너 in Test_Test <p>
     * problems, ans1 ~ ans4, bookMark, passBtn </p>
     */
    private void test_setClick() {
        problems.setOnClickListener(this);

        ans[0].setOnClickListener(this);
        ans[1].setOnClickListener(this);
        ans[2].setOnClickListener(this);
        ans[3].setOnClickListener(this);

        bookMark.setOnClickListener(this);
        passBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast testToast = Toast.makeText(this.getApplicationContext(), "Touch!", Toast.LENGTH_SHORT);
        testToast.show();

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
        if (chance == true) {
            chance = false;
            if (view == ans[problemSet[solvedNum].correctPos]) {
                Log.d("Test_Test", "Select Right Ans!");
                correctSymbol.setVisibility(View.VISIBLE);
                correctSymbol.startAnimation(animation);
                collectNum++;
            }
            else {
                Log.d("Test_Test", "Select X Ans!");
                unCorrectSymbol.setVisibility(View.VISIBLE);
                unCorrectSymbol.startAnimation(animation);
            }

            ++solvedNum;
        } else if (++solvedNum < problemsNum) {
            chance = true;
            correctSymbol.setVisibility(View.INVISIBLE);
            unCorrectSymbol.setVisibility(View.INVISIBLE);
            Log.d("Test_Test", "Next ProblemSet load : ");
            test_setActivity(solvedNum);
        } else {
            Log.d("Test_Test", "all ProblemSet is done");
        }
    }


    private int getMemorizedCount() {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from Words where Memorized=1", null);
        int loadedSize = cursor.getCount();

        db.close();

        return loadedSize;
    }

    public Boolean updateDBdd(int[] bookMark, int[] memorized) {

        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db != null) {

            for (int i = 0; i < bookMark.length; ++i) {
                updateSQLee(db, tableName, "BookMark", bookMark[i], 1);
                Log.d("Test_Test_updateDB", bookMark[i] + "th BookMark change into 1");
            }
            for (int i = 0; i < memorized.length; ++i) {
                updateSQLee(db, tableName, "Memorized", memorized[i], 1);
                Log.d("Test_Test_updateDB", memorized[i] + "th Memorized change into 1");
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
    private void updateSQLee(SQLiteDatabase db, String inputTableName, String what, int id, int value) {
        db.execSQL("UPDATE " + tableName + " SET " + what + " = 1 where _id = " + id);
    }
}

class Problem {
    int id;
    int field;
    String problem;
    String correct;
    String[] unCorrect = new String[3];
    int correctPos;

    public Problem(int id, int field, String problem, String correct, String unCorrect0, String unCorrect1, String unCorrect2, int correctPos) {
        this.id = id;
        this.field = field;
        this.problem = problem;
        this.correct = correct;
        this.unCorrect[0] = unCorrect0;
        this.unCorrect[1] = unCorrect1;
        this.unCorrect[2] = unCorrect2;
        this.correctPos = correctPos;
    }

    public void printAll() {
        Log.d("Test_Test", id + " " + field + " "+ problem + " " + correct + " " + unCorrect[0] + " " + unCorrect[1] + " " + unCorrect[2] + " " + correctPos);
    }
}