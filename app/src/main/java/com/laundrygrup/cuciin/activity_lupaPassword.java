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
import com.google.firebase.auth.FirebaseAuth;

public class activity_lupaPassword extends AppCompatActivity {

    EditText inputEmail;
    Button btnLoginEmail;
    Button btnCariEmail;
    ProgressBar progressBar;
    FirebaseAuth resetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        init();
        cariEmail();
        login();
    }
    private void init()
    {
        inputEmail=(EditText) findViewById(R.id.et_email);
        btnLoginEmail=(Button) findViewById(R.id.btn_loginEmail);
        btnCariEmail=(Button) findViewById(R.id.btn_cari);
        progressBar=(ProgressBar) findViewById(R.id.pb_reset);
        progressBar.setVisibility(View.GONE);
        resetPassword=FirebaseAuth.getInstance();
    }
    private void login()
    {
        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(activity_lupaPassword.this,activity_login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void cariEmail()
    {
        btnCariEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEmail.getText().toString().equals("")){
                    Toast.makeText(activity_lupaPassword.this,"Email kosong!",Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword.sendPasswordResetEmail(inputEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(activity_lupaPassword.this,"Email terkirim!",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(activity_lupaPassword.this,"Email salah!",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });
    }
}
