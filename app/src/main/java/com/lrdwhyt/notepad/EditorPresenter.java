package com.lrdwhyt.notepad;

import java.util.List;

/**
 * Created by lrdwh on 2017-10-28.
 */

public class EditorPresenter implements EditorContract.Presenter, DatabaseSubscriber {

    private Model model;
    private EditorContract.View view;
    private long noteId = -1;
    String oldText = "";

    public EditorPresenter(Model model, EditorContract.View view) {
        this.model = model;
        this.view = view;
        model.readFromDatabase(this);
        float textSize = Float.parseFloat(model.getSharedPreferences().getString("editor_font_size", "16"));
        view.setTextSize(textSize);
        initialiseText();
    }

    public void initialiseText() {
        model.readNoteFromDatabase(noteId);
        view.initialiseText(oldText);
    }

    @Override
    public void updateNote(String text) {
        if (text.equals(oldText)) {
            //  Text is unchanged, no need to write to database
            return;
        }
        if (noteId < 0 && text.equals("")) {
            // New note is blank and shouldn't be saved
            return;
        }

        if (noteId < 0) {
            // New note has to be created
            model.writeToDatabase(this, text);
        } else {
            // Editing an existing note
            model.writeToDBNew(this, noteId, text);
        }

        oldText = text;
    }

    @Override
    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    @Override
    public void onDBReadExecute(List results) {}

    @Override
    public void onDBInsertNew(long id) {
        noteId = id;
    }
}
