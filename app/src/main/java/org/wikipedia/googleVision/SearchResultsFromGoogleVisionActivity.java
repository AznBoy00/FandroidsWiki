package org.wikipedia.googleVision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;

import org.wikipedia.NetworkUtils;
import org.wikipedia.R;
import org.wikipedia.WikiUrlRetriever;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResultsFromGoogleVisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_from_google_vision);

        // Declaration of views
        WebView webView = (WebView) findViewById(R.id.webview);
        ListView listview = (ListView) findViewById(R.id.listView_for_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.page_toolbar);

        // Set the toolbar
        setSupportActionBar(toolbar);

        // When the W icon have been clicked, it will finish/kill the activity
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //TODO put your keywords from intent
        Intent intent = getIntent();
        String [] keywords = intent.getStringArrayExtra("extra_data");
        for(String index : keywords){

            Log.e("55555555555555",index);

        }
        //String[] keywords = {"honda", "toyota"};

        // Get the list of urls
        List<String> listviewUrlElements = displayGoogleVisionResults(keywords);
        // Get the list of article titles
        List<String> listviewNameElements = convertToTitle(listviewUrlElements);

        // Adapter to automatically put in the listview
        ArrayAdapter listAdapter = new ArrayAdapter<String>(this,
                R.layout.search_results_listview, R.id.article_title, listviewNameElements);
        listview.setAdapter(listAdapter);
        // A webview will be opened and redirect to the corresponding page when clicked
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) {
                String url = listviewUrlElements.get(position);
                listview.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(url);
            }

        });
    }

    // Search keywords to find related articles
    public List<String> displayGoogleVisionResults(String[] keywords) {
        // Builds the articleTitle to an URL
//        String[] keywords = {"honda", "toyota"};
        List<String> FinalListOfURL = new ArrayList<String>();
        List<String> listOfURL = new ArrayList<String>();
        for(int index=0; index < keywords.length; index++) {
            URL wikiSearchQuery = NetworkUtils.buildSpecialUrl(keywords[index]);
            try {
                String urls = new WikiUrlRetriever().execute(wikiSearchQuery).get();
                listOfURL = Arrays.asList(urls.split(","));
                for(int count=0; count < listOfURL.size(); count++) {
                    // Add the elements to the ArrayList
                    FinalListOfURL.add(listOfURL.get(count));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //For testing all the elements inside the array
//        for(int count=0; count < FinalListOfURL.size(); count++) {
//            System.out.println("TEST!:"+count + "." + FinalListOfURL.get(count) + "\n");
//        }
        System.out.println("TEST:"+ FinalListOfURL);
        return FinalListOfURL;
    }

    // Get the article titles from the url
    private List<String> convertToTitle(List<String> listOfUrls) {
        List<String> titlesList = new ArrayList<String>();
        String cleanTitle = null;
        for(int index=0; index < listOfUrls.size(); index++) {
            cleanTitle = (listOfUrls.get(index)).replace("https://en.wikipedia.org/wiki/", "");
            cleanTitle = cleanTitle.replace("_", " ");
            titlesList.add(cleanTitle);
        }
        return titlesList;
    }

}
