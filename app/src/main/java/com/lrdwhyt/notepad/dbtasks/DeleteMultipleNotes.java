package com.lrdwhyt.notepad.dbtasks;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDB;
import com.lrdwhyt.notepad.SQLiteHelper;

import java.util.List;

public class DeleteMultipleNotes extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private DatabaseSubscriber dbs;
    private List<Long> notes;

    public DeleteMultipleNotes(DatabaseManager dbh, DatabaseSubscriber dbs, List notes) {
        this.dbh = dbh;
        this.dbs = dbs;
        this.notes = notes;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String commaSeparated = "(";
        for (Long id : notes) {
            commaSeparated += "'" + String.valueOf(id) + "',";
        }
        commaSeparated = commaSeparated.substring(0, commaSeparated.length() - 1);
        commaSeparated += ")";
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getWritableDatabase();
        db.execSQL("DELETE FROM " + NoteDB.Notes.TABLE_NAME +
                " WHERE " + NoteDB.Notes._ID + " IN " + commaSeparated);
        db.execSQL("DELETE FROM " + NoteDB.TagRecords.TABLE_NAME +
                        " WHERE " + NoteDB.TagRecords.COLUMN_NOTE + " IN " + commaSeparated);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dbs.onDeleteMultipleNotes();
        super.onPostExecute(aVoid);
    }

}
