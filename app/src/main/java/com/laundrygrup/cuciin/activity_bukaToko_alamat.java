package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_bukaToko_alamat extends AppCompatActivity {

    private String lastId;
    int tambahId=0;
    final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser getUser=mAuth.getCurrentUser();
    dataUser.setKodetoko cekKodeToko;

    ImageView kembali;
    EditText inputAlamat;
    Button ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buka_toko_alamat);

        init();

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputJasa();
    }
    private void init(){
        kembali=findViewById(R.id.iv_back);
        inputAlamat =findViewById(R.id.et_alamat);
        ok=findViewById(R.id.btn_OK);
    }
    private void inputJasa(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputAlamat.getText().toString().equals("")){
                    Toast.makeText(activity_bukaToko_alamat.this,"Data kosong!",Toast.LENGTH_SHORT).show();
                }
                else {
                    cekPunyaToko();
                    finish();
                }
            }
        });
    }
    private void cekPunyaToko(){
        reference.child("User").child(getUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cekKodeToko =dataSnapshot.getValue(dataUser.setKodetoko.class);
                if(cekKodeToko.getKodeToko().equals("")){
                    getDataLastId();
                    buatToko();
                    updateLastId();
                }
                else {
                    lastId= cekKodeToko.getKodeToko();
                    editJasa();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateLastId(){
        reference.child("data").child("idToko").setValue(new dataToko.idToko(Integer.toString(tambahId)))
                .addOnSuccessListener(activity_bukaToko_alamat.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }
    private void tokoBaruKeUser(){
        dataUser.setKodetoko kodeTokoBaru;
        kodeTokoBaru=new dataUser.setKodetoko( "tk"+lastId);
        reference.child("User").child(getUser.getUid()).updateChildren(kodeTokoBaru.toMap())
                .addOnSuccessListener(activity_bukaToko_alamat.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }
    private void getDataLastId(){
        reference.child("data").child("idToko").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko.idToko id=dataSnapshot.getValue(dataToko.idToko.class);
                lastId=id.getLastId();
                tambahId =Integer.parseInt(lastId)+1;
                lastId=Integer.toString(tambahId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void buatToko(){
        String kodeToko="tk"+lastId;
        reference.child("Toko").child(kodeToko).setValue(new dataToko(kodeToko,"","",inputAlamat.getText().toString(),"","",""))
                .addOnSuccessListener(activity_bukaToko_alamat.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        tokoBaruKeUser();
                    }
                });
    }
    private void editJasa(){
        dataToko.alamat alamat=new dataToko.alamat(inputAlamat.getText().toString());
        reference.child("Toko").child(cekKodeToko.getKodeToko()).updateChildren(alamat.toMap())
                .addOnSuccessListener(activity_bukaToko_alamat.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }
}

