package org.techtown.northkorean_memorization;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        // View 객체 획득
        test_getView();

        // Button 이벤트 등록
        test_setClick();
    }

    public void onClick(View view) {
        if (chance) {
            if (view == ans1);
        }
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
}
