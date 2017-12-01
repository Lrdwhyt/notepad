package com.lrdwhyt.notepad.dbtasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDBContract;
import com.lrdwhyt.notepad.SQLiteHelper;

public class InsertNewNote extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private DatabaseSubscriber dbs;
    private long noteId;
    private String text;

    public InsertNewNote(DatabaseManager dbh, String text, DatabaseSubscriber dbs) {
        this.dbh = dbh;
        this.text = text;
        this.dbs = dbs;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getWritableDatabase();
        ContentValues newEntry = new ContentValues();
        newEntry.put(NoteDBContract.Notes.COLUMN_TEXT, text);
        newEntry.put(NoteDBContract.Notes.COLUMN_DATE, String.valueOf(System.currentTimeMillis()));
        noteId = db.insert(NoteDBContract.Notes.TABLE_NAME, null, newEntry);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dbs.onDBInsertNew(noteId);
        super.onPostExecute(aVoid);
    }

}
