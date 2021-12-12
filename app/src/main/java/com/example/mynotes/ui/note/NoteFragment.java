package com.example.mynotes.ui.note;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.mynotes.MainActivity;
import com.example.mynotes.R;
import com.example.mynotes.tools.Note;


public class NoteFragment extends Fragment {
    public static String ARG_NOTE = "ARG_NOTE";
    public static String NOTE_UPDATE_KEY = "NOTE_UPDATE_KEY";

    private EditText titleView;
    private EditText bodyView;
    private Note note;

    public NoteFragment() {
        super(R.layout.fragment_note);
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleView = view.findViewById(R.id.edit_view_title_note);
        bodyView = view.findViewById(R.id.edit_view_note);

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            note = getArguments().getParcelable(ARG_NOTE);
            displayNote(note);
        }

        view.findViewById(R.id.button_apply).setOnClickListener(v -> {
            if (note != null) {
                note.setBody(bodyView.getText().toString());
                note.setTitle(titleView.getText().toString());
                Bundle data = new Bundle();
                data.putParcelable(NoteFragment.ARG_NOTE, note);
                getParentFragmentManager().setFragmentResult(NOTE_UPDATE_KEY, data);
            }
        });

        view.findViewById(R.id.button_back).setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                ((MainActivity) activity).onBackPressed();
            }
        });
    }

    private void displayNote(Note note) {
        titleView.setText(note.getTitle());
        bodyView.setText(note.getBody());
    }
}
