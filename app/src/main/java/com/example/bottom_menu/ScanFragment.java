package com.example.bottom_menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.graphics.Point;
import android.graphics.Rect;

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

import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanFragment extends Fragment {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ExecutorService cameraExecutor;
    public PreviewView previewView;
    private MyImageAnalyzer analyzer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        previewView = view.findViewById(R.id.previewView);

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity());

        analyzer = new MyImageAnalyzer(getFragmentManager());
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != (PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, 101);
                    }else {
                        ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                        bindPreview(processCameraProvider);
                    }

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(requireActivity()));
//        cameraProviderFuture.addListener(() -> {
//            try {
//                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                bindPreview(cameraProvider);
//            } catch (ExecutionException | InterruptedException e) {
//                // No errors need to be handled for this Future.
//                // This should never be reached.
//                e.printStackTrace();
//            }
//        }, ContextCompat.getMainExecutor(requireActivity()));
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101 && grantResults.length >0) {
            ProcessCameraProvider processCameraProvider = null;
            try {
                processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bindPreview(processCameraProvider);
        }
    }

    private void bindPreview(ProcessCameraProvider processCameraProvider) {

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        processCameraProvider.unbindAll();
        ImageCapture imageCapture = new ImageCapture.Builder().build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        imageAnalysis.setAnalyzer(cameraExecutor,analyzer);
        processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);
        previewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);

//        Preview preview = new Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3).
//                setTargetRotation(previewView.getDisplay().getRotation())
//                .build();
//
//
//
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//
//
//
//        cameraProvider.bindToLifecycle(this, cameraSelector,preview);
    }

    public class MyImageAnalyzer implements ImageAnalysis.Analyzer {
        private FragmentManager fragmentManager;
        private bottom_dialog bd;

        public MyImageAnalyzer(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            bd = new bottom_dialog();
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
                                    Barcode.FORMAT_AZTEC)
                            .build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);
            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            // Task completed successfully
                            // [START_EXCLUDE]
                            // [START get_barcodes]
                            readerBarcodeData(barcodes);
                            // [END get_barcodes]
                            // [END_EXCLUDE]
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            e.printStackTrace();
                            // ...
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Barcode>> task) {
                            image.close();
                        }
                    });
        }

        private void readerBarcodeData(List<Barcode> barcodes) {
            for (Barcode barcode : barcodes) {
                Rect bounds = barcode.getBoundingBox();
                Point[] corners = barcode.getCornerPoints();

                String rawValue = barcode.getRawValue();

                int valueType = barcode.getValueType();
                // See API reference for complete list of supported types
                switch (valueType) {
                    case Barcode.TYPE_WIFI:
                        String ssid = barcode.getWifi().getSsid();
                        String password = barcode.getWifi().getPassword();
                        int type = barcode.getWifi().getEncryptionType();
                        break;
                    case Barcode.TYPE_URL:
                        if (!bd.isAdded()) {
                            bd.show(fragmentManager, "");
                        }
                        bd.fetchUrl(barcode.getUrl().getUrl());
                        String title = barcode.getUrl().getTitle();
                        String url = barcode.getUrl().getUrl();
                        break;
                }
            }
        }
    }

/*    private void scanBarcodes(InputImage image) {
        // [START set_detector_options]
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC)
                        .build();
        // [END set_detector_options]

        // [START get_detector]
        BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        // BarcodeScanner scanner = BarcodeScanning.getClient(options);
        // [END get_detector]

        // [START run_detector]
        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        // Task completed successfully
                        // [START_EXCLUDE]
                        // [START get_barcodes]
                        for (Barcode barcode: barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String rawValue = barcode.getRawValue();

                            int valueType = barcode.getValueType();
                            // See API reference for complete list of supported types
                            switch (valueType) {
                                case Barcode.TYPE_WIFI:
                                    String ssid = barcode.getWifi().getSsid();
                                    String password = barcode.getWifi().getPassword();
                                    int type = barcode.getWifi().getEncryptionType();
                                    break;
                                case Barcode.TYPE_URL:
                                    String title = barcode.getUrl().getTitle();
                                    String url = barcode.getUrl().getUrl();
                                    break;
                            }
                        }
                        // [END get_barcodes]
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        e.printStackTrace();
                        // ...
                    }
                });
        // [END run_detector]
    }
*/


}