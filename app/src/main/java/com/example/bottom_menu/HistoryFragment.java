package com.example.bottom_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

public class HistoryFragment extends Fragment {
    ImageView imgCreated, imgScanned;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        imgCreated =view.findViewById(R.id.logoCreated);
        imgScanned = view.findViewById(R.id.logoScanned);
        historyCreated historyCreated = new historyCreated();
        imgCreated.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,historyCreated).addToBackStack(null).commit();
        });
        imgScanned.setOnClickListener(view12 -> {

        });
        return view;
    }
}

