package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.Random;

public class activity_pembayaran extends AppCompatActivity {

    String nama;
    String noHp;
    String alamat;
    String catatan;
    String kodeToko;

    TextView namaPemesan;
    TextView noHpPemesan;
    TextView alamatPemesan;
    TextView catatanPemesan;

    Button buatPesanan;
    ImageView kembali;
    Spinner metodeBayar;


    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser mAuth=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        init();
        cekPembayaran();
    }
    private void init(){
        nama=getIntent().getStringExtra("nama");
        noHp=getIntent().getStringExtra("noHp");
        alamat=getIntent().getStringExtra("alamat");
        catatan=getIntent().getStringExtra("catatan");
        kodeToko=getIntent().getStringExtra("kodeToko");

        namaPemesan=findViewById(R.id.tv_namaPemesan_konfirmasi);
        namaPemesan.setText(nama);
        noHpPemesan=findViewById(R.id.tv_noHp_konfirmasi);
        noHpPemesan.setText(noHp);
        alamatPemesan=findViewById(R.id.tv_alamat_konfirmasi);
        alamatPemesan.setText(alamat);
        catatanPemesan=findViewById(R.id.tv_catatan_konfirmasi);
        catatanPemesan.setText(catatan);

        buatPesanan=findViewById(R.id.btn_pembayaran_konfirmasi);
        kembali=findViewById(R.id.iv_kembali_konfirmasi);
        metodeBayar=findViewById(R.id.spinner_pembayaran);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void cekPembayaran(){
        buatPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isKodePesananAda();
            }
        });
    }
    private String setKodePesanan(){
        Random random=new Random();
        int val=random.nextInt();
        String hex;
        hex=Integer.toHexString(val);
        return hex;

    }
    private void isKodePesananAda(){
        final String kodePesan;
        kodePesan=setKodePesanan().toUpperCase();
        reference.child("Pesanan").child(kodePesan).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataPesanan kodePesanan = dataSnapshot.getValue(dataPesanan.class);
                        if (kodePesanan != null) {
                            if(kodePesanan.getKodePesanan()==kodePesan){
                                isKodePesananAda();
                                Toast.makeText(activity_pembayaran.this,kodePesanan.getKodePesanan(),Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            simpanData(kodePesan);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void simpanData(final String kodePesan){
        reference.child("Pesanan").child(kodePesan)
                .setValue(new dataPesanan(mAuth.getUid(),kodeToko,kodePesan,nama, noHp, alamat, catatan," "," "))
                .addOnSuccessListener(activity_pembayaran.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pesananKeUser(kodePesan);
                        pesananKeToko(kodePesan);
                        tampilRincian(kodePesan);
                    }
                });
    }
    private void pesananKeUser(String kodePesan){
        reference.child("User").child(mAuth.getUid()).child("Pesanan").child(kodePesan)
                .setValue(new dataPesanan.kodePesanan(kodePesan)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }
    private void pesananKeToko(String kodePesan){
        reference.child("Toko").child(kodeToko).child("Pesanan").child(kodePesan)
                .setValue(new dataPesanan.kodePesanan(kodePesan)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }
    private void tampilRincian(String kodePesan){
        Intent intent= new Intent(activity_pembayaran.this, activity_rincianPesanan.class);
        intent.putExtra("kodePesan",kodePesan);
        intent.putExtra("kodeToko",kodeToko);
        startActivity(intent);
    }

}
