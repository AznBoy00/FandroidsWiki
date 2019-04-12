package org.wikipedia.directmessage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class testUserList {


    @Before
    public void SetUp() {}

    @Test
    public void testOnSuccess() throws Exception{
        Iterator<Integer> mockIterator = mock(Iterator.class);
        when(mockIterator.hasNext()).thenReturn(true, true, true, false);
        Integer[] keys = new Integer [] {1, 2, 3, 4};
        when(mockIterator.next()).thenReturn(keys[0], keys[1], keys[2], keys[3]);
        ArrayList<Integer> mockArrayList = mock(ArrayList.class);
        int index = 0;
        while(mockIterator.hasNext()) {
            int keyValue = keys[index];
            int iteratorValue = mockIterator.next();
            assertEquals(keyValue, iteratorValue);
            if(keys[0] != iteratorValue){
                mockArrayList.add(iteratorValue);
                assertTrue(keys[0] != iteratorValue);
            }
            index++;
        }
    }


}
