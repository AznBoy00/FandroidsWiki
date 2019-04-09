package org.wikipedia.directmessage;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Iterator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class testUserList {

    Context context;
    Intent intent;
    String request;
    JSONObject jsonObject;
    Iterator iterator;
    String key;
    ArrayList<String> mockArrayList;

    @Before
    public void SetUp() {
        context = PowerMockito.mock(Context.class);
        intent = mock(Intent.class);
        iterator = mock(Iterator.class);
        request = "{\"John Smith\":{\"email\":\"johnsmith1@mail.com\",\"username\":\"John Smith\"},\"Jane Doe\":{\"email\":\"janedoe@mail.com\",\"username\":\"Jane Doe\"}}";


    }

    @Test
    public void testOnSuccess() throws Exception{
        jsonObject = mock(JSONObject.class);
        PowerMockito.whenNew(JSONObject.class).withArguments(request).thenReturn(jsonObject);
        iterator = jsonObject.keys();
        mockArrayList = mock(ArrayList.class);

        while(iterator.hasNext()){
            key = iterator.next().toString();
            assertNotEquals(iterator.toString(), iterator.next().toString());
            mockArrayList.add(key);
            verify(mockArrayList).add(key);
        }

    }


}
