package com.appsindotech.popularmovies.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsindotech.popularmovies.DetailActivity;
import com.appsindotech.popularmovies.R;
import com.appsindotech.popularmovies.adapter.MovieAdapter;
import com.appsindotech.popularmovies.api.MovieDbService;
import com.appsindotech.popularmovies.api.model.MovieData;
import com.appsindotech.popularmovies.api.model.MovieResults;
import com.appsindotech.popularmovies.widget.AutofitRecyclerView;
import com.appsindotech.popularmovies.widget.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMovieSelectedListener} interface
 * to handle interaction events.
 */
public class MovieListFragment extends Fragment implements MovieAdapter.OnItemClickListener {

    @Bind(R.id.movie_grid)
    AutofitRecyclerView movieGrid;

    public static final String MOVIE_KEY="movies";

    private OnMovieSelectedListener mListener;
    private rx.Subscription subscription;
    private MovieAdapter movieGridAdapter;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    private List<MovieData> listOfMovies;


    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);

        movieGrid.setHasFixedSize(true);
        movieGrid.addItemDecoration(new MarginDecoration(getActivity()));

        movieGridAdapter = new MovieAdapter(getActivity());
        movieGridAdapter.setOnItemClickListener(this);

        movieGrid.setAdapter(movieGridAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieSelectedListener) {
            mListener = (OnMovieSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(listOfMovies != null)
        {
            movieGridAdapter.setData(listOfMovies);
        }
        loadData();

    }

    @Override
    public void onStop() {
        super.onStop();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void loadData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = sharedPreferences.getString(getString(R.string.pref_sort_order_key), "popular.desc");

        if (sortOrder.contentEquals("popular.desc")) {
            getActivity().setTitle(getString(R.string.popular_movies));
        } else {
            getActivity().setTitle(getString(R.string.highest_rated));
        }
        subscription = MovieDbService.getDb().getPopularMovies(sortOrder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<MovieResults>() {
                    @Override
                    public void call(MovieResults movieResults) {
                        listOfMovies = movieResults.getResults();
                        movieGridAdapter.setData(movieResults.getResults());
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("MovieListFragment", "Item Clicked pos: " + position);

        mListener.onMovieSelected(movieGridAdapter.getItem(position));
        
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_KEY, (ArrayList<? extends Parcelable>) listOfMovies);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            listOfMovies = (List<MovieData>)savedInstanceState.get(MOVIE_KEY);
        }
    }


    public interface OnMovieSelectedListener
    {
        public void onMovieSelected(MovieData data);
    }
    
}
