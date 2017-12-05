package com.lrdwhyt.notepad.dbtasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDB;
import com.lrdwhyt.notepad.NoteEntry;
import com.lrdwhyt.notepad.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ReadNotesByTag extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private Cursor selectedNoteEntries;
    private DatabaseSubscriber dbs;
    private String tag;

    public ReadNotesByTag(DatabaseManager dbh, DatabaseSubscriber dbs, String tag) {
        this.dbh = dbh;
        this.dbs = dbs;
        this.tag = tag;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getReadableDatabase();
        selectedNoteEntries = db.rawQuery("SELECT " + NoteDB.Notes.TABLE_NAME + "." + NoteDB.Notes._ID + ", " + NoteDB.Notes.TABLE_NAME + "." + NoteDB.Notes.COLUMN_TEXT + ", " + NoteDB.Notes.TABLE_NAME + "." + NoteDB.Notes.COLUMN_DATE +
                " FROM " + NoteDB.Notes.TABLE_NAME +
                " INNER JOIN " + NoteDB.Tags.TABLE_NAME + " ON tags._id = tagrecords.tag" +
                " INNER JOIN " + NoteDB.TagRecords.TABLE_NAME + " ON tagrecords.note = notes._id" +
                " WHERE tags.name = ?" +
                " ORDER BY notes.date desc", new String[] { tag });
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
