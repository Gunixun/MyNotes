package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.mynotes.tools.InMemoryNotesRepository;
import com.example.mynotes.tools.Note;
import com.example.mynotes.tools.NotesRepository;
import com.example.mynotes.ui.list.NotesListFragment;
import com.example.mynotes.ui.note.NoteFragment;


public class MainActivity extends AppCompatActivity {

    private InMemoryNotesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new ViewModelProvider(this).get(InMemoryNotesRepository.class);

        FragmentManager fm = getSupportFragmentManager();

        fm.setFragmentResultListener(NotesListFragment.OPEN_NOTE_KEY, this, (requestKey, result) -> {
            Note note = result.getParcelable(NoteFragment.ARG_NOTE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, NoteFragment.newInstance(note))
                    .addToBackStack("Transaction1")
                    .commit();

        });
        fm.setFragmentResultListener(NoteFragment.NOTE_UPDATE_KEY, this, (requestKey, result) -> {
            Note note = result.getParcelable(NoteFragment.ARG_NOTE);
            if (!note.isEmpty()) {
                repository.addNote(note);
            }else{
                repository.removeNote(note);
            }

        });
    }
}