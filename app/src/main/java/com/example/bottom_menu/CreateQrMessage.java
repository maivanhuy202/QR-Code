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


public class CreateQrMessage  extends Fragment {

    ImageView close;
    Button apply;
    EditText editPN;
    EditText editMessage;
    ResultCreate resultCreate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_message, container, false);
        close = (ImageView) view.findViewById(R.id.btn_ArrowBack);
        apply = (Button)  view.findViewById(R.id.btnGenerate);
        editPN = view.findViewById(R.id.editTextTo);
        editMessage = view.findViewById(R.id.editTextMessage);
        close.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        apply.setOnClickListener(view12 -> {
            String sms = "SMSTO:" + editPN.getText().toString().trim() + ":" + editMessage.getText().toString().trim();
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(sms, BarcodeFormat.QR_CODE, 250,250);
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