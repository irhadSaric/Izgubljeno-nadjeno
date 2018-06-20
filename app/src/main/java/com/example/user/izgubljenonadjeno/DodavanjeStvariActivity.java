package com.example.user.izgubljenonadjeno;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class DodavanjeStvariActivity extends SingleFragmentActivity implements StvarFragment.Callbacks{

    private List<Stvar> mStvari;
    private static final String EXTRA_STVAR_ID ="com.example.user.izgubljenonadjeno.stvar_id";

    public static Intent newIntent(Context packageContext, UUID stvarId) {
        Intent intent = new Intent(packageContext, DodavanjeStvariActivity.class);
        intent.putExtra(EXTRA_STVAR_ID, stvarId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        mStvari = StvariLab.get(this).getStvari();
        UUID stvarId = (UUID) getIntent().getSerializableExtra(EXTRA_STVAR_ID);

        return StvarFragment.newInstance(stvarId);
    }

    @Override
    public void onStvarUpdated(Stvar stvar) {

    }
}
