package com.example.mynotes.ui.list;

import com.example.mynotes.tools.Note;

import java.util.List;

public interface NotesListView {
    void showNotes(List<Note> notes);

    void createNote(Note notes);
}
