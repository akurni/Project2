package com.appsindotech.popularmovies;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.appsindotech.popularmovies.api.model.MovieData;
import com.appsindotech.popularmovies.fragment.DetailActivityFragment;
import com.appsindotech.popularmovies.fragment.MovieListFragment;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectedListener {

    private boolean isDualPane;
    private View detailBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_container) != null) {
            isDualPane = true;

            detailBackground = findViewById(R.id.linearlayout_overlay);
            detailBackground.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    int action = MotionEventCompat.getActionMasked(motionEvent);

                    switch(action) {
                        case (MotionEvent.ACTION_DOWN) :
                            return removeMovieDetailFragmentIfExist();
                        default :
                            return false;
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMovieDetail(MovieData data) {
        if (isDualPane) {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.movie_detail_container);


            // If we are not currently showing a fragment for the new
            // position, we need to create and install a new one.
            DetailActivityFragment df = DetailActivityFragment.newInstance(data);

            // Execute a transaction, replacing any existing fragment
            // with this one inside the frame.
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();

            if (currentFragment == null) {
                ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_back);
            }

            ft.replace(R.id.movie_detail_container, df);
            ft.commit();
        }else
        {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("movie_data", data);
            startActivity(i);
        }

    }

    @Override
    public void onBackPressed() {

        if (!removeMovieDetailFragmentIfExist())
            super.onBackPressed();
    }

    public boolean removeMovieDetailFragmentIfExist() {
        Fragment detailFragment = (Fragment) getSupportFragmentManager()
                .findFragmentById(R.id.movie_detail_container);

        if (detailFragment != null && !detailFragment.isRemoving()) {
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_back);

            ft.remove(detailFragment);
            ft.commit();

            return true;
        }

        return false;
    }

    @Override
    public void onMovieSelected(MovieData data) {
        showMovieDetail(data);
    }
}
