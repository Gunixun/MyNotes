package com.example.mynotes.ui.note;

import android.os.Bundle;

public interface NoteView {
    void displayNote(String title, String body);

    void onSaved(String key, Bundle bundle);

    void dirtyClear();

    void setDirty();
}
