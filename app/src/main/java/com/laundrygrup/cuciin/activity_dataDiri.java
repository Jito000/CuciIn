package com.laundrygrup.cuciin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class activity_dataDiri extends AppCompatActivity {

    EditText inputNamaDepan;
    EditText inputNamaBelakang;
    ProgressBar pb_dataDiri;
    Spinner spinner_tahun;
    Spinner spinner_hari;
    Spinner spinner_bulan;

    Button btnSimpan;

    String uid = getIntent().getStringExtra("uid");
    String namaDepan = getIntent().getStringExtra("namaDepan");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_diri);


        inputDataDiri();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftar();
            }
        });


    }
    private void inputDataDiri(){
        inputNamaDepan = (EditText) findViewById(R.id.et_namaDepan);
        inputNamaBelakang = (EditText) findViewById(R.id.et_namaBelakang);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        spinner_hari = (Spinner) findViewById(R.id.spinner_hari);
        spinner_bulan = (Spinner) findViewById(R.id.spinner_bulan);
        spinner_tahun = (Spinner) findViewById(R.id.spinner_tahun);
        pb_dataDiri= (ProgressBar) findViewById(R.id.pb_dataDiri);
        pb_dataDiri.setVisibility(View.GONE);
        inputNamaDepan.setText(namaDepan);
    }
    private void daftar(){
        if (inputNamaDepan.getText().toString().equals("") || inputNamaBelakang.getText().toString().equals("") ) {
            Toast.makeText(activity_dataDiri.this, "Isi semua data", Toast.LENGTH_SHORT).show();
        } else {
            pb_dataDiri.setVisibility(View.VISIBLE);
            simpanData();
        }
    }
    private void simpanData(){
        DatabaseReference simpanData = FirebaseDatabase.getInstance().getReference();
        simpanData.child("User").child(uid).setValue(new dataUser(inputNamaDepan.getText().toString(), inputNamaBelakang.getText().toString(),spinner_hari.getSelectedItem().toString()+" "+spinner_bulan.getSelectedItem().toString()+" "+spinner_tahun.getSelectedItem().toString()))
                .addOnSuccessListener(activity_dataDiri.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pb_dataDiri.setVisibility(View.GONE);
                        tampilMenu();
                    }
                });
    }
    private void tampilMenu(){
        Intent intent = new Intent(activity_dataDiri.this, activity_menu.class);
        startActivity(intent);
    }
}

