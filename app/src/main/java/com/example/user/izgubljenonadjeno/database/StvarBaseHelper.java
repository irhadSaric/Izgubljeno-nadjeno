package com.example.user.izgubljenonadjeno.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 6/11/2018.
 */

public class StvarBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String IME_BAZE = "stvariBaza.db";

    public StvarBaseHelper(Context context) {
        super(context, IME_BAZE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + StvarDbShema.StvarTable.IME_TABELE + "(" +
                " _id integer primary key autoincrement, " +
                StvarDbShema.StvarTable.Cols.UUID + ", " +
                StvarDbShema.StvarTable.Cols.NAZIV_STVARI + ", " +
                StvarDbShema.StvarTable.Cols.DATUM + ", " +
                StvarDbShema.StvarTable.Cols.LOKACIJA_KORISNIKA + ", " +
                StvarDbShema.StvarTable.Cols.LOKACIJA_PRONALASKA + ", " +
                StvarDbShema.StvarTable.Cols.KONTAKT + ", " +
                StvarDbShema.StvarTable.Cols.IZGUBLJENO + ", " +
                StvarDbShema.StvarTable.Cols.IME +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
