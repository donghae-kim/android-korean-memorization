package org.techtown.northkorean_memorization;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Test_Test extends AppCompatActivity implements View.OnClickListener {
    private boolean chance;
    private int isBookMark;
    private int problemsNum;
    private int solvedNum;
    private int correctNum;
    private int score;
    private Problem[] problemSet;
    private int[] indexSet;
    private int[] isCorrect;

    private final String tableName = "Words";
    private final String databaseName = "Words.db";

    private int field;
    private boolean range;

    TextView problems;
    TextView allCounter;
    TextView corCounter;

    Button ans[] = new Button[4];
    Button bookMark;
    Button passBtn;
    ImageButton nextBtn;
    ImageView correctSymbol;
    ImageView unCorrectSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_test);

        test_initValue();
        PreCreateDB.copyDB(this);

        // View 객체 획득
        test_getView();
        test_getIntent();

        problemSet = test_buildProblem(problemsNum, field, range);
        test_setCounter();

        // Button 이벤트 등록
        test_setClick();
        test_setActivity(0);
    }

    private void test_getIntent() {
        Intent intent = getIntent();

        problemsNum = intent.getIntExtra("num", 20);
        field = intent.getIntExtra("field", 0);
        range = intent.getBooleanExtra("range", false);

        Log.d("Test_Test", problemsNum + " " + field + " " + range);
    }

    private void test_initValue() {
        chance = true;
        problemsNum = 20;
        solvedNum = 0;
        correctNum = 0;
        problemSet = null;
    }

    private Problem[] test_buildProblem(int problemSize, int section, boolean memExclude) {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String conditionWhere = test_buildString(section, memExclude);
        Log.d("Test_Test", conditionWhere);

        Cursor cursor = db.rawQuery("select * from " + tableName + conditionWhere, null);
        //Cursor cursor = db.rawQuery("select * from Words", null);
        int loadedSize = cursor.getCount();

        Log.d("Test_Test", "Loaded " + loadedSize + " rows");

        if (loadedSize < problemSize)
            problemSize = loadedSize;

        indexSet = new int[problemSize];
        isCorrect = new int[problemSize];

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

    private String test_buildString(int section, boolean memExclude) {
        String conditionWhere = "";

        if (section != 0 || memExclude) {
            conditionWhere = " where";
            if (section != 0)
                conditionWhere += " Field = " + section;
            if (section != 0 && memExclude)
                conditionWhere += " AND";
            if (memExclude)
                conditionWhere += " Memorized = 0";
        }
        return conditionWhere;
    }

    private Problem test_makeProblem(Cursor cursor, int index, int loadedSize, int from, int to) {
        int id = cursor.getInt(0);
        int field = cursor.getInt(4);
        int bookMark = cursor.getInt(5);
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
        return (new Problem(id, field, bookMark, problem, correct, unCorrect1, unCorrect2, unCorrect3, correctPos));
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

        isBookMark = problemSet[problemNumber].bookMark;
        test_setBookMark(isBookMark);
    }

    /**
     * XML내부 View 객체 참조 획득 in Test_Test <p>
     * problems, ans1 ~ ans4, bookMark, passBtn </p>
     */
    private void test_getView() {
        problems = (TextView) findViewById(R.id.test_problems);

        allCounter = (TextView) findViewById(R.id.test_all_counter);
        corCounter = (TextView) findViewById(R.id.test_correct_counter);

        ans[0] = (Button) findViewById(R.id.test_ans1);
        ans[1] = (Button) findViewById(R.id.test_ans2);
        ans[2] = (Button) findViewById(R.id.test_ans3);
        ans[3] = (Button) findViewById(R.id.test_ans4);

        bookMark = (Button) findViewById(R.id.test_bookMark);
        passBtn = (Button) findViewById(R.id.test_passBtn);
        nextBtn = (ImageButton) findViewById(R.id.test_nextBtn);

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
        nextBtn.setOnClickListener(this);
    }

    private int test_getScore() {
        return (int)(((float)correctNum / solvedNum) * 100);
    }

    private void test_setCounter() {
        allCounter.setText("( "+ solvedNum +" / " + problemsNum + " )");
        corCounter.setText("" + correctNum);
    }

    private void test_toggleBookMark() {
        Log.d("Test_Test", "bookMark is toggled");

        if(isBookMark != 0)
            test_setBookMark(0);
        else
            test_setBookMark(1);
    }

    private void test_setBookMark(int value) {
        if(value != 0) {
            bookMark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.test_resize_star_full, 0, 0, 0);
            isBookMark = 1;
        }
        else {
            bookMark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.test_resize_star_blank, 0, 0, 0);
            isBookMark = 0;
        }
    }

    private void test_saveBookMark(int id, int value) {
        Test_DatabaseAdapter.DatabaseHelper helper = new Test_DatabaseAdapter.DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("UPDATE " + tableName + " SET BookMark = " + value + " where _id = " + id);
        db.close();
        Log.d("Test_Test", id+ "'s bookMark is saved : value = " + value);
    }

    @Override
    public void onClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
        if (view == bookMark)
            test_toggleBookMark();

        if (chance == true) {
            if (view == ans[0] || view == ans[1] || view == ans[2] || view == ans[3]) {
                chance = false;
                if (view == ans[problemSet[solvedNum].correctPos]) {
                    Log.d("Test_Test", "Select Right Ans!");
                    isCorrect[solvedNum] = 1;
                    correctSymbol.setVisibility(View.VISIBLE);
                    correctSymbol.startAnimation(animation);
                    correctNum++;
                } else {
                    Log.d("Test_Test", "Select X Ans!");
                    isCorrect[solvedNum] = 0;
                    unCorrectSymbol.setVisibility(View.VISIBLE);
                    unCorrectSymbol.startAnimation(animation);
                }
                nextBtn.setVisibility(View.VISIBLE);
            }
            if(view == passBtn) {
                chance = false;
                nextBtn.setVisibility(View.VISIBLE);
            }
        } else if (view == nextBtn) {
            test_saveBookMark(problemSet[solvedNum].id, isBookMark);

            ++solvedNum;
            if (solvedNum < problemsNum) {
                chance = true;
                correctSymbol.setVisibility(View.INVISIBLE);
                unCorrectSymbol.setVisibility(View.INVISIBLE);
                nextBtn.setVisibility(View.INVISIBLE);
                Log.d("Test_Test", "Next ProblemSet load : ");
                test_setActivity(solvedNum);
            } else {
                Log.d("Test_Test", "all ProblemSet is done");
                score = test_getScore();
                Intent intent = test_setIntent();
                startActivity(intent);
                finish();
            }
        }
        test_setCounter();
    }

    private Intent test_setIntent() {
        Intent intent = new Intent(Test_Test.this, Test_Result.class);

        intent.putExtra("score", score);
        intent.putExtra("problemsNum", problemsNum);
        intent.putExtra("correctNum", correctNum);

        intent.putExtra("indexSet", indexSet);
        intent.putExtra("isCorrect", isCorrect);

        return intent;
    }


}

class Problem {
    int id;
    int field;
    int bookMark;
    String problem;
    String correct;
    String[] unCorrect = new String[3];
    int correctPos;

    public Problem(int id, int field, int bookMark, String problem, String correct, String unCorrect0, String unCorrect1, String unCorrect2, int correctPos) {
        this.id = id;
        this.field = field;
        this.bookMark = bookMark;
        this.problem = problem;
        this.correct = correct;
        this.unCorrect[0] = unCorrect0;
        this.unCorrect[1] = unCorrect1;
        this.unCorrect[2] = unCorrect2;
        this.correctPos = correctPos;
    }

    public void printAll() {
        Log.d("Test_Test", id + " " + field + " " + bookMark + " " + problem +
                " " + correct + " " + unCorrect[0] + " " + unCorrect[1] + " " + unCorrect[2] + " " + correctPos);
    }
}