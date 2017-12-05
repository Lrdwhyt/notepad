package com.lrdwhyt.notepad.notes;

import com.lrdwhyt.notepad.NoteEntry;

import java.util.List;

public interface NotesContract {

    interface View {

        void updateNotes(List<NoteEntry> l);
        void populateDrawer(List<String> drawerItems);
        void setNumberColumns(int i);

    }

    interface Presenter {

        void attachView(NotesContract.View view);
        void onDeleteSelectedNotesClick(List list);
        List<String> getTagList();
        void browseTag(String tagName);
        void browseAll();

    }

}
