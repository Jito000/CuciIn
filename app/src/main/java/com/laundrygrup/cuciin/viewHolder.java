package com.laundrygrup.cuciin;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class viewHolder extends RecyclerView.ViewHolder {

    private TextView namaToko;
    private TextView biaya;
    private CardView cardView;
    public viewHolder(View itemView){
        super(itemView);
        cardView=itemView.findViewById(R.id.cv_beranda);
        namaToko =itemView.findViewById(R.id.tv_namaToko_cardView);
        biaya=itemView.findViewById(R.id.tv_biaya_cardView);
    }
    @SuppressLint("SetTextI18n")
    public void tampilData(dataToko data){
        namaToko.setText(data.getNamaToko());
        biaya.setText("Rp "+data.getBiaya()+",- /kg");

    }
}



