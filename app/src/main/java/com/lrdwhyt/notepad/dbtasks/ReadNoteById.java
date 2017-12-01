package com.lrdwhyt.notepad.dbtasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDBContract;
import com.lrdwhyt.notepad.NoteEntry;
import com.lrdwhyt.notepad.SQLiteHelper;

public class ReadNoteById extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private Cursor selectedNoteEntries;
    private DatabaseSubscriber dbs;
    private long id;

    public ReadNoteById(DatabaseManager dbh, DatabaseSubscriber dbs, long id) {
        this.dbh = dbh;
        this.dbs = dbs;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getReadableDatabase();
        selectedNoteEntries = db.query(NoteDBContract.Notes.TABLE_NAME, new String[]{NoteDBContract.Notes._ID, NoteDBContract.Notes.COLUMN_TEXT, NoteDBContract.Notes.COLUMN_DATE}, "_id = ?", new String[] { String.valueOf(id) }, null, null, null);
        return null;
    }

    @Override
    protected void onPostExecute(Void _void) {
        // Do something with selectedNoteEntries
        NoteEntry result;
        selectedNoteEntries.moveToFirst();
        result = new NoteEntry(Long.parseLong(selectedNoteEntries.getString(0)), selectedNoteEntries.getString(1), selectedNoteEntries.getLong(2));
        selectedNoteEntries.close();
        dbs.onNoteRetrieve(result);
        super.onPostExecute(_void);
    }

}
