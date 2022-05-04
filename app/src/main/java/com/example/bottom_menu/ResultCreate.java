package com.example.bottom_menu;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class ResultCreate extends Fragment {
    ImageView result;
    ImageView share, save, copy, close;
    BitMatrix bitMatrix;
    public ResultCreate(BitMatrix bitMatrix){
        this.bitMatrix = bitMatrix;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_generator, container, false);
        share = view.findViewById(R.id.btn_share);
        save = view.findViewById(R.id.btn_save);
        copy = view.findViewById(R.id.btn_copy);
        result = view.findViewById(R.id.imgResult);
        close = view.findViewById(R.id.btnArrowBack);
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap bitmap = encoder.createBitmap(bitMatrix);
        result.setImageBitmap(bitmap);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        share.setOnClickListener(view1 -> {
            StrictMode.VmPolicy.Builder builder = new  StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            File f = new File(requireActivity().getExternalCacheDir() + "/" + getResources().getString(R.string.app_name) + ".png");
            Intent intent;

            try {
                FileOutputStream outputStream = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                outputStream.flush();
                outputStream.close();
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            }catch (Exception e){
                throw new RuntimeException(e);
            }

            startActivity(Intent.createChooser(intent,"share image"));
        });

        close.setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());
        return view;
    }

}
