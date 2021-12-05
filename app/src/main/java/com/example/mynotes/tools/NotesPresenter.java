package com.example.mynotes.tools;

import com.example.mynotes.ui.list.NotesListView;

public class NotesPresenter {

    private NotesListView view;

    private NotesRepository repository;

    public NotesPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void addNode(Note note){
        if (repository.addNote(note)){
            view.createNote(note);
        }
    }

    public void refresh() {
        view.showNotes(repository.getNotes());
    }
}
