package com.example.user.izgubljenonadjeno.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.user.izgubljenonadjeno.Stvar;

import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 6/13/2018.
 */

public class StvarCursorWrapper extends CursorWrapper {
    public StvarCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Stvar getStvar() {
        String uuidString = getString(getColumnIndex(StvarDbShema.StvarTable.Cols.UUID));
        String nazivStvari = getString(getColumnIndex(StvarDbShema.StvarTable.Cols.NAZIV_STVARI));
        long date = getLong(getColumnIndex(StvarDbShema.StvarTable.Cols.DATUM));
        String lokacijaPronalaska = getString(getColumnIndex(StvarDbShema.StvarTable.Cols.LOKACIJA_PRONALASKA));
        String lokacijaKorisnika = getString(getColumnIndex(StvarDbShema.StvarTable.Cols.LOKACIJA_KORISNIKA));
        int izgubljeno = getInt(getColumnIndex(StvarDbShema.StvarTable.Cols.IZGUBLJENO));
        String kontaktTelefon = getString(getColumnIndex(StvarDbShema.StvarTable.Cols.KONTAKT));
        String imeKorisnika = getString(getColumnIndex(StvarDbShema.StvarTable.Cols.IME));

        Stvar stvar = new Stvar(UUID.fromString(uuidString));
        stvar.setNazivStvari(nazivStvari);
        stvar.setDatum(new Date(date));
        stvar.setLokacijaPronalaska(lokacijaPronalaska);
        stvar.setLokacijaKorisnika(lokacijaKorisnika);
        stvar.setKontaktTelefon(kontaktTelefon);
        stvar.setImeKorisnika(imeKorisnika);
        stvar.setIzgubljeno(izgubljeno);

        return stvar;
    }
}
