package com.lrdwhyt.notepad;

import android.content.SharedPreferences;

import com.lrdwhyt.notepad.dbtasks.AddTagToNote;
import com.lrdwhyt.notepad.dbtasks.CreateTag;
import com.lrdwhyt.notepad.dbtasks.InsertNewNote;
import com.lrdwhyt.notepad.dbtasks.ReadAllTags;
import com.lrdwhyt.notepad.dbtasks.ReadNoteById;
import com.lrdwhyt.notepad.dbtasks.ReadNoteTags;
import com.lrdwhyt.notepad.dbtasks.RemoveTagFromNote;
import com.lrdwhyt.notepad.dbtasks.RetrieveNotes;
import com.lrdwhyt.notepad.dbtasks.UpdateNote;

public class Model {

    private DatabaseManager dbh;
    private SharedPreferences sp;

    public Model(DatabaseManager dbh, SharedPreferences sp) {
        this.dbh = dbh;
        this.sp = sp;
    }

    public void readNotesFromDB(DatabaseSubscriber dbn) {
        RetrieveNotes dbTask = new RetrieveNotes(dbh, dbn);
        dbTask.execute();
    }

    public void writeNewNote(DatabaseSubscriber dbs, String text) {
        InsertNewNote dbTask = new InsertNewNote(dbh, text, dbs);
        dbTask.execute();
    }

    public void updateNoteToDB(DatabaseSubscriber dbs, long noteId, String text) {
        UpdateNote dbTask = new UpdateNote(dbh, noteId, text);
        dbTask.execute();
    }

    public void readNoteFromDB(DatabaseSubscriber dbs, long noteId) {
        ReadNoteById dbTask = new ReadNoteById(dbh, dbs, noteId);
        dbTask.execute();
    }

    public void readTagList(DatabaseSubscriber dbs) {
        ReadAllTags dbTask = new ReadAllTags(dbh, dbs);
        dbTask.execute();
    }

    public void insertTag(DatabaseSubscriber dbs, String tag) {
        CreateTag dbTask = new CreateTag(dbh, tag, dbs);
        dbTask.execute();
    }

    public void readNoteTags(DatabaseSubscriber dbs, long noteId) {
        ReadNoteTags dbTask = new ReadNoteTags(dbh, dbs, noteId);
        dbTask.execute();
    }

    public void addTag(DatabaseSubscriber dbs, long noteId, String tag) {
        AddTagToNote dbTask = new AddTagToNote(dbh, dbs, noteId, tag);
        dbTask.execute();
    }

    public void removeTag(DatabaseSubscriber dbs, long noteId, String tag) {
        RemoveTagFromNote dbTask = new RemoveTagFromNote(dbh, dbs, noteId, tag);
        dbTask.execute();
    }

    public SharedPreferences getSharedPreferences() {
        return sp;
    }

}
