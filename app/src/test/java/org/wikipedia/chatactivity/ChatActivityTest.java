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
    private DatabaseReference myDBRef = mock(DatabaseReference.class);
    private int maxLoadLimit = 100; //default 100
    ChatActivity chatActivity =new ChatActivity();

    @Test
    public void setMaxLoadLimitTestEquals() {
      chatActivity.setMaxLoadLimit(maxLoadLimit);
        assertEquals(chatActivity.getMaxLoadLimit(),maxLoadLimit);
    }
    @Test
    public void setMaxLoadLimitTestNotEquals() {
        chatActivity.setMaxLoadLimit(maxLoadLimit);
        assertNotEquals(chatActivity.getMaxLoadLimit(),1000);
    }

    @Test
    public void getMaxLoadLimitTestEquals() {
        ChatActivity chatActivity = new ChatActivity();
        assertEquals(chatActivity.getMaxLoadLimit(),100);
    }

    @Test
    public void getMaxLoadLimitTestNotEquals() {
        ChatActivity chatActivity = new ChatActivity();
        assertNotEquals(chatActivity.getMaxLoadLimit(),1000);
    }
}