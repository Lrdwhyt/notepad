package com.lrdwhyt.notepad.dbtasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDBContract;
import com.lrdwhyt.notepad.NoteEntry;
import com.lrdwhyt.notepad.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ReadNoteTags extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private Cursor selectedNoteEntries;
    private DatabaseSubscriber dbs;
    private long id;

    public ReadNoteTags(DatabaseManager dbh, DatabaseSubscriber dbs, long id) {
        this.dbh = dbh;
        this.dbs = dbs;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getReadableDatabase();
        selectedNoteEntries = db.rawQuery("SELECT " + NoteDBContract.Tags.COLUMN_NAME + " FROM " + NoteDBContract.TagRecords.TABLE_NAME + " INNER JOIN " + NoteDBContract.Tags.TABLE_NAME + " WHERE " + NoteDBContract.TagRecords.COLUMN_NOTE + " = ? AND " + NoteDBContract.TagRecords.COLUMN_TAG + " = " + NoteDBContract.Tags._ID, new String[]{ String.valueOf(id) });
        return null;
    }

    @Override
    protected void onPostExecute(Void _void) {
        // Do something with selectedNoteEntries
        List<String> results = new ArrayList<>();
        while (selectedNoteEntries.moveToNext()) {
            results.add(selectedNoteEntries.getString(0));
        }
        selectedNoteEntries.close();
        dbs.onReadNoteTags(results);
        super.onPostExecute(_void);
    }

}
