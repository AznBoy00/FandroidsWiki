package org.wikipedia.googleVision;

import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.wikipedia.NetworkUtils;
import org.wikipedia.WikiUrlRetriever;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class SearchResultListTest {

    private String[] listOfKeywords;
    SearchResultsFromGoogleVisionActivity searchResultsMocked;
    String[] keywords;
    String expectedResults;
    List<String> expectedResultsList;
    List<String> expectedCleanUrlsList;
    String expectedCleanUrls;
    List<String> fakeExpectedCleanUrlsList;

    @Before
    public void SetUp() {
        NetworkUtils network = mock(NetworkUtils.class);
        WikiUrlRetriever urlRetriever = mock(WikiUrlRetriever.class);
        NetworkUtils urlBuilder = mock(NetworkUtils.class);
        searchResultsMocked = mock(SearchResultsFromGoogleVisionActivity.class);
        expectedResults = "[https://en.wikipedia.org/wiki/Honda, https://en.wikipedia.org/wiki/Honda_Civic, https://en.wikipedia.org/wiki/Honda_Accord, https://en.wikipedia.org/wiki/Honda_Civic_Type_R, https://en.wikipedia.org/wiki/Honda_Fit, https://en.wikipedia.org/wiki/Honda_City, https://en.wikipedia.org/wiki/Honda_Gold_Wing, https://en.wikipedia.org/wiki/Honda_in_Formula_One, https://en.wikipedia.org/wiki/Honda_Civic_(sixth_generation), https://en.wikipedia.org/wiki/Honda_Insight]";
        expectedResultsList = Arrays.asList("https://en.wikipedia.org/wiki/Honda", "https://en.wikipedia.org/wiki/Honda_Civic", "https://en.wikipedia.org/wiki/Honda_Accord", "https://en.wikipedia.org/wiki/Honda_Civic_Type_R", "https://en.wikipedia.org/wiki/Honda_Fit", "https://en.wikipedia.org/wiki/Honda_City", "https://en.wikipedia.org/wiki/Honda_Gold_Wing", "https://en.wikipedia.org/wiki/Honda_in_Formula_One", "https://en.wikipedia.org/wiki/Honda_Civic_(sixth_generation)", "https://en.wikipedia.org/wiki/Honda_Insight");
        expectedCleanUrlsList = Arrays.asList("Honda", "Honda Civic", "Honda Accord", "Honda Civic Type R", "Honda Fit", "Honda City", "Honda Gold Wing", "Honda in Formula One", "Honda Civic (sixth generation)", "Honda Insight");
        expectedCleanUrls = "[Honda, Honda Civic, Honda Accord, Honda Civic Type R, Honda Fit, Honda City, Honda Gold Wing, Honda in Formula One, Honda Civic (sixth generation), Honda Insight]";
        fakeExpectedCleanUrlsList = Arrays.asList("Honda", "Honda Civic", "Honda Accord", "Honda Civic Type R", "Honda Fit", "Honda City", "Honda Gold Wing", "Honda in Formula One", "Honda Civic (sixth generation)", "Honda Insight");

    }

    @Test
    public void TestConvertToTitle() {
        SearchResultsFromGoogleVisionActivity searchResults = new SearchResultsFromGoogleVisionActivity();
        List<String> cleanUrls = searchResults.convertToTitle(expectedResultsList);
        assertTrue((cleanUrls.toString()).equals(expectedCleanUrls));
    }

    @Test
    public void TestConvertToTitleFalse() {
        SearchResultsFromGoogleVisionActivity searchResults = new SearchResultsFromGoogleVisionActivity();
        List<String> cleanUrls = searchResults.convertToTitle(expectedResultsList);
        assertNotSame(cleanUrls.toString(), fakeExpectedCleanUrlsList);
    }
}
