package com.example.bottom_menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class resultPhoneNumber extends BottomSheetDialogFragment {
    private String fetchNum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_phone_number, container, false);


        TextView phoneNumber = view.findViewById(R.id.txt_subject);
        TextView btn_copy = view.findViewById(R.id.btn_copy);
        ImageView close = view.findViewById(R.id.btn_ArrowBack);
        ImageView btn_call = view.findViewById(R.id.btn_call);
        ImageView btnShare = view.findViewById(R.id.btn_share);
        phoneNumber.setText(fetchNum);

        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(phoneNumber.getText());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null,phoneNumber.getText());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard",Toast.LENGTH_LONG).show();
        });

        btnShare.setOnClickListener(view13 -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = phoneNumber.getText().toString().trim();
            String sub = "";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent, "Share"));
        });

        btn_call.setOnClickListener(view14 -> {
            String phone = phoneNumber.getText().toString();
            Uri uri = Uri.parse("tel:" + phone);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
        });

        close.setOnClickListener(view12 -> dismiss());

        return view;
    }
    public void fetch(String num) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchNum = num);
    }

}
