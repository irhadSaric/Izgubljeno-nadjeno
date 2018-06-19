package com.example.user.izgubljenonadjeno.database;

/**
 * Created by User on 6/11/2018.
 */

public class StvarDbShema {
    public static final class StvarTable {
        public static final String IME_TABELE = "stvari";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAZIV_STVARI = "nazivStvari";
            public static final String DATUM = "datum";
            public static final String LOKACIJA_PRONALASKA = "lokacijaPronalaska";
            public static final String LOKACIJA_KORISNIKA = "lokacijaKorisnika";
            public static final String KONTAKT = "kontakt";
            public static final String IZGUBLJENO = "izgubljeno";
            public static final String IME = "ime";
        }
    }
}
