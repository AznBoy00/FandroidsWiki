package org.wikipedia.googleVision;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.wikipedia.R;
import org.wikipedia.search.SearchFragment;
import org.wikipedia.search.SearchResultsFragment;

import java.lang.reflect.Array;

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
    private void displayGoogleVisionResults() {
        String[] words = {"car"};
        SearchFragment searchFragment = new SearchFragment();
        for(int index=0; index < words.length ; index++){
            searchFragment.startSearch(words[index], true);
        }
    }

}
