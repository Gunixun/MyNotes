package com.example.mynotes;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.mynotes.tools.InMemoryNotesRepository;
import com.example.mynotes.tools.Note;
import com.example.mynotes.ui.AboutProgrammFragment;
import com.example.mynotes.ui.list.NotesListFragment;
import com.example.mynotes.ui.note.NoteFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private InMemoryNotesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);

        repository = new ViewModelProvider(this).get(InMemoryNotesRepository.class);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item->{
            switch (item.getItemId()) {
                case R.id.action_program_info:
                    openFragment(new AboutProgrammFragment());
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        });

        connectSignals();

    }

    private void connectSignals(){
        FragmentManager fm = getSupportFragmentManager();

        fm.setFragmentResultListener(NotesListFragment.OPEN_NOTE_KEY, this, (requestKey, result) -> {
            Note note = result.getParcelable(NoteFragment.ARG_NOTE);
            openFragment(NoteFragment.newInstance(note));

        });
        fm.setFragmentResultListener(NoteFragment.NOTE_UPDATE_KEY, this, (requestKey, result) -> {
            Note note = result.getParcelable(NoteFragment.ARG_NOTE);
            if (!note.isEmpty()) {
                repository.addNote(note);
            }else{
                repository.removeNote(note);
            }
        });
        fm.setFragmentResultListener(NotesListFragment.REMOVE_NOTE_KEY, this, (requestKey, result) -> {
            Note note = result.getParcelable(NoteFragment.ARG_NOTE);
            repository.removeNote(note);
        });
    }

    public void supplyToolbar(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_app_bar_open_drawer_description,
                R.string.nav_app_bar_navigate_up_description
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("Transaction")
                .commit();
    }

}