package com.example.user.izgubljenonadjeno;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class StvarListActivity extends SingleFragmentActivity implements StvarListFragment.Callbacks, StvarFragment.Callbacks{
    @Override
    public void onStvarUpdated(Stvar stvar) {
        StvarListFragment listFragment = (StvarListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    public void onStvarSelected(Stvar stvar, boolean citanje) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            if(citanje)
            {
                Intent intent = StvarCitanjePagerActivity.newIntent(this, stvar.getID());
                startActivity(intent);
            }
            else
            {
                Intent intent = DodavanjeStvariActivity.newIntent(this, stvar.getID());
                startActivity(intent);
            }
        } else {
            Fragment newDetail = StvarCitanjeFragment.newInstance(stvar.getID());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment createFragment() {
        return new StvarListFragment();
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stvar_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/

}
