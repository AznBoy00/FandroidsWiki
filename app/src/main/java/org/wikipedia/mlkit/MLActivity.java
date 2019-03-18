package org.wikipedia.mlkit;

import android.app.ProgressDialog;
import android.content.Intent;
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
import org.wikipedia.main.MainActivity;

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
        cameraView.stop();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml);
        cameraView = (CameraView)findViewById(R.id.camera);
        btnDetect = findViewById(R.id.btn_detect);
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Log.e(TAG,"111");
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
                cameraView.stop();
                MLKitService.imageFromBitmap(bitmap);
                labelingObject = MLKitService.getDetectObject();
                Log.e(TAG,"222");
                for (String index : labelingObject){
                    Log.e(TAG,"3333");
                    Log.e(TAG, index);
                }

                // TODO add to searchResultsFromGoogleVisionActivity
                if(labelingObject.size()>0)
                {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                }

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnDetect = findViewById(R.id.btn_detect);
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.start();
                //get picture
                cameraView.captureImage();
                Log.e(TAG,"abc");
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
