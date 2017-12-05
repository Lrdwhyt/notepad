package com.lrdwhyt.notepad.dbtasks;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.NoteDB;
import com.lrdwhyt.notepad.SQLiteHelper;
import com.lrdwhyt.notepad.activities.EditorActivity;

// TODO: Implement
public class DeleteNote extends AsyncTask<Void, Void, Void> {

    private EditorActivity context;
    private long noteId;

    public DeleteNote(EditorActivity context, long noteId) {
        this.context = context;
        this.noteId = noteId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(context).getWritableDatabase();
        db.delete(NoteDB.Notes.TABLE_NAME, "_id = ?", new String[] {String.valueOf(noteId)});
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // context.updateDeletion(noteId);
        super.onPostExecute(aVoid);
    }

}
