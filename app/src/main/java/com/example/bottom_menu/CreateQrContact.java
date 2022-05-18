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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class CreateQrContact  extends Fragment {

    ImageView close;
    Button apply,btnImport;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextAddress;
    EditText editTextCompany;
    EditText editTextPhoneNumber;
    EditText editTextEmail;
    ResultCreate resultCreate;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_contact, container, false);
        close =  view.findViewById(R.id.btn_ArrowBack);
        apply = view.findViewById(R.id.btnGenerate);
        btnImport =  view.findViewById(R.id.btnImport);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextCompany = view.findViewById(R.id.editTextCompany);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        editTextEmail = view.findViewById(R.id.editTextEmail);

        close.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());


        apply.setOnClickListener(view12 -> {
            String contact = "BEGIN:VCARD\nVERSION:3.0\nN:" + editTextFirstName.getText().toString().trim()
                                + ";" + editTextLastName.getText().toString().trim()
                                + "\nFN:\nORG:" + editTextCompany.getText().toString().trim()
                                + "\nADR:;;" + editTextAddress.getText().toString().trim()
                                + "\nTEL;WORK;VOICE:" + editTextPhoneNumber.getText().toString().trim()
                                + "\nEMAIL;WORK;INTERNET:" + editTextEmail.getText().toString().trim()
                                + "\nEND:VCARD";
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(contact, BarcodeFormat.QR_CODE, 250,250);
                resultCreate = new ResultCreate(matrix);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,resultCreate).addToBackStack(null).commit();
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
        return view;
    }
}