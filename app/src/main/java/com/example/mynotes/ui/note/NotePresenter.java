package com.example.mynotes.ui.note;

import android.os.Bundle;

import com.example.mynotes.tools.Callback;
import com.example.mynotes.tools.Note;
import com.example.mynotes.tools.NotesRepository;

import java.util.UUID;


public class NotePresenter {
    public static String ARG_NOTE = "ARG_NOTE";
    public static String KEY = "NOTE_UPDATE_KEY";

    private NoteView view;
    private NotesRepository repository;
    private String noteId;

    public NotePresenter(NoteView view, NotesRepository repository, Note note) {
        this.view = view;
        this.repository = repository;

        view.displayNote(note.getTitle(), note.getBody());
        this.noteId = note.getId();
    }

    public NotePresenter(NoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;

        this.noteId = UUID.randomUUID().toString();
    }

    public void onSaved(String title, String body) {
        view.dirtyClear();

        repository.updateNote(noteId, title, body, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.onSaved(KEY, bundle);
            }

            @Override
            public void onError(Throwable err) {
                view.setDirty();
            }
        });
    }
}
