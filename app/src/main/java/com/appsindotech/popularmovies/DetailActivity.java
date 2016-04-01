package com.appsindotech.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.appsindotech.popularmovies.api.model.MovieData;
import com.appsindotech.popularmovies.fragment.DetailActivityFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MovieData data = getIntent().getParcelableExtra("movie_data");
        DetailActivityFragment detailFragment = DetailActivityFragment.newInstance(data);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_container, detailFragment);
        ft.commit();
    }

}
