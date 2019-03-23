package org.wikipedia;

import android.os.AsyncTask;
import android.util.Log;

import org.wikipedia.util.WikiResponseUtils;

import java.io.IOException;
import java.net.URL;


public class WikiUrlRetriever extends AsyncTask<URL,Void,String> {

    private final String TAG = "WikiUrlRetriever";

    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String wikiResult = null;
        ApiService wikiApiService = ApiService.getApiServiceInstance();
        try{
            String wikiSearchResults = wikiApiService.run(searchUrl.toString());
            wikiResult = retrieveURLFromResult(wikiSearchResults);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return wikiResult;
    }

    protected String retrieveURLFromResult(String wikiSearchResults) {
        String description = null;
        if (wikiSearchResults != null && !wikiSearchResults.equals("")) {
            description = wikiSearchResults;
            description = WikiResponseUtils.getWikiUrlsOnly(description);
            Log.d(TAG,"The return was successful!");
        }
        else{
            Log.d(TAG,"Return failed!");
        }
        return description;
    }

}
