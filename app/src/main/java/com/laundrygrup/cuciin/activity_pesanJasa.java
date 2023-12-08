package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_pesanJasa extends AppCompatActivity {

    EditText inputNama;
    EditText inputnoHp;
    EditText inputAlamat;
    EditText inputCatatan;

    Button kePembayaran;
    ImageView kembali;
    TextView jumlahBayar;

    String kodeToko;

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    dataUser data = new dataUser();
    dataToko.biaya tampil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_jasa);

        init();
        setData();
        cekData();

    }
    private void init(){
        inputNama=findViewById(R.id.et_namaPemesan_buatPesanan);
        inputnoHp=findViewById(R.id.et_noHp_buatPesanan);
        inputAlamat=findViewById(R.id.et_alamat_buatPesanan);
        inputCatatan=findViewById(R.id.et_catatan_buatPesanan);

        kePembayaran=findViewById(R.id.btn_pembayaran_buatPesanan);
        kembali=findViewById(R.id.iv_back_buatPesanan);
        jumlahBayar=findViewById(R.id.tv_totalBayar_buatPesanan);

        kodeToko=getIntent().getStringExtra("kodeToko");
        tampil=new dataToko.biaya();
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setData(){
        mAuth.getCurrentUser().getUid();

        reference.child("User").child(mAuth.getUid()).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data =dataSnapshot.getValue(dataUser.class);
                assert data != null;
                inputNama.setText(data.getNamaDepan()+" "+data.getNamaBelakang());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.child("Toko").child(kodeToko)
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tampil =dataSnapshot.getValue(dataToko.biaya.class);
                        assert tampil != null;
                        jumlahBayar.setText("Rp "+tampil.getBiaya()+",-");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
    private void cekData(){
        kePembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputNama.getText().toString().equals("")||
                        inputnoHp.getText().toString().equals("")||
                        inputAlamat.getText().toString().equals("")||
                        inputCatatan.getText().toString().equals("")){
                    Toast.makeText(activity_pesanJasa.this,"Isi semua data",Toast.LENGTH_LONG).show();
                }
                else{
                    pilihPembayaran();
                }
            }
        });
    }
    private void pilihPembayaran(){
        Intent intent=new Intent(activity_pesanJasa.this, activity_pembayaran.class);
        intent.putExtra("nama",inputNama.getText().toString());
        intent.putExtra("noHp",inputnoHp.getText().toString());
        intent.putExtra("alamat",inputAlamat.getText().toString());
        intent.putExtra("catatan",inputCatatan.getText().toString());
        intent.putExtra("kodeToko",kodeToko);
        startActivity(intent);
        finish();
    }

}
