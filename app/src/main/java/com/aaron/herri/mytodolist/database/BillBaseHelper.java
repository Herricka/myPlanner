package com.aaron.herri.mytodolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by herri on 10/21/2015.
 */
public class BillBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "billBase.db";

    public BillBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BillDbSchema.BillTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        BillDbSchema.BillTable.Cols.UUID + ", " +
                        BillDbSchema.BillTable.Cols.TITLE + ", " +
                        BillDbSchema.BillTable.Cols.DESCRIPTION + ", " +
                        BillDbSchema.BillTable.Cols.PRIORITY + ", " +
                        BillDbSchema.BillTable.Cols.DATE + ", " +
                        BillDbSchema.BillTable.Cols.PAID +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
