package com.example.mynotes.tools;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class InMemoryNotesRepository extends ViewModel implements NotesRepository {

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final List<Note> notes = new ArrayList<>();

    private final Handler handler = new Handler(Looper.getMainLooper());


    public InMemoryNotesRepository(){
        super();
        addNote(new Note("dfgdfgf", "fghgfhgh", new Date()));
        addNote(new Note("dfgdfgf", "fghgfhgh", new Date()));
        addNote(new Note("dfgdfgf", "fghgfhgh", new Date()));
        addNote(new Note("dfgdfgf", "fghgfhgh", new Date()));
    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(notes);
                    }
                });
            }
        });
    }

    @Override
    public boolean addNote(Note note) {
        if (!notes.contains(note)) {
            notes.add(note);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeNote(Note note) {
        notes.remove(note);
        return true;
    }

    @Override
    public boolean clear(){
        notes.clear();
        return true;
    }
}
