package org.wikipedia;

import org.wikipedia.util.WikiResponseUtils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiService {

    private final String TAG = "ApiService";

    //TODO check how it works
    //OkHttpClient client =OkHttpConnectionFactory.getClient();

    //Use singleton pattern:
    private static final ApiService apiServiceInstance = new ApiService();
    private ApiService(){};
    public static ApiService getApiServiceInstance()
    {
        return apiServiceInstance;
    };
    //Create a new okHttpClient
    private final OkHttpClient client = new OkHttpClient();

    // Get request using okhttp
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    // Local test fuction
    public static void main(String[] args) throws IOException {
        ApiService example = new ApiService();
        String response = example.run("https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&format=json&search=bee");
        response = WikiResponseUtils.getWikiDescriptionFromResponse(response);
        System.out.println(response);
    }

    }


