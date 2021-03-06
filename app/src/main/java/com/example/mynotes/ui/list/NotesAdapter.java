package com.example.mynotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.tools.Note;
import com.example.mynotes.ui.adapters.AdapterItem;
import com.example.mynotes.ui.adapters.NoteAdapterItem;

import java.util.ArrayList;
import java.util.Collection;


public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final ArrayList<AdapterItem> data = new ArrayList<>();
    private OnClick onClick;

    public void setData(ArrayList<AdapterItem> notes) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilsAdapterItems(notes, data));
        data.clear();
        data.addAll(notes);
        diffResult.dispatchUpdatesTo(this);
    }

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    interface OnClick {
        void onClick(Note note);

        void onLongClick(Note note);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    public ArrayList<AdapterItem> removeItem(Note note) {
        ArrayList<AdapterItem> newList = new ArrayList<>(data);

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof NoteAdapterItem && ((NoteAdapterItem) data.get(i)).getNote().getId().equals(note.getId())) {
                newList.remove(i);
                break;
            }
        }
        return newList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NoteViewHolder) {

            NoteViewHolder noteViewHolder = (NoteViewHolder) holder;

            NoteAdapterItem note = (NoteAdapterItem) data.get(position);

            noteViewHolder.getTitle().setText(note.getTitle());
            noteViewHolder.getMessage().setText(note.getBody());
            noteViewHolder.getDate().setText(note.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        private final TextView body;

        private final TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.note_title);

            body = itemView.findViewById(R.id.note_body);

            date = itemView.findViewById(R.id.note_date);

            CardView card = itemView.findViewById(R.id.card);

            card.setOnClickListener(view -> {
                AdapterItem item = data.get(getAdapterPosition());

                if (item instanceof NoteAdapterItem) {
                    if (getOnClick() != null) {
                        getOnClick().onClick(((NoteAdapterItem) item).getNote());
                    }
                }
            });
            card.setOnLongClickListener(view -> {
                AdapterItem item = data.get(getAdapterPosition());

                if (item instanceof NoteAdapterItem) {
                    if (getOnClick() != null) {
                        getOnClick().onLongClick(((NoteAdapterItem) item).getNote());
                    }
                }
                return true;
            });
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getMessage() {
            return body;
        }

        public TextView getDate() {
            return date;
        }
    }
}
