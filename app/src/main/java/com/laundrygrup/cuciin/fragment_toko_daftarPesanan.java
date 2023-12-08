package com.laundrygrup.cuciin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class fragment_toko_daftarPesanan extends Fragment {
    private FirebaseUser mAuth;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter <dataPesanan,viewHolder_updatePesanan> adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_jasa_cucian, container, false);

        init(view);
        getKodeToko();

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
        mAuth= FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }
    private void tampilList(String kodeToko) {
        Query query =databaseReference.child("Pesanan").orderByChild("kodeToko").equalTo(kodeToko);
        FirebaseRecyclerOptions<dataPesanan> options = new FirebaseRecyclerOptions.Builder<dataPesanan>()
                .setQuery(query, dataPesanan.class).build();

        adapter = new FirebaseRecyclerAdapter<dataPesanan, viewHolder_updatePesanan>(options) {

            @Override
            protected void onBindViewHolder(@NonNull viewHolder_updatePesanan viewHolder_updatePesanan1, int i, @NonNull final dataPesanan dataPesanan1) {
                viewHolder_updatePesanan1.tampilData(dataPesanan1);
                viewHolder_updatePesanan1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputPilihan(dataPesanan1);
                    }
                });
            }

            @NonNull
            @Override
            public viewHolder_updatePesanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_update_pesanan, parent, false);
                return new viewHolder_updatePesanan(view);
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private void getKodeToko(){
        databaseReference.child("User").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataUser kodeToko=dataSnapshot.getValue(dataUser.class);
                if(kodeToko!=null){
                    tampilList(kodeToko.getKodeToko());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void inputPilihan(dataPesanan data){
        Intent intent=new Intent(getContext(),activity_updatePesanan.class);
        intent.putExtra("kodePesanan",data.getKodePesanan());
        startActivity(intent);
    }
}
