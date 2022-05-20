package com.example.bottom_menu;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
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

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class resultWifi extends BottomSheetDialogFragment {
    private String fetchSsid, fetchPassword;
    private int fetchEncryptionType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_wifi, container, false);

        TextView result = view.findViewById(R.id.txt_result);
        TextView btn_copy = view.findViewById(R.id.btn_copy);
        ImageView close = view.findViewById(R.id.btn_ArrowBack);
        ImageView btnShare = view.findViewById(R.id.btn_share);
        ImageView btnConnect = view.findViewById(R.id.btn_connect);

        String ET;
        if (fetchEncryptionType == 1) ET = "None";
        else if (fetchEncryptionType == 2) ET = "WPA/WPA2";
        else ET = "WEP";
        String temp = "SSID: " + fetchSsid + ", P:" +fetchPassword + ", Encryption type:" + ET;
        result.setText(temp);

        btn_copy.setOnClickListener(view1 -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(result.getText());
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText(null,result.getText());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(requireContext(), "Text copied into clipboard",Toast.LENGTH_LONG).show();
        });

        btnShare.setOnClickListener(view13 -> {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body = result.getText().toString().trim();
            String sub = "";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent, "Share"));
        });

        btnConnect.setOnClickListener(view14 -> {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = String.format("\"%s\"", fetchSsid);
            wifiConfiguration.preSharedKey = String.format("\"%s\"", fetchPassword);
            WifiManager wifiManager = (WifiManager) requireActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //remember id
            int netId = wifiManager.addNetwork(wifiConfiguration);
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();
        });

        close.setOnClickListener(view12 -> dismiss());

        return view;
    }
    public void fetch(String ssid, String password, int encryptionType) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(() -> fetchSsid = ssid);
        executorService.execute(() -> fetchPassword = password);
        executorService.execute(() -> fetchEncryptionType = encryptionType);
    }
}
