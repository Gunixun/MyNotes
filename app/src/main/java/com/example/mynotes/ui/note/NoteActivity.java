package com.example.mynotes.ui.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.mynotes.R;
import com.example.mynotes.tools.Note;

public class NoteActivity extends AppCompatActivity {

    public static final String SELECTED_NOTE = "SELECTED_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        } else {
            if (savedInstanceState == null) {
                FragmentManager fm = getSupportFragmentManager();

                Note note = getIntent().getParcelableExtra(SELECTED_NOTE);

                fm.beginTransaction()
                        .replace(R.id.container, NoteFragment.newInstance(note))
                        .commit();
            }
        }
    }
}