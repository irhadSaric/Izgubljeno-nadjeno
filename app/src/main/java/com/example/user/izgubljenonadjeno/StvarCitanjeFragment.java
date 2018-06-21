package com.example.user.izgubljenonadjeno;

/**
 * Created by User on 6/20/2018.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by User on 6/8/2018.
 */

public class StvarCitanjeFragment extends Fragment {
    private Stvar mStvar;
    private ImageView mSlika;
    private File mFajlSlika;
    private ImageButton mDugmeZaPoziv;

    private static final String ARG_STVAR_ID = "stvar_id";
    private static final int REQUEST_PERMISSION_POZIV = 1;

    private void updatePhotoView() {
        if (mFajlSlika == null || !mFajlSlika.exists()) {
            mSlika.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mFajlSlika.getPath(), getActivity());
            mSlika.setImageBitmap(bitmap);
        }
    }

    public static StvarCitanjeFragment newInstance(UUID stvarId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STVAR_ID, stvarId);
        StvarCitanjeFragment fragment = new StvarCitanjeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID stvarId = (UUID) getArguments().getSerializable(ARG_STVAR_ID);
        mStvar = StvariLab.get(getActivity()).getStvar(stvarId);
        mFajlSlika = StvariLab.get(getActivity()).getSlika(mStvar);
    }

    private void zovni(){
        String broj = mStvar.getKontaktTelefon().toString();
        if(broj.length() > 0){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_POZIV);
            }
            else
            {
                String pozvati = "tel:" + broj;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(pozvati)));
            }
        }
        else{
            Toast.makeText(getContext(), getResources().getString(R.id.unesi_broj), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_POZIV){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                zovni();
            }
            else {
                Toast.makeText(getContext(), getResources().getString(R.id.dozvola_za_poziv), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_citanje_stvar, container, false);
        PackageManager packageManager = getActivity().getPackageManager();

        TextView nazivStvari = (TextView) v.findViewById(R.id.naziv_stvari);
        TextView lokacijaPronalaska = (TextView) v.findViewById(R.id.lokacija_pronalaska);
        final TextView kontakt = (TextView) v.findViewById(R.id.kontakt);
        TextView lokacijaKorisnika = (TextView) v.findViewById(R.id.lokacija_korisnika);
        TextView imeKorisnika = (TextView) v.findViewById(R.id.ime_korisnika);
        TextView datum = (TextView) v.findViewById(R.id.datum_pronalaska);
        mDugmeZaPoziv = (ImageButton) v.findViewById(R.id.pozovi);

        mSlika = (ImageView) v.findViewById(R.id.stvar_slika);
        updatePhotoView();

        final RadioButton izgubljeno = (RadioButton) v.findViewById(R.id.izgubljeno_radio);

        nazivStvari.setText(mStvar.getNazivStvari());
        lokacijaPronalaska.setText(mStvar.getLokacijaPronalaska());
        kontakt.setText(mStvar.getKontaktTelefon());
        lokacijaKorisnika.setText(mStvar.getLokacijaKorisnika());
        imeKorisnika.setText(mStvar.getImeKorisnika());
        izgubljeno.setChecked(new String("jeste").equals(mStvar.getIzgubljeno()));
        datum.setText(mStvar.getDatum().toString());

        mDugmeZaPoziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zovni();
                //Toast.makeText(getContext(), mStvar.getIzgubljeno(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
