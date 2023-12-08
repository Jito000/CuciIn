package com.laundrygrup.cuciin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class pagerAdapter_updatePesanan extends FragmentPagerAdapter {
    int nomortab=0;
    public pagerAdapter_updatePesanan(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        fragment_toko_daftarPesanan daftarPesanan= new fragment_toko_daftarPesanan();
        Bundle data=new Bundle();
        data.putInt("nomortab",nomortab);
        daftarPesanan.setArguments(data);
        return daftarPesanan;
    }

    @Override
    public int getCount() {
        return nomortab;
    }
}
