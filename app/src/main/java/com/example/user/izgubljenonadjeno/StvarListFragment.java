package com.example.user.izgubljenonadjeno;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by User on 6/9/2018.
 */

public class StvarListFragment extends Fragment{

    private RecyclerView mStvarRecyclerView;
    private StvarAdapter mAdapter;
    private Callbacks mCallbacks;
    private boolean izgubljeno;
    private boolean nadjeno;
    private boolean sve;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onStvarSelected(Stvar stvar, boolean citanje);
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

    /*****************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nova_stvar:
                Stvar stvar = new Stvar();
                StvariLab.get(getActivity()).dodajStvar(stvar);
                updateUI();
                mCallbacks.onStvarSelected(stvar, false);
                return true;
            case R.id.izgubljene_stvari:
                izgubljeno = true;
                updateUI();
                return true;
            case R.id.nadjene_stvari:
                nadjeno = true;
                updateUI();
                return true;
            case R.id.sve_stvari:
                sve = true;
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_stvar_list, container, false);
        mStvarRecyclerView = (RecyclerView) view.findViewById(R.id.stvar_recycler_view);
        mStvarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();//111111111111111111111111111

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stvar_list, menu);
    }

    public void updateUI() {
        StvariLab stvariLab = StvariLab.get(getActivity());

        List<Stvar> stvari = stvariLab.getStvari();

        if(izgubljeno){
            stvari = stvariLab.getIzgubljene();
            izgubljeno = false;
        }
        if(nadjeno){
            stvari = stvariLab.getNadjene();
            nadjeno = false;
        }
        if(sve){
            stvari = stvariLab.getStvari();
            sve = false;
        }


        if (mAdapter == null) {
            mAdapter = new StvarAdapter(stvari);
            mStvarRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setStvari(stvari);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class StvarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNazivStvari;
        private TextView mDatum;
        private TextView mStaJe;
        private ImageView mSlikica;
        private Stvar mStvar;

        public void bind(Stvar stvar) {
            mStvar = stvar;
            mNazivStvari.setText(mStvar.getNazivStvari());
            mDatum.setText(mStvar.getDatum().toString());
            if(new String("jeste").equals(mStvar.getIzgubljeno())) {
                mStaJe.setText(getResources().getString(R.string.izgubljeno_string));
                mStaJe.setTextColor(getResources().getColor(R.color.crvena));
            }
            else {
                mStaJe.setText(getResources().getString(R.string.nadjeno_string));
                mStaJe.setTextColor(getResources().getColor(R.color.zelena));
            }
            File slikaFajl = StvariLab.get(getContext()).getSlika(mStvar);

            if (slikaFajl == null || !slikaFajl.exists()) {
                mSlikica.setImageResource(R.drawable.ic_upitnik);
            } else {
                Bitmap bitmap = PictureUtils.getScaledBitmap(slikaFajl.getPath(), getActivity());
                mSlikica.setImageBitmap(bitmap);
            }
        }

        public StvarHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_stvar, parent, false));
            itemView.setOnClickListener(this);
            mNazivStvari = (TextView) itemView.findViewById(R.id.stvar_naziv);
            mDatum = (TextView) itemView.findViewById(R.id.stvar_datum);
            mSlikica = (ImageView) itemView.findViewById(R.id.mala_slika);
            mStaJe = (TextView) itemView.findViewById(R.id.stvar_sta_je);
        }

        public void onClick(View view) {
            mCallbacks.onStvarSelected(mStvar, true);
        }
    }

    private class StvarAdapter extends RecyclerView.Adapter<StvarHolder> {
        private List<Stvar> mStvari;
        public StvarAdapter(List<Stvar> stvari) {
            mStvari = stvari;
        }

        @Override
        public StvarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new StvarHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(StvarHolder holder, int position) {
            Stvar stvar = mStvari.get(position);
            holder.bind(stvar);
        }

        @Override
        public int getItemCount() {
            return mStvari.size();
        }

        public void setStvari(List<Stvar> stvari) {
            mStvari = stvari;
        }
    }

}
