package com.example.bottom_menu.main.sub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottom_menu.DatabaseHelper;
import com.example.bottom_menu.ListAdapter;
import com.example.bottom_menu.QrModel;
import com.example.bottom_menu.R;

import java.util.Collections;
import java.util.List;

public class historyCreated extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_created, container, false);


        DatabaseHelper databaseHelper = new DatabaseHelper(this.requireContext());
        List<QrModel> allQr = databaseHelper.getAll();
        Collections.reverse(allQr);
        recyclerView = view.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(allQr, this.getContext());
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}

