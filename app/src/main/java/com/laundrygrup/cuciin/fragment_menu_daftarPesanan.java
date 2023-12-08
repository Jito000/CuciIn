package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class fragment_menu_daftarPesanan extends Fragment{

    private FirebaseUser mAuth;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter <dataPesanan,viewHolder_riwayat> adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_jasa_cucian, container, false);

        init(view);
        tampilPesanan();

        return view;
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
    private void init(View view){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }
    private void tampilPesanan() {
        Query query =databaseReference.child("Pesanan").orderByChild("idPemesan").equalTo(mAuth.getUid());

        FirebaseRecyclerOptions<dataPesanan> options = new FirebaseRecyclerOptions.Builder<dataPesanan>()
                .setQuery(query, dataPesanan.class).build();

        adapter = new FirebaseRecyclerAdapter<dataPesanan, viewHolder_riwayat>(options) {

            @Override
            protected void onBindViewHolder(@NonNull viewHolder_riwayat viewHolder_riwayat1, int i, @NonNull final dataPesanan dataPesanan1) {
                viewHolder_riwayat1.tampilData(dataPesanan1);
                viewHolder_riwayat1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihPesanan(dataPesanan1);
                    }
                });
            }

            @NonNull
            @Override
            public viewHolder_riwayat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_riwayat, parent, false);
                return new viewHolder_riwayat(view);
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private void pilihPesanan(dataPesanan data){
        Intent intent=new Intent(getContext(), activity_deskripsiPesanan.class);
        intent.putExtra("kodePesanan",data.getKodePesanan());
        startActivity(intent);
    }
}
