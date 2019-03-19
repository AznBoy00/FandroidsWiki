package org.wikipedia.qrcode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

public class QRCodeScanFilterTest {

    final String wikiLink = "https://en.wikipedia.org/wiki/Apple_Inc";
    final String non_wikiLink = "http://some.example.com/r/tSrL07HEJuxZrfWw93_n";
    @Before
    public void setUp(){

    }


    @Test
    public void isWikiLinkTrueTest(){
        QRCodeScanActivity qrCodeScan = Mockito.spy(QRCodeScanActivity.class);
        boolean bool = qrCodeScan.isWikiLink(wikiLink);
        assertTrue(bool);
    }

    @Test
    public void isWikiLinkFalseTest(){
        QRCodeScanActivity qrCodeScan = Mockito.spy(QRCodeScanActivity.class);
        boolean bool = qrCodeScan.isWikiLink(non_wikiLink);
        assertTrue(!bool);
    }
}