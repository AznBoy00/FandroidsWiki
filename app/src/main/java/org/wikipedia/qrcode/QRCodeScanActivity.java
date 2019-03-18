//Reference: https://www.journaldev.com/18198/qr-code-barcode-scanner-android

package org.wikipedia.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import org.wikipedia.R;

public class QRCodeScanActivity extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textToShow;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    Button btn_qr_reader_scan;
    String intentData = "";
    String detectedStr;
    private final String logE = "Scanner";
    private final String s = "wikipedia.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_cam_scan);
        detectedStr="";
        initViews();
    }

    private void initViews() {
        textToShow = findViewById(R.id.txtBarcodeValue);
        cameraView = findViewById(R.id.surfaceView);
        btn_qr_reader_scan = findViewById(R.id.btn_qr_reader_scan);


        btn_qr_reader_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_qr_reader_scan.getText() == "Go to Wikipedia page!") {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                    btn_qr_reader_scan.setText("Scanning...");
                    textToShow.setText("No Barcode Detected");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Can't find any QR code...",Toast.LENGTH_SHORT).show();
                }
            }
    });
}
    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector) .setRequestedPreviewSize(1920, 1080).setAutoFocusEnabled(true).build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QRCodeScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(QRCodeScanActivity.this, new
                                String[]{Manifest.permission.CAMERA}, 201);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    textToShow.post(new Runnable() {

                        @Override
                        public void run() {
                            detectedStr = barcodes.valueAt(0).displayValue;
                            //Log.e(logE,"Detected string: " + detectedStr);
                            if(isWikiLink(detectedStr)) {
                                btn_qr_reader_scan.setText("Go to Wikipedia page!");
                                intentData = barcodes.valueAt(0).displayValue;
                                textToShow.setText(intentData);
                            }else
                            {
                                btn_qr_reader_scan.setText("This is not a Wikipedia QR Code!");
                                intentData = "";
                                textToShow.setText(intentData);
                            }

                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    public boolean isWikiLink(String str){

        boolean isWiki = (str.indexOf(s) == -1)?false:true;
        return isWiki;
    }


}
