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

import com.example.bottom_menu.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class resultEmail extends BottomSheetDialogFragment {
    private String fetchAddress, fetchSubject, fetchBody;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_email, container, false);

        TextView address = view.findViewById(R.id.txt_name);
        TextView subject = view.findViewById(R.id.txt_result);
        TextView body = view.findViewById(R.id.txt_company);
        TextView btn_copy = view.findViewById(R.id.btn_copy);
        ImageView close = view.findViewById(R.id.btn_ArrowBack);
        ImageView btnShare = view.findViewById(R.id.btn_share);
        ImageView btnSend = view.findViewById(R.id.btn_import);

        address.setText(fetchAddress);
        subject.setText(fetchSubject);
        body.setText(fetchBody);

        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(subject.getText().toString() + " " + body.getText().toString());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null, subject.getText().toString() + " " + body.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard", Toast.LENGTH_LONG).show();
        });

        btnSend.setOnClickListener(view14 -> {
            String sendAddress = address.getText().toString();
            String sendSubject = subject.getText().toString();
            String sendBody = body.getText().toString();

            Uri uri = Uri.parse("MATMSG:TO:" + sendAddress + ";SUB:" + sendSubject + ";BODY:" + sendBody);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_CC, sendAddress);
            intent.putExtra(Intent.EXTRA_SUBJECT, sendSubject);
            intent.putExtra(Intent.EXTRA_TEXT, sendBody);
            startActivity(intent);
        });

        btnShare.setOnClickListener(view13 -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body1 = body.getText().toString().trim();
            String sub = subject.getText().toString().trim();
            String add = address.getText().toString().trim();
            myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
            myIntent.putExtra(Intent.EXTRA_TEXT, body1);
            myIntent.putExtra(Intent.EXTRA_CC, add);
            startActivity(Intent.createChooser(myIntent, "Share"));
        });

        close.setOnClickListener(view12 -> dismiss());

        return view;
    }

    public void fetch(String address, String subject, String body) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchAddress = address);
        executorService.execute(() -> fetchSubject = subject);
        executorService.execute(() -> fetchBody = body);
    }
}
