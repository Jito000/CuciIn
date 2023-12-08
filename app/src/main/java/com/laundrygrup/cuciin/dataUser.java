package com.laundrygrup.cuciin;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class dataUser {
    private String namaDepan;
    private String namaBelakang;
    private String ttl;
    private String kodeToko;

    public dataUser(){}


    public dataUser(String namaDepan, String namaBelakang, String ttl) {
        this.namaBelakang = namaBelakang;
        this.namaDepan = namaDepan;
        this.ttl = ttl;
        this.kodeToko="";
    }
    //getter
    public String getNamaDepan(){
        return namaDepan;
    }
    public String getNamaBelakang(){
        return namaBelakang;
    }
    public String getTtl(){
        return ttl;
    }
    public String getKodeToko(){
        return kodeToko;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap <String, Object> hasil=new HashMap<>();
        hasil.put("namaDepan",namaDepan);
        hasil.put("namaBelakang",namaBelakang);
        hasil.put("ttl",ttl);
        hasil.put("kodeToko",kodeToko);
        return hasil;
    }
    public static class uid {
        private String ID;

        public uid(String id){
            ID=id;
        }
        public void setId(String id){
            this.ID=id;
        }
        public String getId(){
            return ID;
        }
    }

    public static class setKodetoko {

        private String kodeToko;

        public setKodetoko(){}
        public setKodetoko(String kodeToko){
            this.kodeToko=kodeToko;
        }
        public String getKodeToko(){
            return kodeToko;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("kodeToko",kodeToko);
            return hasil;
        }


    }
    public static class setIdPesanan {

        private String kodePesanan;

        public setIdPesanan(){}
        public setIdPesanan(String kodePesanan){
            this.kodePesanan = kodePesanan;
        }
        public String getIdPesanan(){
            return kodePesanan;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("kodePesanan", kodePesanan);
            return hasil;
        }


    }
}
