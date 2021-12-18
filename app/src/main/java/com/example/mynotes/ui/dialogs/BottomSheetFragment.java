package com.example.mynotes.ui.dialogs;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mynotes.R;
import com.example.mynotes.tools.Note;
import com.example.mynotes.ui.note.NoteFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    public static final String REMOVE_NOTE = "REMOVE_NOTE";

    private Note note;

    public static BottomSheetFragment newInstance(Note note) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putParcelable(NoteFragment.ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(NoteFragment.ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.remove).setOnClickListener(v -> {
            Bundle data = new Bundle();
            data.putParcelable(NoteFragment.ARG_NOTE, note);
            getParentFragmentManager().setFragmentResult(REMOVE_NOTE, data);
            dismiss();
        });
        view.findViewById(R.id.cancel).setOnClickListener(v -> {
            dismiss();
        });
    }

}