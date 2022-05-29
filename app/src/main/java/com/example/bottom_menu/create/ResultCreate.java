package com.example.bottom_menu.create;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.bottom_menu.R;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

@SuppressWarnings("ALL")
public class ResultCreate extends Fragment {
    ImageView result;
    ImageView share, save, copy, close, favourite;
    BitMatrix bitMatrix;
    private boolean CHECKED = true;


    public ResultCreate(BitMatrix bitMatrix) {
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
        close = view.findViewById(R.id.btn_ArrowBack);
        favourite = view.findViewById(R.id.btn_favourite);
        favourite.setColorFilter(-12303292);
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap bitmap = encoder.createBitmap(bitMatrix);
        result.setImageBitmap(bitmap);

        copy.setOnClickListener(view13 -> {

        });
        save.setOnClickListener(view14 -> {
            //ActivityCompat.requestPermissions(requireActivity() , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            try {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != (PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    Toast.makeText(requireActivity(), "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
                } else {

                    OutputStream fos;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ContentResolver resolver = requireContext().getContentResolver();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image" + ".jpg");
                        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        fos = resolver.openOutputStream(imageUri);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        Objects.requireNonNull(fos);
                    }
                    Toast.makeText(requireActivity(), "Save successfully", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        share.setOnClickListener(view1 -> {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
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


            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            startActivity(Intent.createChooser(intent, "share"));
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = Color.parseColor("#8624C1");
                if (CHECKED) {
                    favourite.setColorFilter(color);
                    CHECKED = false;
                } else {
                    favourite.setColorFilter(-12303292);
                    CHECKED = true;
                }
                //Toast.makeText(getActivity(), favourite.getColorFilter().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        close.setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().popBackStackImmediate());
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("value", "Permission Granted, Now you can use local drive .");
        } else {
            Log.e("value", "Permission Denied, You cannot use local drive .");
        }
    }
}
