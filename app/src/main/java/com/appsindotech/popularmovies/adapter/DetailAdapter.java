package com.appsindotech.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsindotech.popularmovies.R;
import com.appsindotech.popularmovies.api.model.MovieData;
import com.appsindotech.popularmovies.api.model.ReviewsData;
import com.appsindotech.popularmovies.api.model.ReviewsResult;
import com.appsindotech.popularmovies.api.model.VideoResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_TRAILERS = 1;
    private static final int TYPE_REVIEWS = 1;
    private final Context context;

    MovieData movieData;
    List<VideoResult> trailers;
    List<ReviewsResult> reviews;

    public DetailAdapter(Context context, MovieData movieData) {
        this.context = context;
        this.movieData = movieData;
        this.trailers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public void setTrailers(List<VideoResult> trailers)
    {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setReviews(List<ReviewsResult> reviews)
    {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_header_layout, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_TRAILERS) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_trailer_layout, parent, false);
            return new TrailerViewHolder(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    private VideoResult getTrailerItem(int position)
    {
        return trailers.get(position-1);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder)
        {
            HeaderViewHolder header = (HeaderViewHolder)holder;
            Picasso.with(context).load(movieData.getPosterPath()).into(header.imagePoster);
            header.textTitle.setText(movieData.getTitle());
            if(movieData.getReleaseDate() != null)
                header.textRelease.setText(movieData.getReleaseDate().substring(0, 4));
            header.textRating.setText(String.format("%.1f/10", movieData.getVoteAverage()));
            header.textOverview.setText(movieData.getOverview());
        }
        else if(holder instanceof TrailerViewHolder)
        {
            VideoResult currentTrailer = getTrailerItem(position);
            TrailerViewHolder trailer = (TrailerViewHolder)holder;
            trailer.textTrailerTitle.setText(currentTrailer.getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        if (isPositionTrailer(position))
            return TYPE_TRAILERS;
        return TYPE_REVIEWS;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionTrailer(int position) {
        return position != 0 && position < trailers.size();
    }

    //increasing getItemcount to 1. This will be the row of header.
    @Override
    public int getItemCount() {
        return trailers.size() + reviews.size() + 1;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_title)
        TextView textTitle;
        @Bind(R.id.image_poster)
        ImageView imagePoster;
        @Bind(R.id.text_release)
        TextView textRelease;
        @Bind(R.id.text_duration)
        TextView textDuration;
        @Bind(R.id.text_rating)
        TextView textRating;
        @Bind(R.id.text_overview)
        TextView textOverview;
        @Bind(R.id.btn_favorite)
        Button btnFavorite;

        public HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.text_trailer_title)
        TextView textTrailerTitle;

        public TrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}