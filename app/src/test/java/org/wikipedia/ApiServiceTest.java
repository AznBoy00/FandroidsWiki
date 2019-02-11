package org.wikipedia;


import org.junit.Test;
import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.page.PageTitle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

//TODO mock okhttp server

public class ApiServiceTest {
    @Test public void testRun() throws Throwable {
        ApiService example = ApiService.getApiServiceInstance();
        String response = example.run("https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&format=json&search=bee");
        assertEquals("[\"bee\",[\"Bee\"],[\"Bees are flying insects closely related to wasps and ants, known for their role in pollination and, in the case of the best-known bee species, the western honey bee, for producing honey and beeswax.\"],[\"https://en.wikipedia.org/wiki/Bee\"]]", response);
    }

}
