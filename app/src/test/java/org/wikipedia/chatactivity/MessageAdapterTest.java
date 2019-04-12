package org.wikipedia.chatactivity;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageAdapterTest {


    @Test
    public void setMaxLoadLimitTest() {
//        Message message1 = mock(Message.class);
//        Message message2 = mock(Message.class);
        List<Message> messages = new ArrayList<Message>() ;
        Context context = mock(Context.class);

        MessageAdapter messageAdapter = new MessageAdapter(context,1,messages);
        messageAdapter.setMaxLoadLimit(50);
        assertEquals(50,messageAdapter.getMaxLoadLimit());
    }

    @Test
    public void getCurrentLastPositionStringTest() {
        List<Message> messages = new ArrayList<Message>() ;
//        Message message1 = mock(Message.class);
        Message message1 = new Message();
        messages.add(message1);
        Context context = mock(Context.class);

        MessageAdapter messageAdapter = new MessageAdapter(context,1,messages);
//
//        //when if statement is true
//        when(messageAdapter.getTotalPosition()).thenReturn(36);
//        messageAdapter.setMaxLoadLimit(50);
//
//        String count_less_then_max = messageAdapter.getCurrentLastPositionString(14);
//
//        assertEquals(count_less_then_max,"at the last position " + 16);
//        verify(messageAdapter,times(2)).getTotalPosition();
//        verify(messageAdapter, times(1)).getMaxLoadLimit();

        //when if statement is false



    }
}