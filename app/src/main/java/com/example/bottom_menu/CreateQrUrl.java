package com.example.bottom_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class CreateQrUrl extends Fragment {

    ImageView close;
    Button apply;
    EditText editUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_url, container, false);
        close = (ImageView) view.findViewById(R.id.btnArrowBack);
        apply = (Button)  view.findViewById(R.id.btnGenerate);
        editUrl = view.findViewById(R.id.enterText);


        return view;
    }
}