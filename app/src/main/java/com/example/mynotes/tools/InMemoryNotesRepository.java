package com.example.mynotes.tools;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNotesRepository extends ViewModel implements NotesRepository {

    private final List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
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
