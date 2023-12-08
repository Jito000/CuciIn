package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_rincianPesanan extends AppCompatActivity {

    TextView namaLaundry;
    TextView alamat;
    TextView kodepesanan;
    Button selesai;

    String kodePesan;
    String kodeToko;

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rician_pesanan);

        init();
        tampilData();
    }
    private void init(){
        namaLaundry=findViewById(R.id.tv_namaLaundry_tampilrincianPesanan);
        alamat=findViewById(R.id.tv_alamat_tampilrincianPesanan);
        kodepesanan=findViewById(R.id.tv_kodeBayar_tampilrincianPesanan);
        selesai=findViewById(R.id.btn_selesai_tampilrincianPesanan);

        kodePesan=getIntent().getStringExtra("kodePesan");
        kodeToko=getIntent().getStringExtra("kodeToko");

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void tampilData(){
        reference.child("Toko").child(kodeToko).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko data=dataSnapshot.getValue(dataToko.class);
                if(data!=null){
                    namaLaundry.setText(data.getNamaToko());
                    alamat.setText(data.getAlamat());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.child("Pesanan").child(kodePesan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataPesanan data=dataSnapshot.getValue(dataPesanan.class);
                if(data!=null){
                    kodepesanan.setText(data.getKodePesanan());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}