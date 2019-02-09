package org.wikipedia.util;

public class WikiResponseUtils {
    // For wiki description

    public static String getWikiDescriptionFromResponse(String response){
        String description = response;
        System.out.println(description.length());
        System.out.println(description.indexOf("]"));
        System.out.println(description.indexOf(",[",description.indexOf("]")));
        System.out.println(description.substring(description.indexOf(",[",description.indexOf(",[",description.indexOf("]")))));
        // First time cut
        description = description.substring(description.indexOf(",[",description.indexOf(",[",description.indexOf("]"))));
        System.out.println(description);
        System.out.println(description.indexOf("[\"")+2);
        System.out.println(description.indexOf("\"]"));
        description = description.substring(description.indexOf("[\"")+2,description.indexOf("\"]"));
        System.out.println(description);

        return description;
    }
}
