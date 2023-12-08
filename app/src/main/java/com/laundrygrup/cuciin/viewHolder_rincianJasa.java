package com.laundrygrup.cuciin;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class viewHolder_rincianJasa extends RecyclerView.ViewHolder {
    private TextView idPesanan;
    private TextView ulasan;
    public viewHolder_rincianJasa(View itemView){
        super(itemView);
        idPesanan =itemView.findViewById(R.id.tv_cardView_idPesanan_rincianJasa);
        ulasan=itemView.findViewById(R.id.tv_cardView_ulasan_rincianJasa);
    }
    @SuppressLint("SetTextI18n")
    public void tampilData(dataUlasan data){
        idPesanan.setText(data.getIdPesanan());
        ulasan.setText(data.getUlasan());

    }

}
