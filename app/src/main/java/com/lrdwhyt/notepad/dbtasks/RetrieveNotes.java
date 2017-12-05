package com.lrdwhyt.notepad.dbtasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.NoteDBContract;
import com.lrdwhyt.notepad.NoteEntry;
import com.lrdwhyt.notepad.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class RetrieveNotes extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private Cursor selectedNoteEntries;
    private DatabaseSubscriber dbs;

    public RetrieveNotes(DatabaseManager dbh, DatabaseSubscriber dbs) {
        this.dbh = dbh;
        this.dbs = dbs;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getReadableDatabase();
        selectedNoteEntries = db.query(NoteDBContract.Notes.TABLE_NAME, new String[]{NoteDBContract.Notes._ID, NoteDBContract.Notes.COLUMN_TEXT, NoteDBContract.Notes.COLUMN_DATE}, null, null, null, null, "DATE desc", "200");
        return null;
    }

    @Override
    protected void onPostExecute(Void _void) {
        // Do something with selectedNoteEntries
        List<NoteEntry> results = new ArrayList<>();
        while (selectedNoteEntries.moveToNext()) {
            results.add(new NoteEntry(Long.parseLong(selectedNoteEntries.getString(0)), selectedNoteEntries.getString(1), selectedNoteEntries.getLong(2)));
        }
        selectedNoteEntries.close();
        dbs.onReadMultipleNotes(results);
        super.onPostExecute(_void);
    }

}
