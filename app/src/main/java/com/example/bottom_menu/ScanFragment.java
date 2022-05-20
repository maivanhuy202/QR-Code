package com.example.bottom_menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanFragment extends Fragment {

    public static final int GET_FROM_GALLERY = 3;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ExecutorService cameraExecutor;
    public PreviewView previewView;
    private MyImageAnalyzer analyzer;
    private ImageView btnFlashOn;
    private ImageView btnFlashOff;
    private boolean lightOn = false;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        previewView = view.findViewById(R.id.previewView);

        btnFlashOn = view.findViewById(R.id.btn_flash_on);
        btnFlashOn.setClickable(true);
        btnFlashOn.setVisibility(View.VISIBLE);

        btnFlashOff = view.findViewById(R.id.btn_flash_off);
        btnFlashOff.setClickable(false);
        btnFlashOff.setVisibility(View.GONE);

        ImageView btnPhotoGallery = view.findViewById(R.id.btn_gallery);
        btnPhotoGallery.setOnClickListener(view1 -> startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY));

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity());

        //noinspection deprecation
        analyzer = new MyImageAnalyzer(getFragmentManager());

        cameraProviderFuture.addListener(() -> {
            try {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != (PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, 101);
                }else {
                    ProcessCameraProvider processCameraProvider = cameraProviderFuture.get();
                    bindPreview(processCameraProvider);
                }

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireActivity()));

        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101 && grantResults.length >0) {
            ProcessCameraProvider processCameraProvider = null;
            try {
                processCameraProvider = cameraProviderFuture.get();
            } catch ( InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            bindPreview(Objects.requireNonNull(processCameraProvider));
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri selectedImage = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver() , selectedImage);
                InputImage image = InputImage.fromBitmap(bitmap, 0);
                BarcodeScannerOptions options =
                        new BarcodeScannerOptions.Builder()
                                .setBarcodeFormats(
                                        Barcode.FORMAT_QR_CODE,
                                        Barcode.FORMAT_AZTEC,
                                        Barcode.FORMAT_ALL_FORMATS)
                                .build();
                BarcodeScanner scanner = BarcodeScanning.getClient(options);
                scanner.process(image)
                        .addOnSuccessListener(this.analyzer::readerBarcodeData)
                        .addOnFailureListener(Throwable::printStackTrace);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindPreview(ProcessCameraProvider processCameraProvider) {

        Preview preview = new Preview.Builder()
                .build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        processCameraProvider.unbindAll();

        ImageCapture imageCapture = new ImageCapture
                .Builder()
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(cameraExecutor,analyzer);

        Camera camera = processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);

        btnFlashOn.setOnClickListener(view1 -> {
            if ( camera.getCameraInfo().hasFlashUnit() ) {
                camera.getCameraControl().enableTorch(!lightOn);
                lightOn = !lightOn;
                btnFlashOn.setVisibility(View.GONE);
                btnFlashOn.setClickable(false);
                btnFlashOff.setVisibility(View.VISIBLE);
                btnFlashOff.setClickable(true);
            } else {
                Toast.makeText(getActivity(), "Your phone doesn't have a flash light", Toast.LENGTH_SHORT).show();
            }

        });
        btnFlashOff.setOnClickListener(view -> {
            if ( camera.getCameraInfo().hasFlashUnit() ) {
                camera.getCameraControl().enableTorch(!lightOn);
                lightOn = !lightOn;
                btnFlashOn.setVisibility(View.VISIBLE);
                btnFlashOn.setClickable(true);
                btnFlashOff.setVisibility(View.GONE);
                btnFlashOff.setClickable(false);
            } else {
                Toast.makeText(getActivity(), "Your phone doesn't have a flash light", Toast.LENGTH_SHORT).show();
            }
        });

        previewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);
    }

    public static class MyImageAnalyzer implements ImageAnalysis.Analyzer {
        private final FragmentManager fragmentManager;
        private final resultUrl resultUrl;
        private final resultText resultText;
        private final resultMessage resultMessage;
        private final resultPhoneNumber resultPhoneNumber;
        private final resultEmail resultEmail;
        private final resultContact resultContact;
        private final resultDefault resultDefault;
        private final resultWifi resultWifi;

        public MyImageAnalyzer(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            resultUrl = new resultUrl();
            resultText = new resultText();
            resultMessage = new resultMessage();
            resultPhoneNumber = new resultPhoneNumber();
            resultEmail = new resultEmail();
            resultContact = new  resultContact();
            resultDefault = new resultDefault();
            resultWifi = new resultWifi();
        }

        @Override
        public void analyze(@NonNull ImageProxy image) {
            scanBarcodes(image);
        }

        private void scanBarcodes(ImageProxy image) {
            @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();
            assert image1 != null;
            InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());
            BarcodeScannerOptions options =
                    new BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                    Barcode.FORMAT_QR_CODE,
                                    Barcode.FORMAT_AZTEC,
                                    Barcode.FORMAT_ALL_FORMATS)
                            .build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);
            // Task failed with an exception
            // ...
            // Task completed successfully
            // [START_EXCLUDE]
            // [START get_barcodes]
            // [END get_barcodes]
            // [END_EXCLUDE]
            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(this::readerBarcodeData)
                    .addOnFailureListener(Throwable::printStackTrace)
                    .addOnCompleteListener(task -> image.close());
        }

        private void readerBarcodeData(List<Barcode> barcodes) {
            for (Barcode barcode : barcodes) {
                String rawValue = barcode.getRawValue();
                int valueType = barcode.getValueType();
                // See API reference for complete list of supported types
                switch (valueType) {
                    case Barcode.TYPE_WIFI:
                        String ssid = Objects.requireNonNull(barcode.getWifi()).getSsid();
                        String password = barcode.getWifi().getPassword();
                        int encryptionType = barcode.getWifi().getEncryptionType();
                        if (!resultWifi.isAdded()){
                            resultWifi.show(fragmentManager,"QR WIFI SCANNED");
                        }
                        resultWifi.fetch(ssid,password,encryptionType);
                        break;
                    case Barcode.TYPE_URL:
                        if (!resultUrl.isAdded()) {
                            resultUrl.show(fragmentManager, "URL QR SCANNED");
                        }
                        resultUrl.fetchUrl(Objects.requireNonNull(barcode.getUrl()).getUrl());
                        break;
                    case Barcode.TYPE_CALENDAR_EVENT:
                        break;
                    case Barcode.TYPE_EMAIL:
                        String address = Objects.requireNonNull(barcode.getEmail()).getAddress();
                        String subject= barcode.getEmail().getSubject();
                        String body = barcode.getEmail().getBody();
                        if (!resultEmail.isAdded()) {
                            resultEmail.show(fragmentManager, "QR EMAIL SCANNED");
                        }
                        resultEmail.fetch(address, subject, body);
                        break;
                    case Barcode.TYPE_TEXT:
                        String text = barcode.getDisplayValue();
                        if (!resultText.isAdded()) {
                            resultText.show(fragmentManager,"QR TEXT SCANNED");
                        }
                        resultText.fetchText(text);
                        break;
                    case Barcode.TYPE_SMS:
                        String phoneNumber = Objects.requireNonNull(barcode.getSms()).getPhoneNumber();
                        String message = barcode.getSms().getMessage();
                        if(!resultMessage.isAdded()) {
                            resultMessage.show(fragmentManager, "QR MESSAGE SCANNED");
                        }
                        resultMessage.fetch(phoneNumber, message);
                        break;
                    case Barcode.TYPE_PHONE:
                        String phoneNum = Objects.requireNonNull(barcode.getPhone()).getNumber();
                        if (!resultPhoneNumber.isAdded()){
                            resultPhoneNumber.show(fragmentManager,"QR PHONE SCANNED");
                        }
                        resultPhoneNumber.fetch(phoneNum);
                        break;
                    case Barcode.TYPE_CONTACT_INFO:
                        String name = Objects.requireNonNull(Objects.requireNonNull(barcode.getContactInfo()).getName()).getFormattedName();
                        String address1 = Arrays.toString(barcode.getContactInfo().getAddresses().get(0).getAddressLines()) ;
                        String company = Objects.requireNonNull(barcode.getContactInfo().getOrganization());
                        String phone = barcode.getContactInfo().getPhones().get(0).getNumber();
                        String email = barcode.getContactInfo().getEmails().get(0).getAddress();
                        if (!resultContact.isAdded()){
                            resultContact.show(fragmentManager,"QR CONTACT SCANNED");
                        }
                        resultContact.fetch(name, address1,company, phone,email);
                    default:
                        if (!resultDefault.isAdded()) {
                            resultDefault.show(fragmentManager, "QR SCANNED");
                        }
                        resultDefault.fetch(rawValue);
                }
            }
        }
    }


}