package com.example.mynotes.ui.note;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynotes.R;
import com.example.mynotes.tools.InMemoryNotesRepository;
import com.example.mynotes.tools.Note;


public class NoteFragment extends Fragment implements NoteView {
    private EditText titleView;
    private EditText bodyView;
    private Button btnApply;

    NotePresenter presenter;

    public NoteFragment() {
        super(R.layout.fragment_note);
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NotePresenter.ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InMemoryNotesRepository repository = new ViewModelProvider(requireActivity()).get(InMemoryNotesRepository.class);

        titleView = view.findViewById(R.id.edit_view_title_note);
        bodyView = view.findViewById(R.id.edit_view_note);

        if (getArguments() == null || getArguments().getParcelable(NotePresenter.ARG_NOTE) == null) {
            presenter = new NotePresenter(this, repository);
        } else{
            presenter = new NotePresenter(
                    this,
                    repository,
                    getArguments().getParcelable(NotePresenter.ARG_NOTE)
            );
        }

        btnApply = view.findViewById(R.id.button_apply);
        btnApply.setOnClickListener(v -> {
            presenter.onSaved(
                    titleView.getText().toString(),
                    bodyView.getText().toString()
            );
        });

        view.findViewById(R.id.button_back).setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.onBackPressed();
            }
        });
    }

    @Override
    public void displayNote(String title, String body) {
        titleView.setText(title);
        bodyView.setText(body);
    }

    @Override
    public void onSaved(String key, Bundle bundle) {
        getParentFragmentManager().setFragmentResult(key, bundle);
    }

    @Override
    public void dirtyClear() {
        btnApply.setEnabled(false);
    }


}
