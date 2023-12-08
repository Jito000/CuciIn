package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_toko extends AppCompatActivity{

    ImageButton kembali;
    Button buatToko;
    RelativeLayout gambar;

    DatabaseReference reference;
    FirebaseUser getUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);

        init(new fragment_toko_daftarPesanan());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inten=new Intent(activity_toko.this, activity_loading.class);
                startActivity(inten);
            }
        },5);

        cekToko();

    }
    private void init(Fragment fragment){

        reference=FirebaseDatabase.getInstance().getReference();
        getUser = FirebaseAuth.getInstance().getCurrentUser();

        kembali=findViewById(R.id.ib_back_toko);
        buatToko=findViewById(R.id.btn_buat_toko);
        gambar=findViewById(R.id.rl_uploadGambar);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buatToko();
        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_toko.this,"Coming soon...",Toast.LENGTH_LONG).show();
            }
        });

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_Toko,fragment);
        fragmentTransaction.commit();
    }
    private void cekToko(){
        reference.child("User").child(getUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataUser dataDiri=dataSnapshot.getValue(dataUser.class);
                assert dataDiri != null;
                if(dataDiri.getKodeToko()!=null){
                    tampilStatusToko(dataDiri.getKodeToko());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void tampilStatusToko(String kodeToko){
        reference.child("Toko").child(kodeToko).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko tampilNama=dataSnapshot.getValue(dataToko.class);
                assert tampilNama != null;
                if(tampilNama.getKodeToko()!=null){
                    buatToko.setText("Edit Toko");

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void buatToko(){
        buatToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_toko.this,activity_bukaToko.class);
                startActivity(intent);
            }
        });
    }
}

