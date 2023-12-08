package com.laundrygrup.cuciin;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewHolder_riwayat extends RecyclerView.ViewHolder {
    private TextView namaToko;
    private TextView biaya;
    private TextView status;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    public viewHolder_riwayat(View itemView){
        super(itemView);
        namaToko =itemView.findViewById(R.id.tv_namaToko_cardView_riwayat);
        biaya=itemView.findViewById(R.id.tv_biaya_cardView_riwayat);
        status=itemView.findViewById(R.id.tv_status_cardView_riwayat);
    }
    @SuppressLint("SetTextI18n")
    public void tampilData(dataPesanan data){
        tampilNamaToko(data.getKodeToko());
        tampilStatus(data.getKodePesanan());
        if(!data.getBiaya().equals(" ")){
            biaya.setText("Rp "+data.getBiaya()+",-");
        }

    }
    private void tampilNamaToko(String kodeToko){
        reference.child("Toko").child(kodeToko).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataToko data=dataSnapshot.getValue(dataToko.class);
                if(data!=null){
                    namaToko.setText(data.getNamaToko());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void tampilStatus(String kodePesanan){
        reference.child("Pesanan").child(kodePesanan).child("Status").orderByKey().limitToLast(1)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        dataPesanan.updateStatus data=dataSnapshot.getValue(dataPesanan.updateStatus.class);
                        if(data!=null){
                            status.setText(data.getKeterangan());
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        dataPesanan.updateStatus data=dataSnapshot.getValue(dataPesanan.updateStatus.class);
                        if(data!=null){
                            status.setText(data.getKeterangan());
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
