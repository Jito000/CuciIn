package com.laundrygrup.cuciin;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class dataToko {
    private String namaToko;
    private String alamat;
    private String biaya;
    private String noHp;
    private String deskripsi;
    private String konfirmasi;
    private String kodeToko;

    public dataToko(){}
    public dataToko(String kodeToko,String namaToko, String biaya, String alamat, String noHp, String deskripsi,String konfirmasi){
        this.kodeToko=kodeToko;
        this.namaToko=namaToko;
        this.biaya=biaya;
        this.alamat=alamat;
        this.noHp=noHp;
        this.deskripsi=deskripsi;
        this.konfirmasi=konfirmasi;
    }
    //getter
    public String getKodeToko(){return kodeToko;}
    public String getNamaToko(){
        return namaToko;
    }
    public String getAlamat(){
        return alamat;
    }
    public String getBiaya(){
        return biaya;
    }
    public String getNoHp(){
        return noHp;
    }
    public String getDeskripsi(){
        return deskripsi;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap <String, Object> hasil=new HashMap<>();
        hasil.put("kodeToko",kodeToko);
        hasil.put("namaToko",namaToko);
        hasil.put("alamat",alamat);
        hasil.put("biaya",biaya);
        hasil.put("noHp",noHp);
        hasil.put("deskripsi",deskripsi);
        hasil.put("konfirmasi",konfirmasi);
        return hasil;
    }

    public static class idToko {
        private String lastId;

        public idToko(){}
        public idToko(String lastId) {
            this.lastId = lastId;
        }

        public String getLastId() {
            return lastId;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("lastId", lastId);
            return hasil;
        }
    }

    public static class nama {

        private String namaToko;
        public nama(String namaToko){
            this.namaToko=namaToko;
        }
        public String getNamaToko(){
            return namaToko;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("namaToko",namaToko);
            return hasil;
        }
    }
    public static class biaya {

        private String biaya;
        public biaya(){}
        public biaya(String biaya){
            this.biaya =biaya;
        }
        public String getBiaya(){
            return biaya;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("biaya", biaya);
            return hasil;
        }
    }
    public static class alamat {

        private String alamat;
        public alamat(String alamat){
            this.alamat =alamat;
        }
        public String getAlamat(){
            return alamat;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("alamat", alamat);
            return hasil;
        }
    }
    public static class noTelp {

        private String noHp;
        public noTelp(String noHp){
            this.noHp =noHp;
        }
        public String getNoHp(){
            return noHp;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("noHp", noHp);
            return hasil;
        }
    }
    public static class deskripsi {

        private String deskripsi;
        public deskripsi(String deskripsi){
            this.deskripsi =deskripsi;
        }
        public String getDeskripsi(){
            return deskripsi;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("deskripsi", deskripsi);
            return hasil;
        }
    }
    public static class konfirmasi {

        private String konfirmasi;
        public konfirmasi(String konfirmasi){
            this.konfirmasi =konfirmasi;
        }
        public String getKonfirmasi(){
            return konfirmasi;
        }

        @Exclude
        public Map<String, Object> toMap(){
            HashMap<String, Object> hasil=new HashMap<>();
            hasil.put("konfirmasi", konfirmasi);
            return hasil;
        }
    }
}
