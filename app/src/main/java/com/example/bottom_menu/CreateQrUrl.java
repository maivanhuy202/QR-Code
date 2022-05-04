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

public class CreateQrUrl extends Fragment {

    ImageView close;
    Button apply;
    EditText editUrl;
    ResultCreate resultCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_url, container, false);
        close = (ImageView) view.findViewById(R.id.btnArrowBack);
        apply = (Button)  view.findViewById(R.id.btnGenerate);
        editUrl = view.findViewById(R.id.editTextURL);
        close.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        apply.setOnClickListener(view12 -> {
            String txt = editUrl.getText().toString().trim();
            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(txt, BarcodeFormat.QR_CODE, 250,250);
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