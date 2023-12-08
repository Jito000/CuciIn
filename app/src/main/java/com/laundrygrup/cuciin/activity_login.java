package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_login extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    Button btndaftar;
    Button btnLogin;
    Button btnLupaPassword;
    ProgressBar pb_daftar;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        cekData();
        daftar();

        lupaPassword();
    }
    private void init(){
        inputEmail=(EditText) findViewById(R.id.et_email);
        inputPassword=(EditText) findViewById(R.id.et_password);
        btndaftar=(Button) findViewById(R.id.btn_daftar);
        btnLogin=(Button) findViewById(R.id.btn_login);
        btnLupaPassword=(Button) findViewById(R.id.btn_lupaPassword);
        pb_daftar=(ProgressBar) findViewById(R.id.pb_daftar);
        pb_daftar.setVisibility(View.GONE);
    }
    private void cekData(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputEmail.getText().toString().equals("") ||inputPassword.getText().toString().equals("")){
                    Toast.makeText(activity_login.this,"Email atau password kosong",Toast.LENGTH_SHORT).show();
                }else if (inputPassword.getText().length()<6) {
                    Toast.makeText(activity_login.this, "Password minimal 6", Toast.LENGTH_SHORT).show();
                }else {
                    pb_daftar.setVisibility(View.VISIBLE);
                    ambilData();
                }

            }
        });
    }
    private void ambilData(){
        mAuth.signInWithEmailAndPassword(inputEmail.getText().toString(),inputPassword.getText().toString())
                .addOnCompleteListener(activity_login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            pb_daftar.setVisibility(View.GONE);
                            tampilMenu();
                        }
                        else {
                                    Toast.makeText(activity_login.this, "Err: Email atau password salah", Toast.LENGTH_LONG).show();
                                    pb_daftar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    private void tampilMenu(){
        Intent intent;
        intent = new Intent(activity_login.this, activity_menu.class);
        startActivity(intent);
        finish();
    }
    private void daftar(){
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_login.this,activity_daftar.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void lupaPassword(){
        btnLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_login.this,activity_lupaPassword.class);
                startActivity(intent);
            }
        });
    }
}
