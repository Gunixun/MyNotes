package com.example.mynotes.ui.list;

import com.example.mynotes.ui.adapters.AdapterItem;

import java.util.List;

public interface NotesListView {

    void showProgress();

    void hideProgress();

    void showNotes(List<AdapterItem> notes);

    void showError(String error);
}
