package com.example.mynotes.tools;

import com.example.mynotes.ui.adapters.AdapterItem;
import com.example.mynotes.ui.adapters.NoteAdapterItem;
import com.example.mynotes.ui.list.NotesListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesPresenter {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

    private NotesListView view;

    private NotesRepository repository;

    public NotesPresenter(NotesListView view) {
        this.view = view;
    }

    public void setRepository(NotesRepository repository) {
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
                view.showNotes(adapterItems);
                view.hideProgress();

            }

            @Override
            public void onError(Throwable err) {
                view.hideProgress();
            }
        });
    }
}
