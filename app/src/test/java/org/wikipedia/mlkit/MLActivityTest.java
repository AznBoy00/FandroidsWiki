package org.wikipedia.mlkit;

import com.wonderkiln.camerakit.CameraView;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

public class MLActivityTest {

    MLActivity ml = new MLActivity();

    @Test
    public void testIsNotDetected(){
        String [] testArray = new String[0];
        assertFalse(ml.isDetected(testArray));
    }

    @Test
    public void testIsDetected(){
        String [] testArray = new String[5];
        assertTrue(ml.isDetected(testArray));
    }

    @Test
    public void testCovertArrayListToArrayValue(){
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> listSpy = spy(arrayList);
        when(listSpy.size()).thenReturn(1);
        listSpy.add("Dog");
        String[] testArray = ml.covertArrayListToArray(listSpy);
        assertEquals("Dog",testArray[0]);
    }

    @Test
    public void testCovertArrayListToArrayReturnTypeTrue(){
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> listSpy = spy(arrayList);
        when(listSpy.size()).thenReturn(1);
        listSpy.add("Dog");
        assertTrue(ml.covertArrayListToArray(listSpy).getClass()==String[].class);
    }
}
