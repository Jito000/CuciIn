package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class activity_bukaToko extends AppCompatActivity {

    dataUser.setKodetoko cekKodeToko;
    dataToko dataToko_bukaToko;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser getUser=mAuth.getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference= database.getReference();


    TextView tampilToko;
    TextView tampilBiaya;
    TextView tampilTelp;
    TextView tampilAlamat;
    TextView tampilStateAlamat;
    TextView tampilStateDeskripsi;
    TextView tampilDeskripsi;

    TextView inputToko;
    TextView inputAlamat;
    TextView inputBiaya;
    TextView inputTelp;
    TextView inputDeskripsi;

    Button konfirmasi;
    ImageButton kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka_toko);

        init();
        inputData();
        cekDataMasuk();

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(){
        kembali=(ImageButton)findViewById(R.id.ib_back);

        konfirmasi=findViewById(R.id.btn_konfirmasi);

        inputToko=(TextView)findViewById(R.id.tv_namaToko);
        inputAlamat=(TextView)findViewById(R.id.tv_alamat);
        inputTelp=(TextView)findViewById(R.id.tv_noHp);
        inputBiaya=(TextView)findViewById(R.id.tv_biaya);
        inputDeskripsi=(TextView)findViewById(R.id.tv_deskripsi);

        tampilDeskripsi=(TextView)findViewById(R.id.tampilDeskripsi_bukaToko);
        tampilToko=(TextView)findViewById(R.id.tv_tampilNama_bukaToko);
        tampilAlamat=(TextView)findViewById(R.id.tampilAlamat_bukaToko);
        tampilStateAlamat=(TextView) findViewById(R.id.tv_tampilStateAlamat_bukaToko);
        tampilTelp=(TextView)findViewById(R.id.tv_tampilTelp_bukaToko);
        tampilBiaya=(TextView)findViewById(R.id.tv_tampilBiaya_bukaToko);
        tampilStateDeskripsi=(TextView)findViewById(R.id.tv_tampilDes_bukaToko);

    }
    private void cekDataMasuk(){
        reference.child("User").child(getUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cekKodeToko=dataSnapshot.getValue(dataUser.setKodetoko.class);
                setdata(cekKodeToko.getKodeToko());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setdata(final String kodeToko){
        reference.child("Toko").child(kodeToko).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko_bukaToko = dataSnapshot.getValue(dataToko.class);
                assert dataToko_bukaToko != null;
                tampilToko.setText(dataToko_bukaToko.getNamaToko());
                tampilBiaya.setText("Rp "+ dataToko_bukaToko.getBiaya()+",- per kg ");
                tampilStateAlamat.setText("Ubah");
                tampilAlamat.setText(dataToko_bukaToko.getAlamat());
                tampilTelp.setText(dataToko_bukaToko.getNoHp());
                tampilStateDeskripsi.setText("Ubah");
                tampilDeskripsi.setText(dataToko_bukaToko.getDeskripsi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void inputData(){
        inputToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_bukaToko.this,activity_bukaToko_namaToko.class);
                startActivity(intent);
            }
        });
        inputBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_bukaToko.this,activity_bukaToko_biayaJasa.class);
                startActivity(intent);
            }
        });
        inputAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_bukaToko.this,activity_bukaToko_alamat.class);
                startActivity(intent);
            }
        });
        inputTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_bukaToko.this,activity_bukaToko_telp.class);
                startActivity(intent);
            }
        });
        inputDeskripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_bukaToko.this,activity_bukaToko_deskripsi.class);
                startActivity(intent);
            }
        });
        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setKonfirmasi();
            }
        });
    }
    private void setKonfirmasi() {
        if (dataToko_bukaToko.getNamaToko().equals("") || dataToko_bukaToko.getBiaya().equals("") || dataToko_bukaToko.getNoHp().equals("") ||
                dataToko_bukaToko.getAlamat().equals("") || dataToko_bukaToko.getDeskripsi().equals("")) {
            Toast.makeText(activity_bukaToko.this, "Isi semua data", Toast.LENGTH_LONG).show();
        } else {
            dataToko.konfirmasi konfirmasi=new dataToko.konfirmasi("Terkonfirmasi");
            reference.child("Toko").child(cekKodeToko.getKodeToko()).updateChildren(konfirmasi.toMap())
                    .addOnSuccessListener(activity_bukaToko.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(activity_bukaToko.this,"Terkonfirmasi",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
