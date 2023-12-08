package com.laundrygrup.cuciin;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int nomorTab;

    public PagerAdapter(FragmentManager fm,int nomorTab){
        super(fm,nomorTab);
        this.nomorTab=nomorTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new fragment_menu_daftarPesanan();
            case 1:
                return new fragment_menu_jasaCucian();
            case 2:
                return new fragment_chat();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nomorTab;
    }
}
