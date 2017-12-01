package com.lrdwhyt.notepad.dbtasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.NoteDBContract;
import com.lrdwhyt.notepad.SQLiteHelper;

public class UpdateNote extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbm;
    private long noteId;
    private String text;

    public UpdateNote(DatabaseManager dbm, long noteId, String text) {
        this.dbm = dbm;
        this.noteId = noteId;
        this.text = text;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbm.getContext()).getWritableDatabase();
        ContentValues currentEntry = new ContentValues();
        currentEntry.put(NoteDBContract.Notes.COLUMN_TEXT, text);
        currentEntry.put(NoteDBContract.Notes.COLUMN_DATE, String.valueOf(System.currentTimeMillis()));
        db.update(NoteDBContract.Notes.TABLE_NAME, currentEntry, "_id = ?", new String[]{Long.toString(noteId)});
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
