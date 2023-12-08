package com.laundrygrup.cuciin;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class dataPesanan {
    private String idPemesan;
    private String kodeToko;
    private String kodePesanan;
    private String namaPemesan;
    private String alamat;
    private String biaya;
    private String noHp;
    private String catatan;
    private String berat;

    public dataPesanan(){}
    public dataPesanan(String idPemesan,String kodeToko,String kodePesanan,String namaPemesan, String noHp, String alamat, String catatan,String biaya,String berat){
        this.idPemesan=idPemesan;
        this.kodeToko=kodeToko;
        this.kodePesanan=kodePesanan;
        this.namaPemesan =namaPemesan;
        this.alamat=alamat;
        this.noHp=noHp;
        this.catatan =catatan;
        this.biaya=biaya;
        this.berat=berat;
    }
    public String getIdPemesan(){
        return idPemesan;
    }
    public String getKodePesanan(){
        return kodePesanan;
    }
    public String getKodeToko(){
        return kodeToko;
    }
    public String getNamaPemesan(){
        return namaPemesan;
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
    public String getCatatan(){
        return catatan;
    }
    public String getBerat(){
        return berat;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hasil=new HashMap<>();
        hasil.put("idPemesan",idPemesan);
        hasil.put("kodePesanan",kodePesanan);
        hasil.put("namaPemesan", namaPemesan);
        hasil.put("noHp",noHp);
        hasil.put("alamat",alamat);
        hasil.put("catatan", catatan);
        hasil.put("biaya",biaya);
        hasil.put("berat",berat);
        return hasil;
    }
    public static class kodePesanan {
        private String kodePesanan;

        public kodePesanan(){
        }
        public kodePesanan(String kodePesanan) {
            this.kodePesanan = kodePesanan;
        }

        public void setKodePesanan(String kodePesanan){
            this.kodePesanan =kodePesanan;
        }
        public String getKodePesanan() {
            return kodePesanan;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("kodePesanan", kodePesanan);
            return hasil;
        }
    }
    public static class biayaDanBerat{
        private String biaya;
        private String berat;

        public biayaDanBerat(){}
        public biayaDanBerat(String biaya,String berat){
            this.biaya = biaya;
            this.berat = berat;
        }
        public String getBiaya () {
        return biaya;
    }
        public String getBerat () {
        return berat;
    }
        @Exclude
        public Map<String, Object> toMap () {
        HashMap<String, Object> hasil = new HashMap<>();
        hasil.put("biaya", biaya);
        hasil.put("berat", berat);
        return hasil;
        }
    }
    public static class updateStatus{
        private String tanggal;
        private String keterangan;

        public updateStatus(){}
        public updateStatus(String tanggal,String keterangan){
            this.tanggal = tanggal;
            this.keterangan = keterangan;
        }
        public String getTanggal () {
            return tanggal;
        }
        public String getKeterangan () {
            return keterangan;
        }
        @Exclude
        public Map<String, Object> toMap () {
            HashMap<String, Object> hasil = new HashMap<>();
            hasil.put("tanggal", tanggal);
            hasil.put("keterangan", keterangan);
            return hasil;
        }

    }
}
