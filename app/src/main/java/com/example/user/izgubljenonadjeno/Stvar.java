package com.example.user.izgubljenonadjeno;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 6/8/2018.
 */

public class Stvar {
    private UUID ID;
    private String nazivStvari;
    private Date datum;
    private String lokacijaPronalaska;
    private String lokacijaKorisnika;
    private String izgubljeno;
    private String kontaktTelefon;
    private String imeKorisnika;


    public Stvar() {
        this(UUID.randomUUID());
    }

    public Stvar(UUID uuid) {
        ID = uuid;
        datum = Calendar.getInstance().getTime();
        izgubljeno = "nadjeno";
    }

    public String getImeSlike(){ return "IMG_" + getID().toString() + ".jpg";}

    public String getIzgubljeno() {
        return izgubljeno;
    }

    public void setIzgubljeno(String izgubljeno) {
        this.izgubljeno = izgubljeno;
    }

    public String getLokacijaPronalaska() {
        return lokacijaPronalaska;
    }

    public void setLokacijaPronalaska(String lokacijaPronalaska) {
        this.lokacijaPronalaska = lokacijaPronalaska;
    }

    public String getLokacijaKorisnika() {
        return lokacijaKorisnika;
    }

    public void setLokacijaKorisnika(String lokacijaKorisnika) {
        this.lokacijaKorisnika = lokacijaKorisnika;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getNazivStvari() {
        return nazivStvari;
    }

    public void setNazivStvari(String nazivStvari) {
        this.nazivStvari = nazivStvari;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getKontaktTelefon() {
        return kontaktTelefon;
    }

    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon = kontaktTelefon;
    }

    public String getImeKorisnika() {
        return imeKorisnika;
    }

    public void setImeKorisnika(String imeKorisnika) {
        this.imeKorisnika = imeKorisnika;
    }
}
