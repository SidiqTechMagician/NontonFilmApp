package com.nbs.nontonfilmapp.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.nbs.nontonfilmapp.R;
import com.nbs.nontonfilmapp.adapter.MovieAdapter;
import com.nbs.nontonfilmapp.api.ApiService;
import com.nbs.nontonfilmapp.base.BaseActivity;
import com.nbs.nontonfilmapp.model.DiscoverMovieResult;
import com.nbs.nontonfilmapp.model.MovieItem;
import com.nbs.nontonfilmapp.util.Constant;
import com.nbs.nontonfilmapp.util.ProgressHolder;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends BaseActivity {
    private final static int MAX_WIDTH_COL_DP = 185;
    private final static String KEY_STATE_SORT = "keyStateSort";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_movie)
    RecyclerView rvMovie;
    @Bind(R.id.progress_holder)
    ProgressHolder progressHolder;

    private ArrayList<MovieItem> listMovie ;
    private MovieAdapter movieAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private String currentSort = Constant.SORT_BY_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initProcess();
        initRequest();
        initEvent();
    }

    @Override
    public void initUI() {
        super.initUI();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(staggeredGridLayoutManager);
        rvMovie.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rvMovie.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rvMovie.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        int viewWidth = rvMovie.getMeasuredWidth();
                        float cardViewWidth = MAX_WIDTH_COL_DP * getResources().getDisplayMetrics().density;
                        int newSpanCount = Math.max(2, (int) Math.floor(viewWidth / cardViewWidth));
                        staggeredGridLayoutManager.setSpanCount(newSpanCount);
                        staggeredGridLayoutManager.requestLayout();
                    }
                });

    }

    @Override
    public void initProcess() {
        super.initProcess();
        listMovie = new ArrayList<>();
        movieAdapter = new MovieAdapter(MainActivity.this, listMovie);
        rvMovie.setAdapter(movieAdapter);
    }

    @Override
    public void initRequest() {
        super.initRequest();
        callDiscoverMovieAsync();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        progressHolder.setRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDiscoverMovieAsync();
            }
        });
    }

    private void callDiscoverMovieAsync(){
        rvMovie.setVisibility(View.GONE);
        Call<DiscoverMovieResult> discoverMovieResultCall = mApiClient.getDiscoverMovieAsync(ApiService.APIKEY, currentSort);
        discoverMovieResultCall.enqueue(new Callback<DiscoverMovieResult>() {
            @Override
            public void onResponse(Response<DiscoverMovieResult> response, Retrofit retrofit) {
                parsingResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                progressHolder.stopAndError(t.getMessage().toLowerCase(), true);
            }
        });
    }

    private void parsingResponse(Response<DiscoverMovieResult> response) {
        DiscoverMovieResult mDiscoverMovieResult = response.body();
        if (mDiscoverMovieResult != null){
            listMovie.clear();
            listMovie.addAll(mDiscoverMovieResult.listItems);
            movieAdapter.notifyDataSetChanged();

            rvMovie.setVisibility(View.VISIBLE);
            progressHolder.stopAndGone();
        }else{
            try {
                progressHolder.stopAndError(new String(response.errorBody().bytes()), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter){
            showSortDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.movie_sort_by)
                .setSingleChoiceItems(
                        new String[]{getString(R.string.sort_by_most_popular),
                                getString(R.string.sort_by_highest_rated)},
                        (currentSort.equals(Constant.SORT_BY_POPULAR)) ? 0 : 1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                currentSort = (which == 0) ? Constant.SORT_BY_POPULAR : Constant.SORT_BY_HIGHEST_RATED;
                                callDiscoverMovieAsync();
                                dialog.dismiss();
                            }
                        });
        builder.create().show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_STATE_SORT, currentSort);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentSort = savedInstanceState.getString(KEY_STATE_SORT);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
