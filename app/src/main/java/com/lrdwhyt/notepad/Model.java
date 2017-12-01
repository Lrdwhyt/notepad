package com.lrdwhyt.notepad;

import android.content.SharedPreferences;

import com.lrdwhyt.notepad.dbtasks.InsertNewNote;
import com.lrdwhyt.notepad.dbtasks.RetrieveNotes;
import com.lrdwhyt.notepad.dbtasks.UpdateNote;

public class Model {

    private DatabaseManager dbh;
    private SharedPreferences sp;

    public Model(DatabaseManager dbh, SharedPreferences sp) {
        this.dbh = dbh;
        this.sp = sp;
    }

    public void readFromDatabase(DatabaseSubscriber dbn) {
        RetrieveNotes dbTask = new RetrieveNotes(dbh, dbn);
        dbTask.execute();
    }

    public void writeToDatabase(DatabaseSubscriber dbs, String text) {
        InsertNewNote dbTask = new InsertNewNote(dbh, text, dbs);
        dbTask.execute();
    }

    public void writeToDBNew(DatabaseSubscriber dbs, long noteId, String text) {
        UpdateNote dbTask = new UpdateNote(dbh, noteId, text);
        dbTask.execute();
    }

    public SharedPreferences getSharedPreferences() {
        return sp;
    }

}
