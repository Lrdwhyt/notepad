package com.lrdwhyt.notepad;

import java.util.List;

public class NoteEntry {

    public long _id;
    public String text;
    public long date;

    public NoteEntry() {
        this.text = "";
    }

    public NoteEntry(long _id, String text, long date) {
        this._id = _id;
        this.text = text;
        this.date = date;
    }

}
