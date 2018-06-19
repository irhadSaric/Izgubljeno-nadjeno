package com.example.user.izgubljenonadjeno;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatumPickerFragment extends DialogFragment {
    private static final String ARG_DATUM = "date";
    public static final String EXTRA_DATUM = "com.example.user.izgubljenonadjeno.datum";
    private DatePicker mDatumPicker;

    public static DatumPickerFragment newInstance(Date datum) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATUM, datum);
        DatumPickerFragment fragment = new DatumPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATUM, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date datum = (Date) getArguments().getSerializable(ARG_DATUM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        int godina = calendar.get(Calendar.YEAR);
        int mjesec = calendar.get(Calendar.MONTH);
        int dan = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_datum, null);

        mDatumPicker = (DatePicker) v.findViewById(R.id.dialog_datum_picker);
        mDatumPicker.init(godina, mjesec, dan, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.datum_picker_naslov)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int godina = mDatumPicker.getYear();
                                int mjesec = mDatumPicker.getMonth();
                                int dan = mDatumPicker.getDayOfMonth();
                                Date date = new GregorianCalendar(godina, mjesec, dan).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }
}
