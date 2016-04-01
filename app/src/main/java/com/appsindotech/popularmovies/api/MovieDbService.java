package com.appsindotech.popularmovies.api;

import com.appsindotech.popularmovies.api.model.MovieResults;
import com.appsindotech.popularmovies.api.model.ReviewsData;
import com.appsindotech.popularmovies.api.model.VideoData;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public final class MovieDbService {
    public static final String API_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "fe2ba09ec60e3f3834bc68522989a9ac";
    public static MovieDb db;

    public static MovieDb getDb() {
        if (db == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Create an instance of our GitHub API interface.
            db = retrofit.create(MovieDb.class);
        }

        return db;

    }

    public interface MovieDb {
        @GET("discover/movie?api_key=" + API_KEY)
        Observable<MovieResults> getPopularMovies(
                @Query("sort_by") String sortBy);

        @GET("movie/{id}/videos?api_key=" + API_KEY)
        Observable<VideoData> getVideos(
                @Path("id") Integer id);

        @GET("movie/{id}/reviews?api_key=" + API_KEY)
        Observable<ReviewsData> getReviews(
                @Path("id") Integer id);
    }
}
