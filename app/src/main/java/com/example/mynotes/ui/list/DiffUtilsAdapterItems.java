package com.example.mynotes.ui.list;

import androidx.recyclerview.widget.DiffUtil;

import com.example.mynotes.ui.adapters.AdapterItem;

import java.util.List;

public class DiffUtilsAdapterItems extends DiffUtil.Callback  {
    private final List<AdapterItem> newData;
    private final List<AdapterItem> oldData;

    public DiffUtilsAdapterItems(List<AdapterItem> newData, List<AdapterItem> oldData) {
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
        return newData.get(newPos).getKey().equals(oldData.get(oldPos).getKey());
    }

    @Override
    public boolean areContentsTheSame(int oldPos, int newPos) {
        return newData.get(newPos).equals(oldData.get(oldPos));
    }
}
