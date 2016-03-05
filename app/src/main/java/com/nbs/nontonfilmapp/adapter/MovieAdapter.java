package com.nbs.nontonfilmapp.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nbs.nontonfilmapp.R;
import com.nbs.nontonfilmapp.activities.DetailMovieActivity;
import com.nbs.nontonfilmapp.api.ApiService;
import com.nbs.nontonfilmapp.model.MovieItem;
import com.nbs.nontonfilmapp.util.CustomOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sidiq on 05/03/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private List<MovieItem> listItem = new ArrayList<>();
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_poster)
        ImageView imgPoster;
        @Bind(R.id.txt_title)
        TextView txtTitle;
        public View mRoot;

        public ViewHolder(View v) {
            super(v);
            mRoot = v;

            ButterKnife.bind(this, v);
        }
    }

    public MovieAdapter(Activity mActivity, ArrayList<MovieItem> mListItem) {
        this.listItem = mListItem;
        this.activity = mActivity;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MovieItem item = listItem.get(position);

        holder.txtTitle.setText(item.getTitle());

        String imageUrl = ApiService.ROOT_POSTER_IMAGE_URL + item.getPosterPath();

        if (!TextUtils.isEmpty(item.getPosterPath())){
            Glide.with(activity)
                    .load(imageUrl)
                    .placeholder(R.color.grey_200)
                    .error(android.R.color.transparent)
                    .into(holder.imgPoster);
        }else{
            holder.imgPoster.setBackgroundColor(ContextCompat.getColor(activity, R.color.grey_200));
        }

        holder.mRoot.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                DetailMovieActivity.start(activity, listItem.get(position));
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
