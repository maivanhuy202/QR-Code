package com.example.bottom_menu;

import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.result.SMSMMSResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import com.google.zxing.common.BitMatrix;

public class CreateQrMessage  extends Fragment {

    ImageView close;
    Button apply;
    EditText editPN;
    EditText editMessage;
    ResultCreate resultCreate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_qr_message, container, false);
        close = (ImageView) view.findViewById(R.id.btnArrowBack);
        Fragment me = this;
        apply = (Button)  view.findViewById(R.id.btnGenerate);
        editPN = view.findViewById(R.id.editTextTo);
        editMessage = view.findViewById(R.id.editTextMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String txtPN = "SMSTO:" + editPN.getText().toString().trim();
//                String txtMessage = ":"+editMessage.getText().toString().trim();
                String sms = "SMSTO:" + editPN.getText().toString().trim() + ":" + editMessage.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(sms.toString(), BarcodeFormat.QR_CODE, 250,250);
                    resultCreate = new ResultCreate(matrix);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container,resultCreate).addToBackStack(null).commit();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }
}