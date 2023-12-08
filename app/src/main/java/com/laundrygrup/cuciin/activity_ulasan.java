package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_ulasan extends AppCompatActivity {

    String kodePesanan;
    String kodeTokoKeUlasan;
    ImageView kembali;
    TextView namaToko;
    EditText inputUlasan;
    Button simpanUlasan;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulasan);
        init();
        ambilNamaToko();
        cekData();
    }
    private void init(){
        kodePesanan=getIntent().getStringExtra("kodePesanan");
        kembali=findViewById(R.id.iv_kembali_ulasan);
        namaToko=findViewById(R.id.tv_namaToko_ulasan);
        inputUlasan=findViewById(R.id.et_inputUlasan_ulasan);
        simpanUlasan=findViewById(R.id.btn_inputUlasan_update);

        reference= FirebaseDatabase.getInstance().getReference();
    }
    private void ambilNamaToko(){
        reference.child("Pesanan").child(kodePesanan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataPesanan data= dataSnapshot.getValue(dataPesanan.class);
                if(data!=null){
                    tampilNamaToko(data.getKodeToko());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void tampilNamaToko(String kodeToko){
        reference.child("Toko").child(kodeToko).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko data=dataSnapshot.getValue(dataToko.class);
                if(data!=null){
                    namaToko.setText(data.getNamaToko());
                    kodeTokoKeUlasan=data.getKodeToko();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void cekData(){
        simpanUlasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputUlasan.getText().toString().equals("")){
                    Toast.makeText(activity_ulasan.this,"Field Kosong",Toast.LENGTH_LONG).show();
                }
                else{
                    simpanData();
                }

            }
        });
    }
    private void simpanData(){
        reference.child("Ulasan").child(kodeTokoKeUlasan).child(kodePesanan).setValue(new dataUlasan(kodePesanan,inputUlasan.getText().toString()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_ulasan.this,"Sukses",Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1000);

                    }
                });
    }
}