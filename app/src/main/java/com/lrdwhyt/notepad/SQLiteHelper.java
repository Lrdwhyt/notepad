package com.lrdwhyt.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lrdwh on 2017-09-03.
 */

public class SQLiteHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "notepad";
    public static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteDBContract.Notes.CREATE_TABLE);
        db.execSQL(NoteDBContract.Tags.CREATE_TABLE);
        db.execSQL(NoteDBContract.TagRecords.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NoteDBContract.Notes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NoteDBContract.Tags.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NoteDBContract.TagRecords.TABLE_NAME);
        onCreate(db);
    }
}
