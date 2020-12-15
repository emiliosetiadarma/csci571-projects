package com.example.stocksearch;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    private TextView tvNewsTitle, tvNewsSource, tvNewsDate;
    private ImageView ivNews;
    private CardView newsCard;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNewsTitle = (TextView) itemView.findViewById(R.id.tvNewsTitle);
        tvNewsSource = (TextView) itemView.findViewById(R.id.tvNewsSource);
        tvNewsDate = (TextView) itemView.findViewById(R.id.tvNewsDate);
        ivNews = (ImageView) itemView.findViewById(R.id.ivNews);
        newsCard = (CardView) itemView.findViewById(R.id.newsCard);
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

    public CardView getNewsCard() {
        return newsCard;
    }

    public void setNewsCard(CardView newsCard) {
        this.newsCard = newsCard;
    }
}
