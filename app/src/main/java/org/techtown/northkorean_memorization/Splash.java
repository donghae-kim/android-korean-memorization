package org.techtown.northkorean_memorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    Animation anim;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 화면 가로 고정
        setContentView(R.layout.activity_splash);
        linearLayout=(LinearLayout)findViewById(R.id.activity_splash); // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        linearLayout.startAnimation(anim);
    }
}
