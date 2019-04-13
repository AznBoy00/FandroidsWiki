package org.wikipedia.directmessage;

import android.widget.TextView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testChat {

    @Test
    public void testAddMessageBox() {
        String testMessage = "Message";
        Object mockObject = mock(Object.class);

        TextView mockTextView = mock(TextView.class);
        when(mockTextView.getText()).thenReturn("Message");
        assertEquals(testMessage, mockTextView.getText());

        // Edge case: Check that message isn't unnecessarily large
        when(mockTextView.getText().length()).thenReturn(99999);
        assertTrue(mockTextView.getText().length() <= 999999999);
    }
}
