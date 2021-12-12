package org.techtown.northkorean_memorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Test_ScoreFragment extends Fragment {
    private TextView score;
    private TextView Text_string;

    private int resultScore;
    private int correctNum;
    private int problemNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_score, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        resultScore = bundle.getInt("score");
        correctNum = bundle.getInt("correctNum");
        problemNum = bundle.getInt("problemNum");

        score = view.findViewById(R.id.score_score);
        Text_string = view.findViewById(R.id.score_string);

        score.setText("" + resultScore);
        Text_string.setText(problemNum + "문제 중에 " + correctNum + "문제를 맞추셨습니다!");
    }
}
