package com.laundrygrup.cuciin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_menu extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tampilNama;
    ImageView toko;
    ImageView ava;

    FirebaseAuth mAuth;
    FirebaseUser getUser;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
        tampilNama();
        tampilMenu();
    }
    private void init(){
        tabLayout=(TabLayout) findViewById(R.id.tab_layout);
        viewPager= (ViewPager) findViewById(R.id.vpager);

        tampilNama=(TextView) findViewById(R.id.tv_namaDepan);
        toko=(ImageView)findViewById(R.id.iv_shop);
        ava=(ImageView) findViewById(R.id.iv_photo);

        mAuth=FirebaseAuth.getInstance();
        getUser=mAuth.getCurrentUser();
        ref=FirebaseDatabase.getInstance().getReference();
        toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_menu.this,activity_toko.class);
                startActivity(intent);
            }
        });
    }
    private void tampilNama(){
        ref.child("User").child(getUser.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataUser dataDiri=dataSnapshot.getValue(dataUser.class);
                tampilNama.setText(dataDiri.getNamaDepan()+" "+dataDiri.getNamaBelakang());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void tampilMenu(){
        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
