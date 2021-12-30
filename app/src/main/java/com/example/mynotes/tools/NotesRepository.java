package com.example.mynotes.tools;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void updateNote(String noteId, String title, String body, Callback<Note> callback);

    void removeNote(Note note, Callback<Void> callback);

    void clear(Callback<Void> callback);
}
