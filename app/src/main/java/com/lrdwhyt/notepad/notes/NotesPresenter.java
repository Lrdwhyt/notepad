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
        model.readNotesFromDB(this);
        int numberColumns = Integer.parseInt(model.getSharedPreferences().getString("pref_number_columns", "2"));
        view.setNumberColumns(numberColumns);
    }

    @Override
    public void onReadMultipleTags(List tagList) {

    }

    @Override
    public void onDeleteSelectedNotesClick(List noteIds) {
    }

    @Override
    public void onReadSingleNote(NoteEntry note) {
    }

    @Override
    public void onInsertSingleNote(long id) {
    }

    @Override
    public void attachView(NotesContract.View view) {
        this.view = view;
    }

    public void onDbChange() {
        model.readNotesFromDB(this);
    }

    @Override
    public void onReadNoteTags(List results) {

    }

    @Override
    public void onInsertSingleTag() {

    }

    @Override
    public void onReadMultipleNotes(List results) {
        view.updateNotes(results);
    }
}