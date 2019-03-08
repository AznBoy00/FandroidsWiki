package org.wikipedia.qrcode;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;
import org.mockito.Mockito;
import org.wikipedia.page.PageBackStackItem;
import org.wikipedia.page.PageTitle;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class QRCodeGenerateTest {
    private static final String TAG = "QRCodeGenerateTest";

    PageBackStackItem lastTabMock;
    PageTitle titleMock;
    AppCompatActivity appCompatActivity;
    Bitmap qrImage;
    private String url;

    @Test public void generateQRCodeTest() throws Throwable {
        //mock
        appCompatActivity = mock(AppCompatActivity.class);
        lastTabMock = mock(PageBackStackItem.class);
        titleMock = mock(PageTitle.class);
        qrImage = mock(Bitmap.class);

        url = "https://en.wikipedia.org/wiki/Apple_Inc.";

        //stub
        when(lastTabMock.getTitle()).thenReturn(titleMock);
        when(titleMock.getCanonicalUri()).thenReturn(url);

        //Spy
        QRCodeGenerateActivity qrCodeGenerateActivitySpy = Mockito.spy(QRCodeGenerateActivity.class);
        qrCodeGenerateActivitySpy.generateImage(lastTabMock.getTitle().getCanonicalUri());
        System.out.println("[" + TAG + "]" + "Mock URL: " + titleMock.getCanonicalUri() + " = String URL: " + url);

        //Assert
        assertEquals(url, lastTabMock.getTitle().getCanonicalUri());

        //Verify
        verify(qrCodeGenerateActivitySpy).generateImage(lastTabMock.getTitle().getCanonicalUri());
    }
}