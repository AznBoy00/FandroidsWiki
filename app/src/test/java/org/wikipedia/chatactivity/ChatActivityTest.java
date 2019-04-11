package org.wikipedia.chatactivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChatActivityTest {

    @Mock
    private DatabaseReference myDBRef = mock(DatabaseReference.class);
    private int maxLoadLimit = 100; //default 100
    private Query maxLoadLimitQuery =mock(Query.class);

    @Before


    @Test
    public void setMaxLoadLimitTest() {
        Query returned = mock(Query.class);
//        Query returned2 = myDBRef.orderByKey();
        ChatActivity chatActivity = new ChatActivity();
        when(myDBRef.orderByKey()).thenReturn(returned);
        when(myDBRef.orderByKey().limitToLast(maxLoadLimit)).thenReturn(returned);
//        chatActivity.setMaxLoadLimit(10);
//        assertEquals(maxLoadLimit, 10);
//        assertEquals(maxLoadLimitQuery,returned);
//        verify(myDBRef).orderByKey().limitToLast(maxLoadLimit);
    }

    @Test
    public void getMaxLoadLimitTest() {
        ChatActivity chatActivity = new ChatActivity();
        assertEquals(chatActivity.getMaxLoadLimit(),100);
    }
}