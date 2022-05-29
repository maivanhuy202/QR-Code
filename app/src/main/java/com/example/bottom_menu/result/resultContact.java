package com.example.bottom_menu.result;

import android.content.Context;
import android.content.Intent;
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

public class resultContact extends BottomSheetDialogFragment {
    private String fetchAddress, fetchCompany, fetchPhone, fetchEmail, fetchName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_contact, container, false);

        TextView name = view.findViewById(R.id.txt_name);
        TextView address = view.findViewById(R.id.txt_result);
        TextView company = view.findViewById(R.id.txt_company);
        TextView phone = view.findViewById(R.id.txt_phone);
        TextView email = view.findViewById(R.id.txt_email);
        TextView btn_copy = view.findViewById(R.id.btn_copy);
        ImageView close = view.findViewById(R.id.btn_ArrowBack);
        ImageView btnShare = view.findViewById(R.id.btn_share);
        ImageView btnImport = view.findViewById(R.id.btn_import);

        name.setText(fetchName);
        address.setText(fetchAddress);
        company.setText(fetchCompany);
        phone.setText(fetchPhone);
        email.setText(fetchEmail);

        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText("Name: " + name
                        + "\nAddress: " + address
                        + "\nCompany: " + company
                        + "\nPhone Number: " + phone
                        + "\nEmail: " + email);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null, "Name: " + name
                        + "\nAddress: " + address
                        + "\nCompany: " + company
                        + "\nPhone Number: " + phone
                        + "\nEmail: " + email);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard", Toast.LENGTH_LONG).show();
        });

        btnImport.setOnClickListener(view14 -> {
        });

        btnShare.setOnClickListener(view13 -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String txt = "Name: " + name
                    + "\nAddress: " + address
                    + "\nCompany: " + company
                    + "\nPhone Number: " + phone
                    + "\nEmail: " + email;
            myIntent.putExtra(Intent.EXTRA_TEXT, txt);
            startActivity(Intent.createChooser(myIntent, "Share"));
        });

        close.setOnClickListener(view12 -> dismiss());

        return view;
    }

    public void fetch(String name, String address, String company, String phone, String email) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchName = name);
        executorService.execute(() -> fetchAddress = address);
        executorService.execute(() -> fetchCompany = company);
        executorService.execute(() -> fetchPhone = phone);
        executorService.execute(() -> fetchEmail = email);
    }
}
