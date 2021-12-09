package com.example.mynotes.tools;

import java.util.List;

public interface NotesRepository {

    public List<Note> getNotes();

    boolean addNote(Note note);

    boolean removeNote(Note note);
}
