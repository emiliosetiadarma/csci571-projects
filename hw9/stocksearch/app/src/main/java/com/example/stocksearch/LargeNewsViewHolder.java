package com.example.stocksearch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LargeNewsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvNewsTitle, tvNewsSource, tvNewsDate;
    private ImageView ivNews;
    public LargeNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNewsTitle = (TextView) itemView.findViewById(R.id.tvNewsTitle);
        tvNewsSource = (TextView) itemView.findViewById(R.id.tvNewsSource);
        tvNewsDate = (TextView) itemView.findViewById(R.id.tvNewsDate);
        ivNews = (ImageView) itemView.findViewById(R.id.ivNews);
    }

    public TextView getTvNewsTitle() {
        return tvNewsTitle;
    }

    public void setTvNewsTitle(TextView tvNewsTitle) {
        this.tvNewsTitle = tvNewsTitle;
    }

    public TextView getTvNewsSource() {
        return tvNewsSource;
    }

    public void setTvNewsSource(TextView tvNewsSource) {
        this.tvNewsSource = tvNewsSource;
    }

    public TextView getTvNewsDate() {
        return tvNewsDate;
    }

    public void setTvNewsDate(TextView tvNewsDate) {
        this.tvNewsDate = tvNewsDate;
    }

    public ImageView getIvNews() {
        return ivNews;
    }

    public void setIvNews(ImageView ivNews) {
        this.ivNews = ivNews;
    }
}
