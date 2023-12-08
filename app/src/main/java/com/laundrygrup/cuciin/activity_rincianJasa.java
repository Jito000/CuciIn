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

public class activity_rincianJasa extends AppCompatActivity {


    String kodeToko;
    TextView namaToko;
    TextView alamat;
    TextView biaya;
    TextView deskripsi;
    ImageView kembali;
    Button cuciSekarang;
    EditText refresh;

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    private FirebaseRecyclerAdapter <dataUlasan,viewHolder_rincianJasa> adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    dataToko datatoko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_jasa);
        init();
        rincianJasa();
        unduhUlasan();
        tampilUlasan();
        pesanJasa();

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        unduhUlasan();
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
        kodeToko=getIntent().getStringExtra("kodeToko");
        namaToko=findViewById(R.id.tv_namaLaundry_klikToko);
        alamat=findViewById(R.id.tv_alamat_klikToko);
        biaya=findViewById(R.id.tv_biaya_klikToko);
        deskripsi=findViewById(R.id.tv_tampilDeskripsi_rincianJasa);
        cuciSekarang=findViewById(R.id.btn_cuciSekarang_klikToko);
        kembali=findViewById(R.id.iv_kembali_rincianJasa);
        refresh=findViewById(R.id.tv_refresh_rincianJasa);

        recyclerView = findViewById(R.id.recycle_view_rincianJasa);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activity_rincianJasa.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

    }
    private void rincianJasa(){
        reference.child("Toko").child(kodeToko)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        datatoko=dataSnapshot.getValue(dataToko.class);
                        namaToko.setText(datatoko.getNamaToko());
                        alamat.setText(datatoko.getAlamat());
                        biaya.setText("Rp "+datatoko.getBiaya()+",- per kg");
                        deskripsi.setText(datatoko.getDeskripsi());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    private void pesanJasa(){
        cuciSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_rincianJasa.this, activity_pesanJasa.class);
                intent.putExtra("kodeToko",kodeToko);
                startActivity(intent);
            }
        });
    }
    private void unduhUlasan(){
        Query query=reference.child("Ulasan").child(kodeToko).orderByChild("idPesanan");

        FirebaseRecyclerOptions<dataUlasan> options=new FirebaseRecyclerOptions.Builder<dataUlasan>()
                .setQuery(query,dataUlasan.class).build();

        adapter=new FirebaseRecyclerAdapter<dataUlasan, viewHolder_rincianJasa>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewHolder_rincianJasa viewHolder, int i, @NonNull final dataUlasan data) {
                viewHolder.tampilData(data);

            }

            @NonNull
            @Override
            public viewHolder_rincianJasa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_rincian_jasa,parent,false);
                return new viewHolder_rincianJasa(view);
            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private void tampilUlasan(){
        refresh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                refresh.setVisibility(View.GONE);

            }
        });
    }


}
