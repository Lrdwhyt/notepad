package com.lrdwhyt.notepad.notes;

import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.Model;
import com.lrdwhyt.notepad.NoteEntry;

import java.util.ArrayList;
import java.util.List;

public class NotesPresenter implements NotesContract.Presenter, DatabaseSubscriber {

    private Model model;
    private NotesContract.View view;
    List<String> tagNameList = new ArrayList<>();

    public NotesPresenter(Model model, NotesContract.View view) {
        this.model = model;
        this.view = view;
        browseAll();
        int numberColumns = Integer.parseInt(model.getSharedPreferences().getString("pref_number_columns", "2"));
        view.setNumberColumns(numberColumns);
        model.readTagList(this);
    }

    @Override
    public void onReadMultipleTags(List tagList) {
        tagNameList = tagList;
        view.populateDrawer(tagList);
    }

    @Override
    public void onDeleteSelectedNotesClick(List noteIds) {
        model.deleteNotes(this, noteIds);
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

    @Override
    public void onDeleteMultipleNotes() {
        browseAll();
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

    @Override
    public void browseAll() {
        model.readNotesFromDB(this);
    }

    @Override
    public List<String> getTagList() {
        return tagNameList;
    }

    @Override
    public void browseTag(String tagName) {
        model.readNotesByTag(this, tagName);
    }
}