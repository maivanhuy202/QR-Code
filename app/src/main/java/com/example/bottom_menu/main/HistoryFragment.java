package com.example.bottom_menu.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottom_menu.R;
import com.example.bottom_menu.main.sub.historyCreated;
import com.example.bottom_menu.main.sub.historyScanned;

public class HistoryFragment extends Fragment {
    ImageView imgCreated, imgScanned;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        imgCreated = view.findViewById(R.id.logoCreated);
        imgScanned = view.findViewById(R.id.logoScanned);
        historyCreated historyCreated = new historyCreated();
        historyScanned historyScanned = new historyScanned();
        imgCreated.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, historyCreated).addToBackStack(null).commit();
        });
        imgScanned.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, historyScanned).addToBackStack(null).commit();
        });
        return view;
    }
}

