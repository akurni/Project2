package com.appsindotech.popularmovies.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsindotech.popularmovies.R;
import com.appsindotech.popularmovies.adapter.DetailAdapter;
import com.appsindotech.popularmovies.api.MovieDbService;
import com.appsindotech.popularmovies.api.model.MovieData;
import com.appsindotech.popularmovies.api.model.MovieResults;
import com.appsindotech.popularmovies.api.model.VideoData;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String MOVIE_DATA = "data";

    @Bind(R.id.detail_list)
    RecyclerView detailList;
    private DetailAdapter adapter;
    private Subscription subscription;
    private MovieData movieData;

    public DetailActivityFragment() {
    }

    public static DetailActivityFragment newInstance(MovieData data) {
        DetailActivityFragment f = new DetailActivityFragment();

        // Supply json input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_DATA, data);

        f.setArguments(args);

        return f;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        detailList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Get movie data and display it to the user
        movieData = (MovieData)getArguments().getParcelable(MOVIE_DATA);

        adapter = new DetailAdapter(getActivity(), movieData);
        detailList.setAdapter(adapter);

        loadData();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void loadData()
    {
        subscription = MovieDbService.getDb().getVideos(movieData.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<VideoData>() {
                    @Override
                    public void call(VideoData videoData) {
                        adapter.setTrailers(videoData.getResults());
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
