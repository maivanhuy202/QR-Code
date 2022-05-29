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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bottom_menu.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class resultUrl extends DialogFragment {

    private TextView title;
    private String fetchUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_url, container, false);

        title = view.findViewById(R.id.txt_result);

        Button btnCopy = view.findViewById(R.id.btn_copy);
        ImageView btnBrowser = view.findViewById(R.id.btn_connect);
        ImageView close = view.findViewById(R.id.btn_ArrowBack);
        ImageView btnShare = view.findViewById(R.id.btn_share);
        title.setText(fetchUrl);

        btnShare.setOnClickListener(view13 -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = title.getText().toString().trim();
            String sub = "";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
            myIntent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(myIntent, "Share"));
        });

        btnCopy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(title.getText());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null, title.getText());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard", Toast.LENGTH_LONG).show();
        });
        btnBrowser.setOnClickListener(view1 -> {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(fetchUrl));
            startActivity(intent);
        });

        close.setOnClickListener(view12 -> dismiss());


        return view;
    }

    public void fetchUrl(String url) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchUrl = url);
    }
}
