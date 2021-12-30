package com.example.mynotes.ui.list;

import android.os.Bundle;

import com.example.mynotes.tools.Note;
import com.example.mynotes.ui.adapters.AdapterItem;

import java.util.ArrayList;

public interface NotesListView {

    void showProgress();

    void hideProgress();

    void updateNotes(ArrayList<AdapterItem> notes);

    void showError(String error);

    void onNoteRemoved(Note note);

    void onNotesRemoved();

    void openNote(String key, Bundle bundle);
}
