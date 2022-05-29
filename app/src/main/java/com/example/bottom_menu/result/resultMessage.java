package com.example.bottom_menu.result;

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
import androidx.fragment.app.DialogFragment;

import com.example.bottom_menu.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class resultMessage extends BottomSheetDialogFragment {
    private String fetchPhoneNum, fetchMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_message, container, false);


        TextView message = view.findViewById(R.id.txt_result);
        TextView phoneNum = view.findViewById(R.id.txt_name);
        TextView btn_copy = view.findViewById(R.id.btn_copy);
        ImageView close = view.findViewById(R.id.btn_ArrowBack);
        ImageView btnShare = view.findViewById(R.id.btn_share);
        ImageView btnSend = view.findViewById(R.id.btn_import);

        phoneNum.setText(fetchPhoneNum);
        message.setText(fetchMessage);

        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(phoneNum.getText().toString() + " " + message.getText().toString());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null, phoneNum.getText().toString() + " " + message.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard", Toast.LENGTH_LONG).show();
        });

        btnSend.setOnClickListener(view14 -> {
            String smsNumber = phoneNum.getText().toString();
            String smsText = message.getText().toString();

            Uri uri = Uri.parse("smsto:" + smsNumber);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", smsText);
            startActivity(intent);
        });

        btnShare.setOnClickListener(view13 -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = phoneNum.getText().toString().trim() + " " + message.getText().toString().trim();
            String sub = "";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
            myIntent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(myIntent, "Share"));
        });

        close.setOnClickListener(view12 -> dismiss());

        return view;
    }

    public void fetch(String phoneNum, String message) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchPhoneNum = phoneNum);
        executorService.execute(() -> fetchMessage = message);
    }
}
