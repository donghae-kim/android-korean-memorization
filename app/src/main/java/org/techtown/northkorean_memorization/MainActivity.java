package org.techtown.northkorean_memorization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button study;
    private Button test;
    private Button myWord;
    private Button setting;
    private Button registerID;
    private TextView userName;

    private DatabaseReference mDatabase;

    AlertDialog testDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //각 액티비티에 다크모드 구현 위해 존재해야하는 if문 문장
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }

        setContentView(R.layout.activity_main);

        study = findViewById(R.id.study);
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Study.class);
                startActivity(intent);
            }
        });

        test = findViewById(R.id.test);

        myWord = findViewById(R.id.myWord);
        myWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyWord.class);
                startActivity(intent);
            }
        });

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
            }
        });

        registerID = findViewById(R.id.registerID); //임시 버튼임 ㅇ
        registerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        userName = (TextView)findViewById(R.id.name);
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("EMAIL");
        userName.setText(userEmail);
    }

    public void show_default_dialog(View view) {
        Log.d("main_dialog", "btn pressed");

        String path = "/data/data/"+ getPackageName() + "/databases/Test_Saved.db";
        File dbFile = new File(path);

        Test_Dialog.getInstance(this).showDefaultDialog();

        //if(dbFile != null)
        //    Test_Dialog_Saved.getInstance(this).showDefaultDialog();
        //else
        //    Test_Dialog.getInstance(this).showDefaultDialog();
    }
}
