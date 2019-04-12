package org.wikipedia.chatactivity;

import android.content.Context;

import org.junit.Test;


import java.util.ArrayList;

import java.util.List;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


public class MessageAdapterTest {
    List<Message> messages = mock(ArrayList.class);
    Context context = mock(Context.class);

    @Test
    public void setMaxLoadLimitTestEquals() {
        MessageAdapter messageAdapter = new MessageAdapter(context,1,messages);
        messageAdapter.setMaxLoadLimit(50);
        assertEquals(50,messageAdapter.getMaxLoadLimit());
    }

    @Test
    public void setMaxLoadLimitTestNotEquals() {
        MessageAdapter messageAdapter = new MessageAdapter(context,1,messages);
        messageAdapter.setMaxLoadLimit(50);
        assertNotEquals(20,messageAdapter.getMaxLoadLimit());
    }

    @Test
    public void getCurrentLastPositionStringTestEquals(){
        MessageAdapter messageAdapter = new MessageAdapter(context,1,messages);
        assertEquals("at the last position -1",messageAdapter.getCurrentLastPositionString(1));
    }

    @Test
    public void getCurrentLastPositionStringTestNotEquals(){
        MessageAdapter messageAdapter = new MessageAdapter(context,1,messages);
        assertNotEquals(""+ "at the last position " + 1,messageAdapter.getCurrentLastPositionString(1));
    }
}