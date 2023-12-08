package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_deskripsiPesanan extends AppCompatActivity {

    String kodePesanan;
    TextView namaPemesan;
    TextView noHp;
    TextView berat;
    TextView alamatPemesan;
    TextView catatan;
    TextView namaToko;
    TextView alamatToko;
    TextView totalCucian;
    TextView kodeCucian;
    Button pesananSelesai;
    EditText refresh;
    ImageView kembali;

    DatabaseReference reference;
    private FirebaseRecyclerAdapter <dataPesanan.updateStatus,viewholder_updateStatus> adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_pesanan);

        init();
        tampilPemesan();
        inputUlasan();
        tampilStatus();
    }
    @Override
    public void onStart(){
        super.onStart();
        tampilList();
        if(adapter!=null){
            adapter.startListening();
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        if(adapter!=null){
            adapter.stopListening();
        }
    }

    private void init(){
        kodePesanan=getIntent().getStringExtra("kodePesanan");

        namaPemesan=findViewById(R.id.tv_namaPemesan_cekStatus);
        noHp=findViewById(R.id.tv_noHp_cekStatus);
        berat=findViewById(R.id.tv_beratFix_cekStatus);
        alamatPemesan=findViewById(R.id.tv_alamat_cekStatus);
        catatan=findViewById(R.id.tv_catataan_cekStatus);
        namaToko=findViewById(R.id.tv_namaToko_cekStatus);
        alamatToko=findViewById(R.id.tv_alamatToko_cekStatus);
        totalCucian=findViewById(R.id.tv_totalBiaya_cekStatus);
        pesananSelesai=findViewById(R.id.btn_cucianSelesai_cekStatus);
        kodeCucian=findViewById(R.id.tv_kodePesanan_cekStatus);
        refresh=findViewById(R.id.tv_refresh_cekStatus);
        reference= FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recycle_view_CekStatus);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activity_deskripsiPesanan.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        kembali=findViewById(R.id.iv_back_cekStatus);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void tampilPemesan(){
        reference.child("Pesanan").child(kodePesanan).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataPesanan data=dataSnapshot.getValue(dataPesanan.class);
                if(data!=null){
                    namaPemesan.setText(data.getNamaPemesan());
                    noHp.setText(data.getNoHp());
                    if(!data.getBerat().equals(" ")){
                        berat.setText(data.getBerat()+" kg");
                    }
                    alamatPemesan.setText(data.getAlamat());
                    catatan.setText(data.getCatatan());
                    if(!data.getBiaya().equals(" ")){
                        totalCucian.setText("Rp "+data.getBiaya()+",-");
                    }
                    kodeCucian.setText(data.getKodePesanan());
                    tampilToko(data.getKodeToko());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void tampilToko(String kodeToko){
        reference.child("Toko").child(kodeToko).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko data=dataSnapshot.getValue(dataToko.class);
                if(data!=null){
                    namaToko.setText(data.getNamaToko());
                    alamatToko.setText(data.getAlamat());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void tampilList(){
        Query query=reference.child("Pesanan").child(kodePesanan).child("Status").orderByChild("tanggal");

        FirebaseRecyclerOptions<dataPesanan.updateStatus> options=new FirebaseRecyclerOptions.Builder<dataPesanan.updateStatus>()
                .setQuery(query,dataPesanan.updateStatus.class).build();

        adapter=new FirebaseRecyclerAdapter<dataPesanan.updateStatus, viewholder_updateStatus>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholder_updateStatus viewHolder, int i, @NonNull final dataPesanan.updateStatus data) {
                viewHolder.tampilData(data);

            }

            @NonNull
            @Override
            public viewholder_updateStatus onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_update_status,parent,false);
                return new viewholder_updateStatus(view);
            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private void inputUlasan(){
        pesananSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_deskripsiPesanan.this,activity_ulasan.class);
                intent.putExtra("kodePesanan",kodePesanan);
                startActivity(intent);
                finish();
            }
        });
    }
    private void tampilStatus(){
        refresh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                refresh.setVisibility(View.GONE);

            }
        });
    }

}