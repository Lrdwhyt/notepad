package com.lrdwhyt.notepad;

public interface EditorContract {

    interface View {

        void setTextSize(Float f);
        void initialiseText(String text);

    }

    interface Presenter {

        void updateNote(String text);
        void setNoteId(long noteId);
        void initialiseText();
        String[] getTagNames();
        boolean[] getTagStates();
        void createTag(String tag);
        void updateTags(boolean[] tags);

    }

}
