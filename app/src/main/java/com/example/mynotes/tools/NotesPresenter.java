package com.example.mynotes.tools;

import android.os.Bundle;

import com.example.mynotes.ui.adapters.AdapterItem;
import com.example.mynotes.ui.adapters.NoteAdapterItem;
import com.example.mynotes.ui.list.NotesListView;
import com.example.mynotes.ui.note.NotePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesPresenter {

    public static final String KEY = "OPEN_NOTE_KEY";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

    private final NotesListView view;

    private final NotesRepository repository;

    public NotesPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        view.showProgress();

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                ArrayList<AdapterItem> adapterItems = new ArrayList<>();

                for (Note note : result) {
                    adapterItems.add(
                            new NoteAdapterItem(
                                    note,
                                    note.getTitle(),
                                    note.getBody(),
                                    dateFormat.format(note.getDate())
                            )
                    );
                }
                view.updateNotes(adapterItems);
                view.hideProgress();

            }

            @Override
            public void onError(Throwable err) {
                view.hideProgress();
            }
        });
    }

    public void removeNote(Note note) {
        view.showProgress();
        repository.removeNote(note, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.hideProgress();
                view.onNoteRemoved(note);
            }

            @Override
            public void onError(Throwable err) {
                view.hideProgress();
            }
        });
    }

    public void clear() {
        view.showProgress();
        repository.clear(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.hideProgress();
                view.onNotesRemoved();
            }

            @Override
            public void onError(Throwable err) {
                view.hideProgress();
            }
        });
    }

    public void openNote(Note note) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NotePresenter.ARG_NOTE, note);
        view.openNote(KEY, bundle);
    }
}
