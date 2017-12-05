package com.lrdwhyt.notepad;

import android.provider.BaseColumns;

/**
 * Created by lrdwh on 2017-09-03.
 */

public final class NoteDB {

    private NoteDB() {}

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

    public static class TagRecords implements BaseColumns {
        public static final String TABLE_NAME = "tagrecords";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_TAG = "tag";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                COLUMN_NOTE + " INTEGER, " +
                COLUMN_TAG + " INTEGER)";
    }

}
