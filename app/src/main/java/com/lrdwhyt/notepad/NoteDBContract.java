package com.lrdwhyt.notepad;

import android.provider.BaseColumns;

/**
 * Created by lrdwh on 2017-09-03.
 */

public final class NoteDBContract {

    private NoteDBContract() {}

    public static class Notes implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TEXT = "textBody";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TAGS = "tags";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEXT + " TEXT, " +
                COLUMN_DATE + " INTEGER, " +
                COLUMN_TAGS + " TEXT)";
    }

    public static class Tags implements BaseColumns {
        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT)";
    }

}
