package com.example.bottom_menu;

import android.content.Context;
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

public class resultText extends BottomSheetDialogFragment {
    private String fetchText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_text, container, false);


        TextView title = view.findViewById(R.id.txt_title);
        TextView btn_copy = view.findViewById(R.id.copy);
        ImageView close = view.findViewById(R.id.close);

        title.setText(fetchText);

        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(title.getText());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null,title.getText());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard",Toast.LENGTH_LONG).show();
        });

        close.setOnClickListener(view12 -> dismiss());

        return view;
    }
    public void fetchText(String text) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchText = text);
    }
}
