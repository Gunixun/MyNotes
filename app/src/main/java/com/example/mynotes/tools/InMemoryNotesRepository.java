package com.example.mynotes.tools;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class InMemoryNotesRepository extends ViewModel implements NotesRepository {

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final List<Note> notes = new ArrayList<>();

    private final Handler handler = new Handler(Looper.getMainLooper());


    public InMemoryNotesRepository(){
        super();
        notes.add(new Note("dfgdfgf", "fghgfhgh", UUID.randomUUID().toString()));
        notes.add(new Note("dfgdfgf", "fghgfhgh", UUID.randomUUID().toString()));
        notes.add(new Note("dfgdfgf", "fghgfhgh", UUID.randomUUID().toString()));
        notes.add(new Note("dfgdfgf", "fghgfhgh", UUID.randomUUID().toString()));
    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        executor.execute(()->{
            handler.post(()->{
                callback.onSuccess(notes);
            });
        });
    }

    @Override
    public void updateNote(String noteId, String title, String body, Callback<Note> callback){
        executor.execute(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(()->{
                int index = -1;

                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).getId().equals(noteId)) {
                        index = i;
                        break;
                    }
                }

                if (index == -1){
                    notes.add(new Note(title, body, noteId));
                }
                else {

                    Note note = notes.get(index);

                    note.setTitle(title);
                    note.setBody(body);
                    if (note.isEmpty()) {
                        notes.remove(note);
                        callback.onSuccess(null);
                    } else {
                        callback.onSuccess(note);
                    }
                }
            });
        });
    }

    @Override
    public void removeNote(Note note, Callback<Void> callback) {

        executor.execute(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(()->{
                notes.remove(note);
                callback.onSuccess(null);
            });
        });
    }

    @Override
    public void clear(Callback<Void> callback){

        executor.execute(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(()->{
                notes.clear();
                callback.onSuccess(null);
            });
        });
    }
}
