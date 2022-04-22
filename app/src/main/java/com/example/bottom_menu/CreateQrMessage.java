package com.example.bottom_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CreateQrMessage  extends Fragment {

    ImageView close;
    Button apply;
    EditText editPN;
    EditText editMessage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_message, container, false);
        close = (ImageView) view.findViewById(R.id.btnArrowBack);
        Fragment me = this;
        apply = (Button)  view.findViewById(R.id.btnGenerate);
        editPN = view.findViewById(R.id.editTextTo);
        editMessage = view.findViewById(R.id.editTextMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }
}