package org.wikipedia.util;

public class WikiResponseUtils {

    // Retrieves description/summary of the desired article from Wikipedia
    public static String getWikiDescriptionFromResponse(String response){

        // The uncleaned version of the response
        String description = response;

        // Start of cleaning up the response
        description = description.substring(description.indexOf(",[",description.indexOf(",[",description.indexOf("]"))));
        description = description.substring(description.indexOf("[\"")+2,description.indexOf("\"]"));

        return description;
    }

    public static String getWikiUrlsOnly(String response) {
        // Uncleaned
        String urls = response;

        // Cleaning process
        urls = urls.substring((urls.indexOf("https")), urls.length());
        urls = urls.replace("\"", "");
        urls = urls.replace("]]", "");
        System.out.println("TEST: urls is " + urls);
        return urls;
    }
}
