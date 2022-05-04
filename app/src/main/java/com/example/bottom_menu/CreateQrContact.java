package com.example.bottom_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class CreateQrContact  extends Fragment {

    ImageView close;
    Button apply,btnImport;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextAddress;
    EditText editTextCompany;
    EditText editTextPhoneNumber;
    EditText editTextEmail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_contact, container, false);
        close = (ImageView) view.findViewById(R.id.btnArrowBack);
        apply = (Button)  view.findViewById(R.id.btnGenerate);
        btnImport = view.findViewById(R.id.btnImport);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextCompany = view.findViewById(R.id.editTextCompany);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        editTextEmail = view.findViewById(R.id.editTextEmail);

        close.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        return view;
    }
}