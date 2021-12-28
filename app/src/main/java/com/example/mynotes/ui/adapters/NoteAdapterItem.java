package com.example.mynotes.ui.adapters;

import com.example.mynotes.tools.Note;

public class NoteAdapterItem extends AdapterItem {
    private final Note note;
    private final String title;
    private final String body;
    private final String date;

    public NoteAdapterItem(Note note, String title, String body, String date) {
        this.note = note;
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public Note getNote() {
        return note;
    }
}
