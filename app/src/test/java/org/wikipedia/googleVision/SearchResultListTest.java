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

    @Before
    public void SetUp() {
        NetworkUtils network = mock(NetworkUtils.class);
        WikiUrlRetriever urlRetriever = mock(WikiUrlRetriever.class);
        NetworkUtils urlBuilder = mock(NetworkUtils.class);
        searchResultsMocked = mock(SearchResultsFromGoogleVisionActivity.class);
        String fakeKeyword = "honda";
//        Mockito.doReturn(urlBuilder.buildSpecialUrl("honda")).when(searchResultsMocked);
        expectedResults = "[https://en.wikipedia.org/wiki/Honda, https://en.wikipedia.org/wiki/Honda_Civic, https://en.wikipedia.org/wiki/Honda_Accord, https://en.wikipedia.org/wiki/Honda_Civic_Type_R, https://en.wikipedia.org/wiki/Honda_Fit, https://en.wikipedia.org/wiki/Honda_City, https://en.wikipedia.org/wiki/Honda_Gold_Wing, https://en.wikipedia.org/wiki/Honda_in_Formula_One, https://en.wikipedia.org/wiki/Honda_Civic_(sixth_generation), https://en.wikipedia.org/wiki/Honda_Insight]";
//        expectedResults = "[https://en.wikipedia.org/wiki/Honda]";
        expectedResultsList = Arrays.asList("https://en.wikipedia.org/wiki/Honda", "https://en.wikipedia.org/wiki/Honda_Civic", "https://en.wikipedia.org/wiki/Honda_Accord", "https://en.wikipedia.org/wiki/Honda_Civic_Type_R", "https://en.wikipedia.org/wiki/Honda_Fit", "https://en.wikipedia.org/wiki/Honda_City", "https://en.wikipedia.org/wiki/Honda_Gold_Wing", "https://en.wikipedia.org/wiki/Honda_in_Formula_One", "https://en.wikipedia.org/wiki/Honda_Civic_(sixth_generation)", "https://en.wikipedia.org/wiki/Honda_Insight");
        expectedCleanUrlsList = Arrays.asList("Honda", "Honda Civic", "Honda Accord", "Honda Civic Type R", "Honda Fit", "Honda City", "Honda Gold Wing", "Honda in Formula One", "Honda Civic (sixth generation)", "Honda Insight");
        expectedCleanUrls = "[Honda, Honda Civic, Honda Accord, Honda Civic Type R, Honda Fit, Honda City, Honda Gold Wing, Honda in Formula One, Honda Civic (sixth generation), Honda Insight]";

    }

    /** Tests for the display of keywords generated from Google Vision **/
    @Test
    public void testDisplayGoogleVisionResults() {
//        when(searchResults.displayGoogleVisionResults(new String[]{"honda"})).thenReturn(expectedResultsList);
//        SearchResultsFromGoogleVisionActivity searchResults = new SearchResultsFromGoogleVisionActivity();
//        SearchResultsFromGoogleVisionActivity searchResults = mock(SearchResultsFromGoogleVisionActivity.class);
//        NetworkUtils network = Mockito.spy(NetworkUtils.class);
//        Uri uri = Mockito.spy(Uri.class);
////        URL url = mock(URL.class);
////        try {
//            Mockito.doReturn((uri.parse( "https://en.wikipedia.org/w/api.php?action=opensearch&limit=10&format=json&search=honda").buildUpon().build()).toString()).when(network.buildSpecialUrl("honda"));
//
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        }
//        List<String> listOfUrls = searchResults.displayGoogleVisionResults(new String[]{"honda"});
//        System.out.println("TEST:" + listOfUrls.toString());
//        assertTrue((listOfUrls.toString()).equals(expectedResults));
    }

    @Test
    public void TestConvertToTitle() {
//        when(searchResults.convertToTitle(expectedResultsList)).thenReturn(expectedCleanUrlsList);
        SearchResultsFromGoogleVisionActivity searchResults = new SearchResultsFromGoogleVisionActivity();
        List<String> cleanUrls = searchResults.convertToTitle(expectedResultsList);
        System.out.println("TEST:" + cleanUrls.toString());
        System.out.println("TEST:" + expectedCleanUrls.toString());
        assertTrue((cleanUrls.toString()).equals(expectedCleanUrls));
    }
}
