package com.example.user.izgubljenonadjeno;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by User on 6/8/2018.
 */

public class StvarFragment extends Fragment {
    private Stvar mStvar;
    private Button mDatum;
    private static final String ARG_STVAR_ID = "stvar_id";

    public static StvarFragment newInstance(UUID stvarId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STVAR_ID, stvarId);
        StvarFragment fragment = new StvarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID stvarId = (UUID) getArguments().getSerializable(ARG_STVAR_ID);
        mStvar = StvariLab.get(getActivity()).getStvar(stvarId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stvar, container, false);

        EditText nazivStvari = (EditText) v.findViewById(R.id.naziv_stvari);
        nazivStvari.setText(mStvar.getNazivStvari());
        nazivStvari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStvar.setNazivStvari(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDatum = (Button) v.findViewById(R.id.datum_pronalaska);
        mDatum.setText(mStvar.getDatum().toString());
        mDatum.setEnabled(false);

        return v;
    }
}
