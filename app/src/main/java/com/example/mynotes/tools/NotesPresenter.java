package com.example.mynotes.tools;

import com.example.mynotes.ui.list.NotesListView;

public class NotesPresenter {

    private NotesListView view;

    private NotesRepository repository;

    public NotesPresenter(NotesListView view) {
        this.view = view;
    }

    public void setRepository(NotesRepository repository){
        this.repository = repository;
    }

    public void refresh() {
        view.showNotes(repository.getNotes());
    }
}
