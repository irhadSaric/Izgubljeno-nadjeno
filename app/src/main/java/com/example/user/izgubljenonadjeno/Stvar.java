package com.example.user.izgubljenonadjeno;

import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 6/8/2018.
 */

public class Stvar {
    private UUID ID;
    private String nazivStvari;
    private Date datum;
    private String lokacija;
    private String kontaktTelefon;
    private String imeKorisnika;

    public Stvar() {
        this.ID = UUID.randomUUID();
        this.datum = new Date();
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

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
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
