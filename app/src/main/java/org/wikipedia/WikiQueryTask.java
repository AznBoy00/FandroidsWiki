package org.wikipedia;

import android.os.AsyncTask;
import android.util.Log;

import org.wikipedia.util.WikiResponseUtils;

import java.io.IOException;
import java.net.URL;


public class WikiQueryTask extends AsyncTask<URL,Void,String> {

    private final String TAG = "WikiQueryTask";

    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String wikiResult = null;
        ApiService wikiApiService = ApiService.getApiServiceInstance();
        try{
            String wikiSearchResults = wikiApiService.run(searchUrl.toString());
            wikiResult = retrieveResult(wikiSearchResults);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return wikiResult;
    }

    protected String retrieveResult(String wikiSearchResults) {
        String description = null;
        if (wikiSearchResults != null && !wikiSearchResults.equals("")) {
            description = wikiSearchResults;
            description = WikiResponseUtils.getWikiDescriptionFromResponse(description);
            Log.d(TAG,"The return was successful!");
        }
        else{
            Log.d(TAG,"Return failed!");
        }
        return description;
    }

}
