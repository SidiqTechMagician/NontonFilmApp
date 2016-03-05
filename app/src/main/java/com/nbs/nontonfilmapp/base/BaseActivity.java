package com.nbs.nontonfilmapp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nbs.nontonfilmapp.R;
import com.nbs.nontonfilmapp.api.ApiClient;
import com.nbs.nontonfilmapp.api.ApiService;

/**
 * Created by Sidiq on 05/03/2016.
 */
public class BaseActivity extends AppCompatActivity implements BaseMethod {
    public ApiClient mApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApiClient = ApiService.createService(ApiClient.class);
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initUI(View view) {

    }

    @Override
    public void initProcess() {

    }

    @Override
    public void initLib() {

    }

    @Override
    public void initRequest() {

    }

    @Override
    public void initEvent() {

    }

    public void setUpImage(String imageUrl, ImageView img){
        Glide.with(BaseActivity.this)
                .load(imageUrl)
                .placeholder(R.color.grey_200)
                .error(R.color.grey_200)
                .into(img);
    }

    public String getPosterImageUrl(String posterPath){
        return ApiService.ROOT_POSTER_IMAGE_URL + posterPath;
    }

    public String getBackdropImageUrl(String backdropPath){
        return ApiService.ROOT_BACKDROP_IMAGE_URL + backdropPath;
    }
}
