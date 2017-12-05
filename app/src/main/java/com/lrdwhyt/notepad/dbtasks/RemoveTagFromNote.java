package com.lrdwhyt.notepad.dbtasks;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDB;
import com.lrdwhyt.notepad.SQLiteHelper;

public class RemoveTagFromNote extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private DatabaseSubscriber dbs;
    private long noteId;
    private String tag;

    public RemoveTagFromNote(DatabaseManager dbh, DatabaseSubscriber dbs, long noteId, String tagName) {
        this.dbh = dbh;
        this.dbs = dbs;
        this.noteId = noteId;
        this.tag = tagName;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getWritableDatabase();
        db.execSQL("DELETE FROM " + NoteDB.TagRecords.TABLE_NAME +
                " WHERE " + NoteDB.TagRecords.COLUMN_NOTE + " = ?" +
                " AND " + NoteDB.TagRecords.COLUMN_TAG + " = (SELECT _id FROM " + NoteDB.Tags.TABLE_NAME + " WHERE " + NoteDB.Tags.COLUMN_NAME + " = ?)", new String[] { String.valueOf(noteId), tag });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
