package org.wikipedia.chatactivity;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class MessageTest {

    @Mock
    Message message = new Message();

    @Test
    public void setTextTest() {
        message.setText("some text");
        assertEquals(message.getText(),"some text");
    }

    @Test
    public void setNameTest() {
        message.setName("Kevin Lee");
        assertEquals(message.getName(),"Kevin Lee");
    }

    @Test
    public void setPhotoUrl() {
        message.setPhotoUrl("images/group_chat/");
        assertEquals(message.getPhotoUrl(),"images/group_chat/");
    }

    @Test
    public void setUID() {
        message.setUID("8qGjX6aaKcZ1ZmpP7qjnFrD4ewT8");
        assertEquals(message.getUID(),"8qGjX6aaKcZ1ZmpP7qjnFrD4ewT8");
    }
}