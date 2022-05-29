package com.example.bottom_menu.create;

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

import com.example.bottom_menu.DatabaseHelper;
import com.example.bottom_menu.QrModel;
import com.example.bottom_menu.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CreateQrText extends Fragment {

    ImageView close;
    Button apply;
    EditText editText;
    ResultCreate resultCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_text, container, false);
        close = (ImageView) view.findViewById(R.id.btn_ArrowBack);
        apply = (Button) view.findViewById(R.id.btnGenerate);
        editText = view.findViewById(R.id.enterText);
        close.setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());
        apply.setOnClickListener(view1 -> {
            String txt = editText.getText().toString().trim();
            MultiFormatWriter writer = new MultiFormatWriter();

            QrModel qrModel;
            String date = android.text.format.DateFormat.format("kk:mm:ss, dd-MM-yyyy", new java.util.Date()).toString();
            try {
                qrModel = new QrModel(5, date, txt, false, txt);
                BitMatrix matrix = writer.encode(txt, BarcodeFormat.QR_CODE, 250, 250);
                resultCreate = new ResultCreate(matrix);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, resultCreate).addToBackStack(null).commit();
            } catch (WriterException e) {
                e.printStackTrace();
                qrModel = new QrModel(-1, "null", "null", false, "ERROR");
            }

            DatabaseHelper databaseHelper = new DatabaseHelper(this.getContext());
            databaseHelper.add(qrModel);
        });


        return view;
    }
}