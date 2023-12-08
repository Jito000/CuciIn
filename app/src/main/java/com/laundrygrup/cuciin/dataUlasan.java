package com.laundrygrup.cuciin;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class dataUlasan {
    String idPesanan;
    String ulasan;
    public dataUlasan(){

    }
    public dataUlasan(String idPesanan,String ulasan){
        this.idPesanan=idPesanan;
        this.ulasan=ulasan;
    }
    public String getIdPesanan(){
        return idPesanan;
    }
    public String getUlasan(){
        return ulasan;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hasil=new HashMap<>();
        hasil.put("idPesanan", idPesanan);
        hasil.put("ulasan",ulasan);
        return hasil;
    }

}
