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
        String wikiSearchResults = null;
        ApiService wikiApiService = ApiService.getApiServiceInstance();
        try{wikiSearchResults = wikiApiService.run(searchUrl.toString());}catch (IOException e){
            e.printStackTrace();
        }
        return wikiSearchResults;
    }

    @Override
    protected void onPostExecute(String wikiSearchResults) {
        if (wikiSearchResults != null && !wikiSearchResults.equals("")) {
            String description = wikiSearchResults;
            description = WikiResponseUtils.getWikiDescriptionFromResponse(description);
            Log.d(TAG,"Successful Return!");
            Log.d(TAG,description);
        }
        else{
            Log.d(TAG,"nothing Return!");
        }
    }

}
