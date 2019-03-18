package org.wikipedia.googleVision;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.NetworkUtils;
import org.wikipedia.WikiUrlRetriever;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SearchResultListTest {

    private String[] listOfKeywords;
    SearchResultsFromGoogleVisionActivity searchResults;
    String[] keywords;

    @Before
    public void SetUp() {
        NetworkUtils network = mock(NetworkUtils.class);
        WikiUrlRetriever urlRetriever = mock(WikiUrlRetriever.class);
        searchResults = mock(SearchResultsFromGoogleVisionActivity.class);
    }

    /** Tests for the display of keywords generated from Google Vision **/
    @Test
    public void testDisplayGoogleVisionResults() {
        List<String> listOfUrls = searchResults.displayGoogleVisionResults(new String[]{"honda", "toyota"});
        assertTrue((listOfUrls.toString()).equals("[https://en.wikipedia.org/wiki/Honda, https://en.wikipedia.org/wiki/Honda_Civic, https://en.wikipedia.org/wiki/Honda_Accord, https://en.wikipedia.org/wiki/Honda_Civic_Type_R, https://en.wikipedia.org/wiki/Honda_Fit, https://en.wikipedia.org/wiki/Honda_City, https://en.wikipedia.org/wiki/Honda_Gold_Wing, https://en.wikipedia.org/wiki/Honda_in_Formula_One, https://en.wikipedia.org/wiki/Honda_Civic_(sixth_generation), https://en.wikipedia.org/wiki/Honda_Insight, https://en.wikipedia.org/wiki/Toyota, https://en.wikipedia.org/wiki/Toyota_Hilux, https://en.wikipedia.org/wiki/Toyota_Supra, https://en.wikipedia.org/wiki/Toyota_MR2, https://en.wikipedia.org/wiki/Toyota_RAV4, https://en.wikipedia.org/wiki/Toyota_Celica, https://en.wikipedia.org/wiki/Toyota_Corolla, https://en.wikipedia.org/wiki/Toyota_Land_Cruiser, https://en.wikipedia.org/wiki/Toyota_Camry, https://en.wikipedia.org/wiki/Toyota_Prius]"));
    }
}
