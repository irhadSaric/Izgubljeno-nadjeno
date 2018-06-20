package com.example.user.izgubljenonadjeno;

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
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 6/8/2018.
 */

public class StvarFragment extends Fragment {
    private Stvar mStvar;
    private Button mDatum;
    private ImageButton mDugmeZaSliku;
    private ImageView mSlika;
    private File mFajlSlika;
    private Button mDugmeZaPoziv;

    private static final String ARG_STVAR_ID = "stvar_id";
    private static final String DIALOG_DATUM = "DialogDatum";
    private static final int REQUEST_DATUM = 0;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_PERMISSION_POZIV = 1;

    private Callbacks mCallbacks;
    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onStvarUpdated(Stvar stvar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /******************************************/

    private void updatePhotoView() {
        if (mFajlSlika == null || !mFajlSlika.exists()) {
            mSlika.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mFajlSlika.getPath(), getActivity());
            mSlika.setImageBitmap(bitmap);
        }
    }

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
        mFajlSlika = StvariLab.get(getActivity()).getSlika(mStvar);
    }

    @Override
    public void onPause() {
        super.onPause();
        StvariLab.get(getActivity()).updateStvar(mStvar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_DATUM){
            Date date = (Date) data.getSerializableExtra(DatumPickerFragment.EXTRA_DATUM);
            mStvar.setDatum(date);
            updateStvar();
            updateDatum();
            Log.d("filter", mStvar.getDatum().toString());
        }
        else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.criminalintent.fileprovider",
                    mFajlSlika);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updateStvar();
            updatePhotoView();
        }
    }

    private void updateStvar() {
        StvariLab.get(getActivity()).updateStvar(mStvar);
        mCallbacks.onStvarUpdated(mStvar);
    }

    private void updateDatum() {
        mDatum.setText(mStvar.getDatum().toString());
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
            Toast.makeText(getContext(), "Unesite kontakt telefon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_POZIV){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                zovni();
            }
            else {
                Toast.makeText(getContext(), "Nemate dozvolu za pozive", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stvar, container, false);
        PackageManager packageManager = getActivity().getPackageManager();

        EditText nazivStvari = (EditText) v.findViewById(R.id.naziv_stvari);
        EditText lokacijaPronalaska = (EditText) v.findViewById(R.id.lokacija_pronalaska);
        final EditText kontakt = (EditText) v.findViewById(R.id.kontakt);
        EditText lokacijaKorisnika = (EditText) v.findViewById(R.id.lokacija_korisnika);
        EditText imeKorisnika = (EditText) v.findViewById(R.id.ime_korisnika);
        mDugmeZaPoziv = (Button) v.findViewById(R.id.pozovi);

        mDugmeZaSliku = (ImageButton) v.findViewById(R.id.stvar_kamera);
        mSlika = (ImageView) v.findViewById(R.id.stvar_slika);
        updatePhotoView();

        final RadioButton izgubljeno = (RadioButton) v.findViewById(R.id.izgubljeno_radio);

        nazivStvari.setText(mStvar.getNazivStvari());
        lokacijaPronalaska.setText(mStvar.getLokacijaPronalaska());
        kontakt.setText(mStvar.getKontaktTelefon());
        lokacijaKorisnika.setText(mStvar.getLokacijaKorisnika());
        imeKorisnika.setText(mStvar.getImeKorisnika());
        izgubljeno.setChecked(new String("jeste").equals(mStvar.getIzgubljeno()));

        /***********************************************************************/

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mFajlSlika != null &&
                captureImage.resolveActivity(packageManager) != null;
        mDugmeZaSliku.setEnabled(canTakePhoto);
        mDugmeZaSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.example.user.izgubljenonadjeno.fileprovider",
                        mFajlSlika);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        /***************************************************************************/

        mDugmeZaPoziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zovni();
                /*Intent callIntent = new Intent(Intent.ACTION_CALL);
                String brojTelefona = "";
                brojTelefona = "tel:" + mStvar.getKontaktTelefon();
                //Toast.makeText(getContext(), brojTelefona, Toast.LENGTH_SHORT).show();

                callIntent.setData(Uri.parse(brojTelefona));

                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), brojTelefona, Toast.LENGTH_SHORT).show();
                }
                startActivity(callIntent);*/
            }
        });

        /***************************************************************************/

        izgubljeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStvar.setIzgubljeno("jeste");
                updateStvar();
            }
        });

        nazivStvari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStvar.setNazivStvari(charSequence.toString());
                updateStvar();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lokacijaPronalaska.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStvar.setLokacijaPronalaska(charSequence.toString());
                updateStvar();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        kontakt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStvar.setKontaktTelefon(charSequence.toString());
                updateStvar();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lokacijaKorisnika.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStvar.setLokacijaKorisnika(charSequence.toString());
                updateStvar();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imeKorisnika.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStvar.setImeKorisnika(charSequence.toString());
                updateStvar();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDatum = (Button) v.findViewById(R.id.datum_pronalaska);
        updateDatum();

        mDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatumPickerFragment dialog = DatumPickerFragment.newInstance(mStvar.getDatum());
                dialog.setTargetFragment(StvarFragment.this, REQUEST_DATUM);
                dialog.show(manager, DIALOG_DATUM);
            }
        });

        return v;
    }
}
