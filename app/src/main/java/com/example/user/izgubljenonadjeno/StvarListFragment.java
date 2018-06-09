package com.example.user.izgubljenonadjeno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by User on 6/9/2018.
 */

public class StvarListFragment extends Fragment{

    private RecyclerView mStvarRecyclerView;
    private StvarAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_stvar_list, container, false);
        mStvarRecyclerView = (RecyclerView) view.findViewById(R.id.stvar_recycler_view);
        mStvarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        StvariLab stvariLab = StvariLab.get(getActivity());
        List<Stvar> stvari = stvariLab.getStvari();

        if (mAdapter == null) {
            mAdapter = new StvarAdapter(stvari);
            mStvarRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private class StvarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNazivStvari;
        private TextView mDatum;
        private Stvar mStvar;

        public void bind(Stvar stvar) {
            mStvar = stvar;
            mNazivStvari.setText(mStvar.getNazivStvari());
            mDatum.setText(mStvar.getDatum().toString());
        }

        public StvarHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_stvar, parent, false));
            itemView.setOnClickListener(this);
            mNazivStvari = (TextView) itemView.findViewById(R.id.stvar_naziv);
            mDatum = (TextView) itemView.findViewById(R.id.stvar_datum);
        }

        @Override
        public void onClick(View view) {
            Intent intent = MainActivity.newIntent(getActivity(), mStvar.getID());
            startActivity(intent);
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
    }

}
