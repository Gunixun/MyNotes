package com.example.mynotes.ui.list;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mynotes.R;
import com.example.mynotes.tools.InMemoryNotesRepository;
import com.example.mynotes.tools.Note;
import com.example.mynotes.tools.NotesPresenter;
import com.example.mynotes.ui.NavToolBar;
import com.example.mynotes.ui.adapters.AdapterItem;
import com.example.mynotes.ui.dialogs.BottomSheetFragment;
import com.example.mynotes.ui.note.NoteFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String REMOVE_NOTE_KEY = "REMOVE_NOTE_KEY";
    public static final String OPEN_NOTE_KEY = "OPEN_NOTE_KEY";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView notesList;
    private NotesPresenter presenter;
    private CoordinatorLayout rootLayout;

    private NotesAdapter adapter;

    public NotesListFragment() {
        super(R.layout.fragment_note_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesPresenter(this);

        adapter = new NotesAdapter();
        adapter.setOnClick(this::setResult);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InMemoryNotesRepository repository = new ViewModelProvider(requireActivity()).get(InMemoryNotesRepository.class);
        presenter.setRepository(repository);

        rootLayout = view.findViewById(R.id.root_layout);

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.requestNotes();
        });

        notesList = view.findViewById(R.id.notes_list);
        notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        notesList.setAdapter(adapter);

        view.findViewById(R.id.button_add).setOnClickListener(v -> {
            setResult(new Note());
        });
        presenter.requestNotes();

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        if (getActivity() instanceof NavToolBar) {
            ((NavToolBar) getActivity()).supplyToolbar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_clear) {
                repository.clear();
                presenter.requestNotes();
                return true;
            }
            return false;
        });

        getChildFragmentManager().setFragmentResultListener(BottomSheetFragment.REMOVE_NOTE, this, (requestKey, result) -> {
            Note note = result.getParcelable(NoteFragment.ARG_NOTE);
            repository.removeNote(note);
            presenter.requestNotes();
        });
    }

    private void setResult(Note note) {
        Bundle data = new Bundle();
        data.putParcelable(NoteFragment.ARG_NOTE, note);
        getParentFragmentManager().setFragmentResult(OPEN_NOTE_KEY, data);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNotes(List<AdapterItem> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(rootLayout, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, view -> {
                    presenter.requestNotes();
                })
                .show();
    }
}
