package com.nbs.nontonfilmapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nbs.nontonfilmapp.R;
import com.nbs.nontonfilmapp.base.BaseActivity;
import com.nbs.nontonfilmapp.model.MovieItem;
import com.nbs.nontonfilmapp.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailMovieActivity extends BaseActivity {

    @Bind(R.id.img_backdrop)
    ImageView imgBackdrop;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.img_poster)
    ImageView imgPoster;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_release_date)
    TextView txtReleaseDate;
    @Bind(R.id.txt_vote_average)
    TextView txtVoteAverage;
    @Bind(R.id.txt_synopsis)
    TextView txtSynopsis;

    public static String KEY_MOVIE_ITEM = "movieItem";
    private MovieItem movieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_detail_movie);

        initIntent();
        initUI();
        initProcess();
    }

    @Override
    public void initIntent() {
        super.initIntent();
        movieItem = getIntent().getParcelableExtra(KEY_MOVIE_ITEM);
    }

    @Override
    public void initUI() {
        super.initUI();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initProcess() {
        super.initProcess();
        txtTitle.setText(movieItem.getTitle());
        txtSynopsis.setText(movieItem.getOverview());
        txtReleaseDate.setText("Release on "+Util.getFormattedDate(movieItem.getReleaseDate()));
        txtVoteAverage.setText(movieItem.getVoteAverage()+"/"+movieItem.getVoteCount());

        String posterUrl = getPosterImageUrl(movieItem.getPosterPath());
        String backdropUrl = getBackdropImageUrl(movieItem.getBackdropPath());

        setUpImage(posterUrl, imgPoster);
        setUpImage(backdropUrl, imgBackdrop);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_MOVIE_ITEM, movieItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movieItem = savedInstanceState.getParcelable(KEY_MOVIE_ITEM);
    }

    public static void start(Activity activity, MovieItem mMovieItem) {
        Intent starter = new Intent(activity, DetailMovieActivity.class);
        starter.putExtra(KEY_MOVIE_ITEM, mMovieItem);
        activity.startActivityForResult(starter, 0);
    }
}
