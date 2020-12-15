package com.example.stocksearch;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<News> news;
    private static final int LEAD = 0, DEFAULT = 1;

    public NewsAdapter(List<News> news) {
        this.news = news;
    }

    public void setData(List<News> news) {
        this.news = news;
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return LEAD;
        }
        else {
            return DEFAULT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case LEAD:
                View v1 = inflater.inflate(R.layout.layout_large_news_view_holder, parent, false);
                viewHolder = new NewsViewHolder(v1);
                break;
            case DEFAULT:
                View v2 = inflater.inflate(R.layout.layout_news_view_holder, parent, false);
                viewHolder = new NewsViewHolder(v2);
                break;
            default:
                return null;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder vh = (NewsViewHolder) holder;
        configureViewHolder(vh, position);
    }

    private void configureViewHolder(NewsViewHolder holder, int position) {
        News obj = news.get(position);
        Calendar today = Calendar.getInstance();

        // set source
        holder.getTvNewsSource().setText(obj.getSourceName());
        // set title
        holder.getTvNewsTitle().setText(obj.getTitle());
        // set image
        String imageUri = obj.getUrlToImage();
        ImageView ivNews = holder.getIvNews();

        if (imageUri.isEmpty()) {
            ivNews.setImageDrawable(ResourcesCompat.getDrawable(ivNews.getResources(),R.drawable.no_image,null));
        }
        else {
            Picasso.with(ivNews.getContext()).load(imageUri).error(ResourcesCompat.getDrawable(ivNews.getResources(),R.drawable.no_image,null)).fit().into(ivNews);
        }
        // set date
        long timeDays = TimeUnit.MILLISECONDS.toDays(today.getTimeInMillis() - obj.getTime().getTimeInMillis());
        holder.getTvNewsDate().setText(timeDays + " days ago");

        // set on click listener for dialog
        CardView newsCard = holder.getNewsCard();
        newsCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(newsCard.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.news_dialog);
                ImageView ivDialog = (ImageView) dialog.findViewById(R.id.ivDialog);
                TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
                tvDialogTitle.setText(obj.getTitle());
                String imageUri = obj.getUrlToImage();

                if (imageUri.isEmpty()) {
                    ivDialog.setImageDrawable(ResourcesCompat.getDrawable(ivDialog.getResources(),R.drawable.no_image,null));
                }
                else {
                    Picasso.with(ivDialog.getContext()).load(imageUri).error(ResourcesCompat.getDrawable(ivDialog.getResources(),R.drawable.no_image,null)).fit().into(ivDialog);
                }

                ImageButton btnTwitter = (ImageButton) dialog.findViewById(R.id.btnTwitter);
                ImageButton btnChrome = (ImageButton) dialog.findViewById(R.id.btnChrome);
                btnTwitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = "https://twitter.com/intent/tweet?text=Check out this Link:&url="+obj.getUrl();
                        Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        btnTwitter.getContext().startActivity(twitterIntent);
                    }
                });

                btnChrome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(obj.getUrl()));
                        btnChrome.getContext().startActivity(browserIntent);
                    }
                });
                dialog.show();
                return false;
            }
        });
        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(obj.getUrl()));
                v.getContext().startActivity(browserIntent);
            }
        });
    }
}
