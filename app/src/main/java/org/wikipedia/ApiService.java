package org.wikipedia;

import android.util.Log;

import org.wikipedia.dataclient.okhttp.OkHttpConnectionFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiService {

    private String TAG = "ApiService";

    //TODO check how it works
    //OkHttpClient client =OkHttpConnectionFactory.getClient();

    //Create a new okHttpClient
    OkHttpClient client = new OkHttpClient();

    // Get request using okhttp
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Log.e(TAG,response.toString());
            return response.body().string();
        }
    }


    // Local test fuction
    public static void main(String[] args) throws IOException {
        ApiService example = new ApiService();
        String response = example.run("https://en.wikipedia.org/w/api.php?action=opensearch&search=2010%E2%80%932017%20Toronto%20serial%20homicides&limit=1&format=json");
        System.out.println(response);
    }

    }


