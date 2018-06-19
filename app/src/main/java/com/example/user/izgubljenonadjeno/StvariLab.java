package com.example.user.izgubljenonadjeno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.user.izgubljenonadjeno.database.StvarBaseHelper;
import com.example.user.izgubljenonadjeno.database.StvarCursorWrapper;
import com.example.user.izgubljenonadjeno.database.StvarDbShema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 6/8/2018.
 */

public class StvariLab {
    private static StvariLab sStvariLab;
    private Context mContext;
    private SQLiteDatabase mBaza;

    public File getSlika(Stvar stvar) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, stvar.getImeSlike());
    }

    private static ContentValues getContentValues(Stvar stvar) {
        ContentValues values = new ContentValues();
        values.put(StvarDbShema.StvarTable.Cols.UUID, stvar.getID().toString());
        values.put(StvarDbShema.StvarTable.Cols.DATUM, stvar.getDatum().getTime());
        values.put(StvarDbShema.StvarTable.Cols.IZGUBLJENO, stvar.getIzgubljeno());
        values.put(StvarDbShema.StvarTable.Cols.IME, stvar.getImeKorisnika());
        values.put(StvarDbShema.StvarTable.Cols.KONTAKT, stvar.getKontaktTelefon());
        values.put(StvarDbShema.StvarTable.Cols.LOKACIJA_KORISNIKA, stvar.getLokacijaKorisnika());
        values.put(StvarDbShema.StvarTable.Cols.LOKACIJA_PRONALASKA, stvar.getLokacijaPronalaska());
        values.put(StvarDbShema.StvarTable.Cols.NAZIV_STVARI, stvar.getNazivStvari());

        return values;
    }

    private StvariLab(Context context) {
        mContext = context.getApplicationContext();
        mBaza = new StvarBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public static StvariLab get(Context context) {
        if (sStvariLab == null) {
            sStvariLab = new StvariLab(context);
        }
        return sStvariLab;
    }

    public void dodajStvar(Stvar stvar){
        ContentValues values = getContentValues(stvar);
        mBaza.insert(StvarDbShema.StvarTable.IME_TABELE, null, values);
    }

    private StvarCursorWrapper queryStvari(String whereClause, String[] whereArgs) {
        Cursor cursor = mBaza.query(
                StvarDbShema.StvarTable.IME_TABELE,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new StvarCursorWrapper(cursor);
    }

    public void updateStvar(Stvar stvar) {
        String uuidString = stvar.getID().toString();
        ContentValues values = getContentValues(stvar);
        mBaza.update(StvarDbShema.StvarTable.IME_TABELE, values,
                StvarDbShema.StvarTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public List<Stvar> getStvari() {
        List<Stvar> crimes = new ArrayList<>();
        StvarCursorWrapper cursor = queryStvari(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getStvar());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Stvar getStvar(UUID id) {
        StvarCursorWrapper cursor = queryStvari(
                StvarDbShema.StvarTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getStvar();
        } finally {
            cursor.close();
        }
    }
}
