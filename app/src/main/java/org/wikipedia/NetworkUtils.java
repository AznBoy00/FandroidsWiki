/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wikipedia;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String WIKI_BASE_URL =
            "https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&format=json&search=";
    final static String WIKI_BASE_URL_SPECIAL =
            "https://en.wikipedia.org/w/api.php?action=opensearch&limit=10&format=json&search=";

    /**
     * Builds the URL used to query wikipedia.
     */
    public static URL buildUrl(String wikiSearchQuery) {
        System.out.print(WIKI_BASE_URL);
        Uri builtUri = Uri.parse(WIKI_BASE_URL+wikiSearchQuery).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildSpecialUrl(String wikiSearchQuery) {
        System.out.print(WIKI_BASE_URL);
        Uri builtUri = Uri.parse(WIKI_BASE_URL_SPECIAL+wikiSearchQuery).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}