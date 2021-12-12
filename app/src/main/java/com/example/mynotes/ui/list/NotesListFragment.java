package com.example.mynotes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynotes.MainActivity;
import com.example.mynotes.R;
import com.example.mynotes.tools.InMemoryNotesRepository;
import com.example.mynotes.tools.Note;
import com.example.mynotes.tools.NotesPresenter;
import com.example.mynotes.ui.note.NoteFragment;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String REMOVE_NOTE_KEY = "REMOVE_NOTE_KEY";
    public static final String OPEN_NOTE_KEY = "OPEN_NOTE_KEY";

    private LinearLayout notesContainer;
    private NotesPresenter presenter;

    public NotesListFragment() {
        super(R.layout.fragment_note_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesPresenter(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InMemoryNotesRepository repository = new ViewModelProvider(requireActivity()).get(InMemoryNotesRepository.class);
        presenter.setRepository(repository);

        notesContainer = view.findViewById(R.id.notes_container);

        view.findViewById(R.id.button_add).setOnClickListener(v -> {
            setResult(new Note());
        });
        presenter.refresh();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).supplyToolbar(toolbar);


        toolbar.setOnMenuItemClickListener(item->{
            if (item.getItemId() == R.id.action_clear){
                repository.clear();
                presenter.refresh();
                return true;
            }
            return false;
        });
    }

    @Override
    public void showNotes(List<Note> notes) {
        notesContainer.removeAllViews();
        for (Note note : notes) {
            createNote(note);
        }
    }

    private void createNote(Note note) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesContainer, false);
        view.setOnClickListener(v -> {
            setResult(note);
        });
        view.setOnLongClickListener(v->{
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            requireActivity().getMenuInflater().inflate(R.menu.menu_note, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item->{
                switch (item.getItemId()) {
                    case R.id.action_remove:
                        Bundle data = new Bundle();
                        data.putParcelable(NoteFragment.ARG_NOTE, note);
                        getParentFragmentManager().setFragmentResult(REMOVE_NOTE_KEY, data);
                        presenter.refresh();
                        return true;
                }
                return false;
            });
            popupMenu.show();
            return true;
        });
        ((TextView) view.findViewById(R.id.note_title)).setText(note.getTitle());
        ((TextView) view.findViewById(R.id.note_body)).setText(note.getBody());
        ((TextView) view.findViewById(R.id.note_date)).setText(note.getDateString());
        notesContainer.addView(view);
    }

    private void setResult(Note note) {
        Bundle data = new Bundle();
        data.putParcelable(NoteFragment.ARG_NOTE, note);
        getParentFragmentManager().setFragmentResult(OPEN_NOTE_KEY, data);
    }
}
