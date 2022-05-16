package com.example.bottom_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class CreateQrEmail extends Fragment {

    ImageView close;
    Button apply;
    EditText editTo;
    EditText editMessage;
    EditText editSubject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_email, container, false);
        close = (ImageView) view.findViewById(R.id.btn_ArrowBack);
        apply = (Button)  view.findViewById(R.id.btnGenerate);
        editTo = view.findViewById(R.id.toEmail);
        editMessage = view.findViewById(R.id.EditTextMessage);
        editSubject = view.findViewById(R.id.subject);
        close.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        return view;
    }
}