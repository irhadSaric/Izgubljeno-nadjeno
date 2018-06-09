package com.example.user.izgubljenonadjeno;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class StvarPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Stvar> mStvari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stvar_pager);

        mViewPager = (ViewPager) findViewById(R.id.stvar_view_pager);
        mStvari = StvariLab.get(this).getStvari();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Stvar stvar = mStvari.get(position);
                return StvarFragment.newInstance(stvar.getID());
            }
            @Override
            public int getCount() {
                return mStvari.size();
            }
        });
    }
}
