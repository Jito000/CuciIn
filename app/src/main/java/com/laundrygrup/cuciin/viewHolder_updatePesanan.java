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

public class viewHolder_updatePesanan extends RecyclerView.ViewHolder {
    private TextView namaPemesan;
    private TextView kodePesanan;
    private TextView status;
    private CardView cardView;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    public viewHolder_updatePesanan(View itemView){
        super(itemView);
        cardView=itemView.findViewById(R.id.cv_update_pesanan);
        namaPemesan =itemView.findViewById(R.id.tv_namaToko_cardView_update);
        kodePesanan=itemView.findViewById(R.id.tv_biaya_cardView_update);
        status=itemView.findViewById(R.id.tv_status_cardView_update);
    }
    @SuppressLint("SetTextI18n")
    public void tampilData(dataPesanan data){
        namaPemesan.setText(data.getNamaPemesan());
        kodePesanan.setText(data.getKodePesanan());
        tampilStatus(data.getKodePesanan());

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
