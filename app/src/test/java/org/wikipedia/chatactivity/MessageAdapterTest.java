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

        List<Message> messages = new ArrayList<Message>() ;
        Context context = mock(Context.class);

        MessageAdapter messageAdapter = new MessageAdapter(context,1,messages);
        messageAdapter.setMaxLoadLimit(50);
        assertEquals(50,messageAdapter.getMaxLoadLimit());
    }
}