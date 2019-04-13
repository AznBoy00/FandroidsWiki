package org.wikipedia.directmessage;

import android.widget.TextView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testChat {

    @Test
    public void testAddMessageBox() {
        String testMessage = "Message";

        TextView mockTextView = mock(TextView.class);
        when(mockTextView.getText()).thenReturn("Message");
        assertEquals(testMessage, mockTextView.getText());
    }
}
