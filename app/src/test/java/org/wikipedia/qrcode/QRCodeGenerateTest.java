package org.wikipedia.qrcode;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.wikipedia.page.PageBackStackItem;
import org.wikipedia.page.PageTitle;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.failNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class QRCodeGenerateTest {
    private static final String TAG = "QRCodeGenerateTest";

    PageBackStackItem lastTabMock;
    PageTitle titleMock;
    AppCompatActivity appCompatActivity;
    Bitmap qrImage;
    private String url, fakeURL;

    @Before public void SetUp() {
        //mock
        appCompatActivity = mock(AppCompatActivity.class);
        lastTabMock = mock(PageBackStackItem.class);
        titleMock = mock(PageTitle.class);
        qrImage = mock(Bitmap.class);

        url = "https://en.wikipedia.org/wiki/Apple_Inc.";
        fakeURL = "https://en.wikipedia.org/wiki/Apple_Inc._";
    }

    @Test public void generateQRCodeTest() {
        //stub
        when(lastTabMock.getTitle()).thenReturn(titleMock);
        when(titleMock.getCanonicalUri()).thenReturn(url);

        //Spy
        QRCodeGenerateActivity qrCodeGenerateActivitySpy = Mockito.spy(QRCodeGenerateActivity.class);
        qrCodeGenerateActivitySpy.generateImage(lastTabMock.getTitle().getCanonicalUri());
        System.out.println("[" + TAG + "]" + "Mock URL: " + titleMock.getCanonicalUri() + " = String URL: " + url);

        //Verify
        verify(qrCodeGenerateActivitySpy).generateImage(lastTabMock.getTitle().getCanonicalUri());

        //Assert (check for stubs != null AND urls are identical to expected result)
        assertNotNull(lastTabMock.getTitle());
        assertNotNull(titleMock.getCanonicalUri());
        assertEquals(url, lastTabMock.getTitle().getCanonicalUri());
    }

    //Fail case test
    @Test public void failToGenerateQRCodeTest() {
        //stub
        when(lastTabMock.getTitle()).thenReturn(titleMock);
        when(titleMock.getCanonicalUri()).thenReturn(url);

        //Spy
        QRCodeGenerateActivity qrCodeGenerateActivitySpy = Mockito.spy(QRCodeGenerateActivity.class);
        qrCodeGenerateActivitySpy.generateImage(lastTabMock.getTitle().getCanonicalUri());
        System.out.println("[" + TAG + "]" + "Mock URL: " + titleMock.getCanonicalUri() + " != String URL: " + fakeURL);

        //Verify
        verify(qrCodeGenerateActivitySpy).generateImage(lastTabMock.getTitle().getCanonicalUri());

        //Assert Not the same.
        assertNotSame(fakeURL, lastTabMock.getTitle().getCanonicalUri());
    }
}