package com.laundrygrup.cuciin;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class viewholder_updateStatus extends RecyclerView.ViewHolder {
    private TextView tanggal;
    private TextView status;
    public viewholder_updateStatus(View itemView){
        super(itemView);
        tanggal =itemView.findViewById(R.id.tv_tanggal_cardView_updateStatus);
        status=itemView.findViewById(R.id.tv_status_cardView_updateStatus);
    }
    @SuppressLint("SetTextI18n")
    public void tampilData(dataPesanan.updateStatus data){
        tanggal.setText(data.getTanggal());
        status.setText(data.getKeterangan());

    }

}
