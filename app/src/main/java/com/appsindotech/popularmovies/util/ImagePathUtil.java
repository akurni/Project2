package com.appsindotech.popularmovies.util;

/**
 * Created by andrewkurniadi on 12/5/15.
 */
public class ImagePathUtil {
    public static final String BASE_PATH = "http://image.tmdb.org/t/p/";
    public static String getImagePath(String path)
    {
        return String.format(BASE_PATH + "w185/%s", path);
    }
}
