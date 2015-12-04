package com.appsindotech.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsindotech.popularmovies.R;
import com.appsindotech.popularmovies.api.model.MovieData;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    @Bind(R.id.image_poster)
    ImageView imagePoster;
    @Bind(R.id.text_title)
    TextView textTitle;
    @Bind(R.id.text_release)
    TextView textRelease;
    @Bind(R.id.text_duration)
    TextView textDuration;
    @Bind(R.id.text_rating)
    TextView textRating;
    @Bind(R.id.text_overview)
    TextView textOverview;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Get movie data and display it to the user
        MovieData movieData = getActivity().getIntent().getExtras().getParcelable("movie_detail");

        Picasso.with(getActivity()).load(movieData.getPosterPath()).into(imagePoster);
        textTitle.setText(movieData.getTitle());
        textRelease.setText(movieData.getReleaseDate().substring(0, 4));
        textRating.setText(String.format("%.1f/10", movieData.getVoteAverage()));
        textOverview.setText(movieData.getOverview());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
