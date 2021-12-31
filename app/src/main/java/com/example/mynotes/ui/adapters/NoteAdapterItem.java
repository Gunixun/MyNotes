package com.example.mynotes.ui.adapters;

import com.example.mynotes.tools.Note;

public class NoteAdapterItem extends AdapterItem {
    private final Note note;
    private final String title;
    private final String body;
    private final String date;

    public NoteAdapterItem(Note note, String title, String body, String date) {
        super(note.getId());
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NoteAdapterItem)) {
            return false;
        } else {
            return getBody().equals(((NoteAdapterItem) o).getBody())
                    && getTitle().equals(((NoteAdapterItem) o).getTitle());
        }
    }
}
