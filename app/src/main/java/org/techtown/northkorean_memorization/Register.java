package org.techtown.northkorean_memorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("계정 만들기");
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘


        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.check).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.check:
                    signUp();
                    break;
            }
        }
    };


    private void signUp() {
        String name = ((EditText) findViewById(R.id.user_name)).getText().toString();
        String id = ((EditText) findViewById(R.id.user_id)).getText().toString();
        String password = ((EditText) findViewById(R.id.user_password)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.user_password_check)).getText().toString();

        if (id.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
            if (password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(id, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //파이어베이스에 저장시켜주는 코드
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String id = user.getEmail();
                                    String uid = user.getUid();

                                    //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                    HashMap<Object,String> hashMap = new HashMap<>();
                                    hashMap.put("uid",uid);
                                    hashMap.put("email", id);
                                    hashMap.put("name", name);
                                    database = FirebaseDatabase.getInstance();
                                    reference = database.getReference();
                                    reference.child("User").push().setValue(hashMap);

                                    startActivity(new Intent(Register.this, Login.class));
                                    Toast.makeText(Register.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (task.getException().toString() != null) {
                                        Toast.makeText(Register.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show(); // 이미 존재하는 아이디
                                    }
                                }
                            }
                        });
            } else {
                Toast.makeText(Register.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Register.this, "아아디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}
