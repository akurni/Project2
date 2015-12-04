package com.appsindotech.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsindotech.popularmovies.R;
import com.appsindotech.popularmovies.api.model.MovieData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private final Context context;
    private List<MovieData> movieData = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_movie_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public void setData(List<MovieData> results) {
        movieData = results;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieData movieItem = movieData.get(position);
        Picasso.with(context).load(movieItem.getPosterPath()).into(holder.imgThumbnail);
        holder.textTitle.setText(movieItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'grid_movie_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @Bind(R.id.img_thumbnail)
        ImageView imgThumbnail;
        @Bind(R.id.text_title)
        TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MovieData getItem(int position)
    {
        return movieData.get(position);
    }



}