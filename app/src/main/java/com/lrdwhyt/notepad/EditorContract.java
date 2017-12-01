package com.lrdwhyt.notepad;

/**
 * Created by lrdwh on 2017-10-28.
 */

public interface EditorContract {

    interface View {

        void setTextSize(Float f);
        void initialiseText(String text);

    }

    interface Presenter {

        void updateNote(String text);
        void setNoteId(long noteId);

    }

}
