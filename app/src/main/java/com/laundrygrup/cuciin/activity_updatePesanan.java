package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class activity_updatePesanan extends AppCompatActivity {

    String kodePesanan;
    String kodeToko;

    ImageView kembali;

    TextView namaPemesan;
    TextView noHp;
    TextView alamat;
    TextView catatan;
    TextView autofill1;
    TextView autofill2;
    TextView autofill3;
    TextView autofill4;
    TextView autofill5;
    TextView kg;
    TextView berat;

    Button btninputBerat;
    Button gantiStatusDanUpdate;
    Button update;


    EditText inputBerat;
    EditText inputUpdateStatus;

    CardView tampilHalamanStatus;
    CardView updateHalamanstatus;

    DatabaseReference reference;
    private FirebaseRecyclerAdapter <dataPesanan.updateStatus,viewholder_updateStatus> adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pesanan);
        init();
        tampilList();
        tampilPesanan();
        inputberatCucian();
        gantistatusdanupdate();
        setAutoFill();
        inputData();
    }

    @Override
    public void onStart(){
        super.onStart();
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

        namaPemesan=findViewById(R.id.tv_namaPemesan_updatePesanan);
        noHp=findViewById(R.id.tv_noHp_updatePesanan);
        alamat=findViewById(R.id.tv_alamat_updatePesanan);
        catatan=findViewById(R.id.tv_catataan_updatePesanan);
        btninputBerat=findViewById(R.id.btn_inputBerat);
        kg=findViewById(R.id.tv_berat_updatePesanan);
        inputBerat=findViewById(R.id.et_beratCucian_updatePesanan);
        berat=findViewById(R.id.tv_beratFix_updatePesanan);
        kembali=findViewById(R.id.iv_back_updatePesanan);

        tampilHalamanStatus=findViewById(R.id.cv_tampilStatus_updatePesanan);
        updateHalamanstatus=findViewById(R.id.cv_updateStatus_updatePesanan);
        gantiStatusDanUpdate=findViewById(R.id.btn_status_dan_update);
        update=findViewById(R.id.btn_simpanUpdate_updatePesanan);

        autofill1=findViewById(R.id.af_cucian_siap_diambil);
        autofill2=findViewById(R.id.af_dalam_antrian);
        autofill3=findViewById(R.id.af_sedang_dicuci);
        autofill4=findViewById(R.id.af_sudah_dijemur);
        autofill5=findViewById(R.id.af_sudah_disetrika);
        inputUpdateStatus=findViewById(R.id.et_updateStatus_updatePesanan);

        reference= FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recycle_view_UpdateStatus);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(activity_updatePesanan.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void tampilPesanan(){
        reference.child("Pesanan").child(kodePesanan).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataPesanan data=dataSnapshot.getValue(dataPesanan.class);
                if(data!=null){
                    namaPemesan.setText(data.getNamaPemesan());
                    noHp.setText(data.getNoHp());
                    alamat.setText(data.getAlamat());
                    catatan.setText(data.getCatatan());
                    kodeToko=data.getKodeToko();
                    if(!data.getBerat().equals(" ")){
                        berat.setText(data.getBerat()+ " kg");
                        berat.setVisibility(View.VISIBLE);

                        btninputBerat.setVisibility(View.GONE);
                        inputBerat.setVisibility(View.GONE);
                        kg.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void inputberatCucian(){
        btninputBerat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputBerat.getText().toString().equals("")){
                    Toast.makeText(activity_updatePesanan.this,"Isi berat cucian",Toast.LENGTH_LONG).show();
                }
                else{
                   ambilBiaya();
                }
            }
        });
    }
    private void ambilBiaya(){
        reference.child("Toko").child(kodeToko).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko data=dataSnapshot.getValue(dataToko.class);
                if(data!=null){
                    simpanUpdatepesanan(data.getBiaya());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void gantistatusdanupdate(){
        final boolean[] isShowStatus = {true};
        gantiStatusDanUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(isShowStatus[0]){
                    tampilHalamanStatus.setVisibility(View.GONE);
                    updateHalamanstatus.setVisibility(View.VISIBLE);
                    gantiStatusDanUpdate.setText("Tampil Status");
                    isShowStatus[0] =false;
                }
                else {
                    tampilHalamanStatus.setVisibility(View.VISIBLE);
                    updateHalamanstatus.setVisibility(View.GONE);
                    gantiStatusDanUpdate.setText("Update Status");
                    isShowStatus[0] =true;
                }
            }
        });
    }
    private void simpanUpdatepesanan(String biaya){
        int newBiaya=Integer.parseInt(biaya)*Integer.parseInt(inputBerat.getText().toString());
        dataPesanan.biayaDanBerat data= new dataPesanan.biayaDanBerat(Integer.toString(newBiaya),inputBerat.getText().toString());

        reference.child("Pesanan").child(kodePesanan).updateChildren(data.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
    private void setAutoFill(){
        autofill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputUpdateStatus.setText(autofill1.getText());
            }
        });
        autofill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputUpdateStatus.setText(autofill2.getText());
            }
        });
        autofill3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputUpdateStatus.setText(autofill3.getText());
            }
        });
        autofill4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputUpdateStatus.setText(autofill4.getText());
            }
        });
        autofill5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputUpdateStatus.setText(autofill5.getText());
            }
        });
    }
    private void inputData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputUpdateStatus.getText().toString().equals("")){
                    Toast.makeText(activity_updatePesanan.this,"Isi semua data",Toast.LENGTH_LONG).show();
                }
                else{
                    simpanUpdate();
                }
            }
        });
    }
    private void simpanUpdate(){
        @SuppressLint
                ("SimpleDateFormat") final DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date=new Date();
        String tanggal=dateFormat.format(date);
        reference.child("Pesanan").child(kodePesanan).child("Status").child(tanggal)
                .setValue(new dataPesanan.updateStatus(tanggal,inputUpdateStatus.getText().toString()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_updatePesanan.this,"Sukses",Toast.LENGTH_LONG).show();
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

}