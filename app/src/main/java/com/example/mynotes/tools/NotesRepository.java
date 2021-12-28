package com.example.mynotes.tools;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    boolean addNote(Note note);

    boolean removeNote(Note note);

    boolean clear();
}
