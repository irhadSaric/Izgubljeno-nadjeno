package com.example.user.izgubljenonadjeno;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class StvarCitanjePagerActivity extends AppCompatActivity implements StvarFragment.Callbacks{
    private ViewPager mViewPager;
    private List<Stvar> mStvari;
    private static final String EXTRA_STVAR_ID ="com.example.user.izgubljenonadjeno.stvar_id";

    public static Intent newIntent(Context packageContext, UUID stvarId) {
        Intent intent = new Intent(packageContext, StvarCitanjePagerActivity.class);
        intent.putExtra(EXTRA_STVAR_ID, stvarId);
        return intent;
    }

    @Override
    public void onStvarUpdated(Stvar stvar) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stvar_pager);

        mViewPager = (ViewPager) findViewById(R.id.stvar_view_pager);
        mStvari = StvariLab.get(this).getStvari();
        UUID stvarId = (UUID) getIntent().getSerializableExtra(EXTRA_STVAR_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Stvar stvar = mStvari.get(position);
                return StvarCitanjeFragment.newInstance(stvar.getID());
            }
            @Override
            public int getCount() {
                return mStvari.size();
            }
        });

        for (int i = 0; i < mStvari.size(); i++) {
            if (mStvari.get(i).getID().equals(stvarId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
