package com.example.bottom_menu.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import com.example.bottom_menu.R;

public class SettingFragment extends Fragment {

    ImageView share, rule, dark, report;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        share = view.findViewById(R.id.shareApp);
        rule = view.findViewById(R.id.terms_of_use);
        dark = view.findViewById(R.id.dark_mode);
        report = view.findViewById(R.id.report);

        share.setOnClickListener(view1 -> {

        });
        rule.setOnClickListener(view1 -> {

        });
        dark.setOnClickListener(view1 -> {

        });
        report.setOnClickListener(view1 -> {

        });

        return view;
    }


}