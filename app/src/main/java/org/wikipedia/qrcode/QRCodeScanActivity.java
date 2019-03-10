//Reference: https://code.tutsplus.com/tutorials/reading-qr-codes-using-the-mobile-vision-api--cms-24680

package org.wikipedia.qrcode;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.wikipedia.R;

import java.io.IOException;

public class QRCodeScanActivity extends AppCompatActivity {

    private SurfaceView camView;
    private CameraSource camSource;
    private TextView bcText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_cam_scan);

        camView = findViewById(R.id.qr_code_cam_scan);
        bcText = findViewById(R.id.qr_code_cam_text);

        BarcodeDetector bcDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        camSource = new CameraSource.Builder(this, bcDetector).build();

        camView.getHolder().addCallback(new SurfaceHolder.Callback() {
            //assumed permission granted
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {

                    camSource.start(camView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camSource.stop();
            }
        });

        bcDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    bcText.post(new Runnable() {
                        @Override
                        public void run() {
                            bcText.setText(barcodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
    }
}