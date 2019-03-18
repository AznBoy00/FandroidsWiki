package org.wikipedia.mlkit;

import android.graphics.Bitmap;

import org.junit.Test;

import static org.mockito.Mockito.mock;

public class MLKitServiceTest {

    MLKitService mlKitService = MLKitService.getMLKitServiceInstance();
    @Test
    public void testimageFormBitmap(){

        Bitmap bitmap = mock(Bitmap.class);
        MLKitService.imageFromBitmap(bitmap);

    }




}
