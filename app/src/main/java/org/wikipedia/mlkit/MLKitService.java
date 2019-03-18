//Reference : https://developers.google.com/ml-kit/
package org.wikipedia.mlkit;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions;
import java.util.ArrayList;
import java.util.List;

public class MLKitService {

    //For log debugging
    private static final String TAG = "MLKitService";

    // Uses Singleton pattern:
    private static final MLKitService MLKitServiceInstance = new MLKitService();
    private MLKitService(){};
    public static MLKitService getMLKitServiceInstance()
    {
        return MLKitServiceInstance;
    };

    private static ArrayList<String> detectObject = new ArrayList<>();

    public static void imageFromBitmap(Bitmap bitmap) {
        final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionOnDeviceImageLabelerOptions options =
                new FirebaseVisionOnDeviceImageLabelerOptions.Builder()
                        .setConfidenceThreshold(0.8f)
                        .build();
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                .getOnDeviceImageLabeler(options);

        labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                detectObject = processDataResult(firebaseVisionImageLabels);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,e.getMessage());
            }
        });
    }

    private static ArrayList<String> processDataResult(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
        ArrayList<String> tempList = new ArrayList<>();

        for(FirebaseVisionImageLabel label : firebaseVisionImageLabels){
            tempList.add(label.getText());
        }
        return tempList;
    }

    public static ArrayList<String> getDetectObject() {
        return detectObject;
    }
}
