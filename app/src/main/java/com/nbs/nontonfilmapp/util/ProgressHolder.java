package com.nbs.nontonfilmapp.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nbs.nontonfilmapp.R;

/**
 * Created by Sidiq on 05/03/2016.
 */
public class ProgressHolder extends LinearLayout{
    Context mContext;
    ViewGroup rlProgress;
    ProgressBar progressIndicator;
    TextView txtErrorMessage;
    Button btnRequestRetry;

    public ProgressHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        View v = LayoutInflater.from(context).inflate(R.layout.view_progress_holder,
                null);

        // progress
        rlProgress = (ViewGroup) v.findViewById(R.id.rl_progress);
        progressIndicator = (ProgressBar) v.findViewById(R.id.progress_indicator);
        btnRequestRetry = (Button) v.findViewById(R.id.btn_request_retry);
        txtErrorMessage = (TextView) v.findViewById(R.id.txt_error_message);

        addView(v);
    }

    public void setRetryClickListener(OnClickListener onClickListener) {
        btnRequestRetry.setOnClickListener(onClickListener);
    }

    public void startProgress() {
        setVisibility(View.VISIBLE);
        rlProgress.setVisibility(View.VISIBLE);
        btnRequestRetry.setVisibility(View.GONE);
        txtErrorMessage.setVisibility(View.GONE);
    }

    public void stopAndGone() {
        setVisibility(View.GONE);
    }

    public void stopAndError(String errorMessage, boolean isRetry) {
        rlProgress.clearAnimation();
        progressIndicator.setVisibility(View.GONE);
        if (isRetry)
            btnRequestRetry.setVisibility(View.VISIBLE);
        else
            btnRequestRetry.setVisibility(View.GONE);
        txtErrorMessage.setVisibility(View.VISIBLE);
        txtErrorMessage.setText(errorMessage);
    }
}
