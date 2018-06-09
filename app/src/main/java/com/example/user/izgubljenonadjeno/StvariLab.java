package com.example.user.izgubljenonadjeno;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 6/8/2018.
 */

public class StvariLab {
    private static StvariLab sStvariLab;
    private List<Stvar> mStvari;

    public static StvariLab get(Context context) {
        if (sStvariLab == null) {
            sStvariLab = new StvariLab(context);
        }
        return sStvariLab;
    }

    private StvariLab(Context context) {
        mStvari = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Stvar stvar = new Stvar();
            stvar.setNazivStvari("Stvar #" + i);
            mStvari.add(stvar);
        }
    }

    public List<Stvar> getStvari() {
        return mStvari;
    }

    public Stvar getStvar(UUID id) {
        for (Stvar stvar : mStvari) {
            if (stvar.getID().equals(id)) {
                return stvar;
            }
        }
        return null;
    }
}
