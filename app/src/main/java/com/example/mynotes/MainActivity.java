package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.mynotes.tools.Note;
import com.example.mynotes.ui.list.NotesListFragment;
import com.example.mynotes.ui.note.NoteActivity;
import com.example.mynotes.ui.note.NoteFragment;


public class MainActivity extends AppCompatActivity {

    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        fm.setFragmentResultListener(NotesListFragment.RESULT_KEY, this, (requestKey, result) -> {
            selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                showNotes();
            } else {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra(NoteActivity.SELECTED_NOTE, selectedNote);
                startActivity(intent);
            }
        });
    }

    private void showNotes() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteFragment.ARG_NOTE, selectedNote);
        getSupportFragmentManager()
                .setFragmentResult(NoteFragment.NOTE_UPDATE_KEY, bundle);
    }
}