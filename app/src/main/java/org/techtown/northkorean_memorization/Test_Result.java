package org.techtown.northkorean_memorization;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class Test_Result extends AppCompatActivity {
    private int score;
    private int correctNum;
    private int problemsNum;

    Fragment test_scoreFragment;
    Fragment test_reviewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_viewpager2);

        // Test_Test에서 부터 온 intent
        Intent intent = getIntent();
        score = intent.getIntExtra("score", -1);
        correctNum = intent.getIntExtra("score", -1);
        problemsNum = intent.getIntExtra("score", -1);

        ViewPager2 pager = findViewById(R.id.test_pager);
        Test_StateAdapter adapter = new Test_StateAdapter(this);
        pager.setAdapter(adapter);
    }

    private class Test_StateAdapter extends FragmentStateAdapter {
        private ArrayList<Fragment> fragments;

        Test_StateAdapter(FragmentActivity fa) {
            super(fa);
            Bundle bundleScore = new Bundle();
            bundleScore.putInt("score", score);
            bundleScore.putInt("correctNum", correctNum);
            bundleScore.putInt("problemNum", problemsNum);

            test_scoreFragment = new Test_ScoreFragment();
            test_reviewFragment = new Test_ReviewFragment();

            test_scoreFragment.setArguments(bundleScore);

            fragments = new ArrayList<>();
            fragments.add(test_scoreFragment);
            fragments.add(test_reviewFragment);
        }

        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
