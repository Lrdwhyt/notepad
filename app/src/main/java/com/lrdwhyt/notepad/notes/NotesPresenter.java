package com.lrdwhyt.notepad.notes;

import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.Model;
import com.lrdwhyt.notepad.NoteEntry;

import java.util.List;

public class NotesPresenter implements NotesContract.Presenter, DatabaseSubscriber {

    private Model model;
    private NotesContract.View view;

    public NotesPresenter(Model model, NotesContract.View view) {
        this.model = model;
        this.view = view;
        model.readFromDatabase(this);
        int numberColumns = Integer.parseInt(model.getSharedPreferences().getString("pref_number_columns", "2"));
        view.setNumberColumns(numberColumns);
    }

    @Override
    public void onDeleteSelectedNotesClick(List noteIds) {
    }

    @Override
    public void onNoteRetrieve(NoteEntry note) {
    }

    @Override
    public void onDBInsertNew(long id) {
    }

    @Override
    public void attachView(NotesContract.View view) {
        this.view = view;
    }

    public void onDbChange() {
        model.readFromDatabase(this);
    }

    @Override
    public void onDBReadExecute(List results) {
        view.updateNotes(results);
    }
}