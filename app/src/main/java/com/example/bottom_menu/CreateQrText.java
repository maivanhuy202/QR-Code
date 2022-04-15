package com.example.bottom_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class CreateQrText extends Fragment {

    ImageView close, apply;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_text, container, false);
        close = view.findViewById(R.id.btnClose);
        apply = view.findViewById(R.id.btnYes);
        editText = view.findViewById(R.id.enterText);


        return view;
    }
}