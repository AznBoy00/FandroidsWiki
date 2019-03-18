package org.wikipedia.mlkit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import org.wikipedia.R;

import java.util.ArrayList;

public class MLActivity extends AppCompatActivity {
    //For log debugging
    private static final String TAG = "MLActivity";

    //MLKitService
    MLKitService mlKitService = MLKitService.getMLKitServiceInstance();

    // Using camerakit. Reference:https://github.com/CameraKit/camerakit-android
    CameraView cameraView;
    // Button For detection
    Button btnDetect;

    //String list to get all labeling
    private ArrayList<String> labelingObject = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraView.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml);
        cameraView = (CameraView)findViewById(R.id.camera);
        cameraView.start();
        btnDetect = findViewById(R.id.btn_detect);
        //cameraView.start();
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Log.d(TAG,"abc");
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
                cameraView.stop();
                MLKitService.imageFromBitmap(bitmap);
                labelingObject = MLKitService.getDetectObject();
                Log.d(TAG,"abc");
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnDetect = findViewById(R.id.btn_detect);
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get picture
                cameraView.captureImage();
                Log.d(TAG,"abc");
            }
        });
    }

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }



}
