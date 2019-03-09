package org.wikipedia.googleVision;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.wikipedia.NetworkUtils;
import org.wikipedia.R;
import org.wikipedia.WikiUrlRetriever;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class searchResultsFromGoogleVisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_from_google_vision);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        displayGoogleVisionResults();
    }

    //Cannot use the SearchResultsFragment, it has too many dependencies
//    private void displayGoogleVisionResults() {
//        String[] words = {"car"};
//        SearchFragment searchFragment = new SearchFragment();
//        for(int index=0; index < words.length ; index++){
//            searchFragment.startSearch(words[index], true);
//        }
//    }

    private void displayGoogleVisionResults() {
        // Builds the articleTitle to an URL
        String[] keywords = {"honda", "toyota"};
        List<String> FinalListOfURL = new ArrayList<String>();
        List<String> listOfURL = new ArrayList<String>();
        for(int index=0; index < keywords.length; index++) {
            URL wikiSearchQuery = NetworkUtils.buildSpecialUrl(keywords[index]);
            System.out.println("TEST: wikiSearchQuery is " + wikiSearchQuery);
            try {
                String urls = new WikiUrlRetriever().execute(wikiSearchQuery).get();
                System.out.println("TEST: The readingstr is " + urls);
                listOfURL = Arrays.asList(urls.split(","));
                for(int count=0; count < listOfURL.size(); count++) {
                    System.out.println("TEST:"+count + "." + listOfURL.get(count) + "\n");
                    FinalListOfURL.add(listOfURL.get(count));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int count=0; count < FinalListOfURL.size(); count++) {
            System.out.println("TEST!:"+count + "." + FinalListOfURL.get(count) + "\n");
        }
    }

}
