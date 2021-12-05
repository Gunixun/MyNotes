package com.example.mynotes.tools;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository {

    private final List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        Note note = new Note();
        note.setTitle("Test");
        note.setBody("Test 2");
        addNote(note);
        return notes;
    }

    @Override
    public boolean addNote(Note note) {
        if (!notes.contains(note)) {
            notes.add(note);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeNote(Note note) {
        notes.remove(note);
        return true;
    }
}
