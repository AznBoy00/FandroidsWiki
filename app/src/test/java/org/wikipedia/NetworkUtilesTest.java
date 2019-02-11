package org.wikipedia;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class NetworkUtilesTest {
    @Test
    public void testWikiBuildUrl() throws Throwable {
        URL url = new URL("https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&format=json&search=bee");
        //URL url2 = NetworkUtils.buildUrl("bee");
        System.out.println(url.toString());
        //System.out.println(url2.toString());
        //assertEquals(url , NetworkUtils.buildUrl("bee"));
    }

}
