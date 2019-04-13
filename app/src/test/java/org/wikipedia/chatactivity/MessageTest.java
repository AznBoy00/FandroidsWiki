package org.wikipedia.chatactivity;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class MessageTest {

    Message message = new Message();

    @Test
    public void setTextTestEquals() {
        message.setText("some text");
        assertEquals(message.getText(),"some text");
    }   @Test
    public void setTextTestNotEquals() {
        message.setText("some text");
        assertNotEquals(message.getText(),"some");
    }

    @Test
    public void setNameTestEuqals() {
        message.setName("Kevin Lee");
        assertEquals(message.getName(),"Kevin Lee");
    }
    @Test
    public void setNameTestNotEuqals() {
        message.setName("Kevin Lee");
        assertNotEquals(message.getName(),"Kevin");
    }

    @Test
    public void setPhotoUrlEquals() {
        message.setPhotoUrl("images/group_chat/");
        assertEquals(message.getPhotoUrl(),"images/group_chat/");
    }

    @Test
    public void setPhotoUrlNotEquals() {
        message.setPhotoUrl("images/group_chat/");
        assertNotEquals(message.getPhotoUrl(),"images/group_cha/");
    }

    @Test
    public void setUIDEquals() {
        message.setUID("8qGjX6aaKcZ1ZmpP7qjnFrD4ewT8");
        assertEquals(message.getUID(),"8qGjX6aaKcZ1ZmpP7qjnFrD4ewT8");
    }

    @Test
    public void setUIDNotEquals() {
        message.setUID("8qGjX6aaKcZ1ZmpP7qjnFrD4ewT8");
        assertNotEquals(message.getUID(),"8qGjX6aaKcZ1ZmpP7qjnFrD4ewT5");
    }
}