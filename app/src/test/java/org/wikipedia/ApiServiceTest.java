package org.wikipedia;

import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;



public class ApiServiceTest {

    // Using MockWebsserver to mock server and test run function
    @Test
    public void test() throws Throwable {
        // Create a MockWebServer
        MockWebServer server = new MockWebServer();

        // Schedule some responses.
        server.enqueue(new MockResponse().setBody("hello, world!"));

        // Start the server.
        server.start();

        // Ask the server for its URL.
        HttpUrl baseUrl = server.url("key");
        System.out.println(baseUrl.toString());

        String request = ApiService.getApiServiceInstance().run(baseUrl.toString());
        assertEquals("hello, world!", request);

        server.shutdown();
    }

    // Test first time with real server
    @Test public void testRunFunctionOne() throws Throwable {
        ApiService example = ApiService.getApiServiceInstance();
        String response = example.run("https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&format=json&search=bee");
        assertEquals("[\"bee\",[\"Bee\"],[\"Bees are flying insects closely related to wasps and ants, known for their role in pollination and, in the case of the best-known bee species, the western honey bee, for producing honey and beeswax.\"],[\"https://en.wikipedia.org/wiki/Bee\"]]", response);
    }


    // Test second time  with real server
    @Test public void testRunFunctionTwo() throws Throwable {
        ApiService example = ApiService.getApiServiceInstance();
        String response = example.run("https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&format=json&search=steve_jobs");
        assertEquals("[\"steve_jobs\",[\"Steve Jobs\"],[\"Steven Paul Jobs (; February 24, 1955 \\u2013 October 5, 2011) was an American business magnate and investor.\"],[\"https://en.wikipedia.org/wiki/Steve_Jobs\"]]", response);
    }
}

