package com.lrdwhyt.notepad.dbtasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDB;
import com.lrdwhyt.notepad.SQLiteHelper;

public class CreateTag extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private DatabaseSubscriber dbs;
    private long tagId;
    private String tag;

    public CreateTag(DatabaseManager dbh, String tag, DatabaseSubscriber dbs) {
        this.dbh = dbh;
        this.tag = tag;
        this.dbs = dbs;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getWritableDatabase();
        ContentValues newEntry = new ContentValues();
        newEntry.put(NoteDB.Tags.COLUMN_NAME, tag);
        tagId = db.insert(NoteDB.Tags.TABLE_NAME, null, newEntry);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dbs.onInsertSingleTag();
        super.onPostExecute(aVoid);
    }

}
