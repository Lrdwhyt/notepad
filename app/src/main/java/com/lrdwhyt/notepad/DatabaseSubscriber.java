package com.lrdwhyt.notepad;

import java.util.List;

public interface DatabaseSubscriber {

    void onReadMultipleNotes(List results);
    void onInsertSingleNote(long id);
    void onReadSingleNote(NoteEntry note);
    void onReadMultipleTags(List<String> tagList);
    void onInsertSingleTag();
    void onReadNoteTags(List results);

}
