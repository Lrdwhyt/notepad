package com.lrdwhyt.notepad.dbtasks;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDBContract;
import com.lrdwhyt.notepad.SQLiteHelper;

public class AddTagToNote extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private DatabaseSubscriber dbs;
    private long noteId;
    private String tag;

    public AddTagToNote(DatabaseManager dbh, DatabaseSubscriber dbs, long noteId, String tagName) {
        this.dbh = dbh;
        this.dbs = dbs;
        this.noteId = noteId;
        this.tag = tagName;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getWritableDatabase();
        db.execSQL("INSERT INTO " + NoteDBContract.TagRecords.TABLE_NAME +
                        " (" + NoteDBContract.TagRecords.COLUMN_NOTE + ", " + NoteDBContract.TagRecords.COLUMN_TAG + ")" +
                        " SELECT ?, _id FROM " + NoteDBContract.Tags.TABLE_NAME + " WHERE " + NoteDBContract.Tags.COLUMN_NAME + " = ?", new String[] { String.valueOf(noteId), tag });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
