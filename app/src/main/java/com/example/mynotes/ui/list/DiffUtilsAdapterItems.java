package com.example.mynotes.ui.list;

import androidx.recyclerview.widget.DiffUtil;

import com.example.mynotes.ui.adapters.AdapterItem;
import com.example.mynotes.ui.adapters.NoteAdapterItem;

import java.util.ArrayList;

public class DiffUtilsAdapterItems extends DiffUtil.Callback  {
    ArrayList<AdapterItem> newData;
    ArrayList<AdapterItem> oldData;

    public DiffUtilsAdapterItems(ArrayList<AdapterItem> newData, ArrayList<AdapterItem> oldData) {
        this.newData = newData;
        this.oldData = oldData;
    }

    @Override
    public int getOldListSize() {
        return oldData != null ? oldData.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newData != null ? newData.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldPos, int newPos) {
        return newData.get(newPos) instanceof NoteAdapterItem
                && oldData.get(oldPos) instanceof NoteAdapterItem
                && ((NoteAdapterItem) newData.get(newPos)).getNote().getId().equals(
                        ((NoteAdapterItem) oldData.get(newPos)).getNote().getId());
    }

    @Override
    public boolean areContentsTheSame(int oldPos, int newPos) {
        if (newData.get(newPos) instanceof NoteAdapterItem
                && oldData.get(oldPos) instanceof NoteAdapterItem) {
            NoteAdapterItem newAdapter = (NoteAdapterItem) newData.get(newPos);
            NoteAdapterItem oldAdapter = (NoteAdapterItem) oldData.get(oldPos);
            return newAdapter.getBody().equals(oldAdapter.getBody())
                    && newAdapter.getTitle().equals(oldAdapter.getTitle());
        }
        return false;
    }
}
