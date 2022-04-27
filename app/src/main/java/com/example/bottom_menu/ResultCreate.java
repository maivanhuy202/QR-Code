package com.example.bottom_menu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ResultCreate extends Fragment {
    ImageView result;
    BitMatrix bitMatrix;
    public ResultCreate(BitMatrix bitMatrix){
        this.bitMatrix = bitMatrix;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_generator, container, false);
        result = view.findViewById(R.id.imgResult);
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap bitmap = encoder.createBitmap(bitMatrix);
        result.setImageBitmap(bitmap);

        return view;
    }

}
