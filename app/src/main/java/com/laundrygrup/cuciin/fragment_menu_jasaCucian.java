package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class fragment_menu_jasaCucian extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter <dataToko,viewHolder> adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_jasa_cucian, container, false);

        init(view);
        tampilList();

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

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }
    private void tampilList(){
        Query query=databaseReference.child("Toko").orderByChild("konfirmasi").equalTo("Terkonfirmasi");

        FirebaseRecyclerOptions<dataToko> options=new FirebaseRecyclerOptions.Builder<dataToko>()
                .setQuery(query,dataToko.class).build();

        adapter=new FirebaseRecyclerAdapter<dataToko, viewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull final dataToko data) {
                viewHolder.tampilData(data);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihJasa(data);
                    }
                });
            }

            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view,parent,false);
                return new viewHolder(view);
            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private void pilihJasa(dataToko data){
        Intent intent=new Intent(getContext(), activity_rincianJasa.class);
        intent.putExtra("kodeToko",data.getKodeToko());
        startActivity(intent);
    }
}
