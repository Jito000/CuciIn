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

public class activity_daftar extends AppCompatActivity {
    EditText inputEmail;
    EditText inputNama;
    EditText inputPassword;

    Button btndaftar;
    Button btnLogin;
    ProgressBar pb_daftar;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        daftar();

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanAkun();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_daftar.this,activity_login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void daftar(){
        inputNama = (EditText) findViewById(R.id.et_nama);
        inputEmail = (EditText) findViewById(R.id.et_email);
        inputPassword = (EditText) findViewById(R.id.et_password);
        btndaftar = (Button) findViewById(R.id.btn_daftar);
        btnLogin = (Button) findViewById(R.id.btn_login);
        pb_daftar = (ProgressBar) findViewById(R.id.pb_daftar);
        pb_daftar.setVisibility(View.GONE);
    }
    private void simpanAkun(){
        if (inputEmail.getText().toString().equals("") ||inputPassword.getText().toString().equals("")){
            Toast.makeText(activity_daftar.this,"Email atau password kosong",Toast.LENGTH_SHORT).show();
        }else if (inputPassword.getText().length()<6) {
            Toast.makeText(activity_daftar.this, "Password minimal 6", Toast.LENGTH_SHORT).show();
        }else {
            pb_daftar.setVisibility(View.VISIBLE);
            simpanData();
        }
    }
    private void inputDataDiri(){
        Intent intent;
        intent = new Intent(activity_daftar.this,activity_dataDiri.class);
        intent.putExtra("uid",mAuth.getUid());
        intent.putExtra("namaDepan",inputNama.getText().toString());
        startActivity(intent);
    }
    private void simpanData(){
        mAuth.createUserWithEmailAndPassword(inputEmail.getText().toString(),inputPassword.getText().toString())
                .addOnCompleteListener(activity_daftar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(activity_daftar.this,"Akun telah terdaftar",Toast.LENGTH_SHORT).show();
                            pb_daftar.setVisibility(View.GONE);
                        }
                        else{
                            pb_daftar.setVisibility(View.GONE);
                            inputDataDiri();
                        }
                    }
                });
    }
}
