package org.techtown.northkorean_memorization;

import android.os.Bundle;
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

    TextView problems;
    Button ans1, ans2, ans3, ans4;
    Button bookMark;
    Button passBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_test);

        // View 객체 획득
        test_getView();

        test_buildProblem(20, 1);

        // Button 이벤트 등록
        test_setClick();
    }

    private int test_buildProblem(int problemSize, int section) {
        //Test_DatabaseAdapter.DatabaseHelper
        return 1;
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
        Toast testToast = Toast.makeText(this.getApplicationContext(),view + "asd", Toast.LENGTH_SHORT);
        testToast.show();
    }
}

class Problem {
    int id;
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