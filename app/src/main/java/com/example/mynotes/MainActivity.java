package com.example.mynotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mynotes.tools.InMemoryNotesRepository;
import com.example.mynotes.tools.Note;
import com.example.mynotes.tools.NotesPresenter;
import com.example.mynotes.ui.NavToolBar;
import com.example.mynotes.ui.AboutProgramFragment;
import com.example.mynotes.ui.note.NoteFragment;
import com.example.mynotes.ui.note.NotePresenter;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavToolBar {

    private DrawerLayout drawer;
    private InMemoryNotesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);

        repository = new ViewModelProvider(this).get(InMemoryNotesRepository.class);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_program_info:
                    openFragment(new AboutProgramFragment());
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        });

        connectSignals();

    }

    private void connectSignals() {
        FragmentManager fm = getSupportFragmentManager();

        fm.setFragmentResultListener(NotesPresenter.KEY, this, (requestKey, result) -> {
            Note note = result.getParcelable(NotePresenter.ARG_NOTE);
            openFragment(NoteFragment.newInstance(note));

        });
    }

    @Override
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

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.title)
                    .setMessage(R.string.message)
                    .setPositiveButton(R.string.positive, (dialogInterface, i) -> {
                        Toast.makeText(this, getResources().getString(R.string.close_message), Toast.LENGTH_SHORT).show();
                        super.onBackPressed();
                    })
                    .setNegativeButton(R.string.negative, (dialogInterface, i) -> {
                    })
                    .setCancelable(false)
                    .show();

        } else {
            super.onBackPressed();
        }
    }
}