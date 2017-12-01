package com.lrdwhyt.notepad;

import java.util.List;

public interface DatabaseSubscriber {

    void onDBReadExecute(List results);
    void onDBInsertNew(long id);
    void onNoteRetrieve(NoteEntry note);

}
