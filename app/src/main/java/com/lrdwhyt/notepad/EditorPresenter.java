package com.lrdwhyt.notepad;

import android.util.Log;

import java.util.List;

public class EditorPresenter implements EditorContract.Presenter, DatabaseSubscriber {

    String oldText = "";
    NoteEntry note;
    private Model model;
    private EditorContract.View view;
    private long noteId = -1;
    private String[] tagNameList = new String[0];
    private boolean[] tagStateList = new boolean[0];

    public EditorPresenter(Model model, EditorContract.View view) {
        this.model = model;
        this.view = view;
        float textSize = Float.parseFloat(model.getSharedPreferences().getString("editor_font_size", "16"));
        view.setTextSize(textSize);
        model.readTagList(this);
    }

    @Override
    public void onReadSingleNote(NoteEntry note) {
        this.note = note;
        view.initialiseText(note.text);
        oldText = note.text;
    }

    @Override
    public void initialiseText() {
        model.readNoteFromDB(this, noteId);
    }

    @Override
    public String[] getTagNames() {
        return tagNameList;
    }

    @Override
    public void onReadMultipleTags(List<String> tagList) {
        tagNameList = new String[tagList.size()];
        tagNameList = tagList.toArray(tagNameList);
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
            model.writeNewNote(this, text);
        } else {
            // Editing an existing note
            model.updateNoteToDB(this, noteId, text);
        }

        oldText = text;
    }

    @Override
    public void setNoteId(long noteId) {
        this.noteId = noteId;
        model.readNoteTags(this, noteId);
    }

    @Override
    public void onReadMultipleNotes(List results) {
    }

    @Override
    public void onReadNoteTags(List results) {
        tagStateList = new boolean[tagNameList.length];
        for (int i = 0; i < tagNameList.length; ++i) {
            if (results.contains(tagNameList[i])) {
                tagStateList[i] = true;
            } else {
                tagStateList[i] = false;
            }
        }
    }

    @Override
    public void onInsertSingleNote(long id) {
        noteId = id;
    }

    @Override
    public void createTag(String tag) {
        model.insertTag(this, tag);
    }

    @Override
    public void onDeleteMultipleNotes() {

    }

    @Override
    public void updateTags(boolean[] tags) {
        for (int i = 0; i < tags.length; ++i) {
            if (tags[i] != tagStateList[i]) {
                Log.d("updateTags!!", tagNameList[i] + String.valueOf(tags[i]));
                if (tags[i] == false) {
                    model.removeTag(this, noteId, tagNameList[i]);
                } else {
                    model.addTag(this, noteId, tagNameList[i]);
                }
            }
        }
        tagStateList = tags;
    }

    @Override
    public boolean[] getTagStates() {
        return tagStateList;
    }

    @Override
    public void onInsertSingleTag() {

    }
}
