package com.example.bottom_menu;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ItemList> {

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<ItemList> objects) {
        super(context, resource, objects);
    }
}
