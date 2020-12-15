package com.example.stocksearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class StockSection extends Section {
    private String title;
    private List<Stock> list;
    private ClickListener clickListener;
    private float netWorth;

    public StockSection(@NonNull String title, @NonNull List<Stock> list, @NonNull ClickListener clickListener, @NonNull int header, @NonNull float netWorth) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_home_item)
                .headerResourceId(header)
                .build());
        this.title = title;
        this.list = list;
        this.clickListener = clickListener;
        this.netWorth = netWorth;
    }

    public List<Stock> getData() {
        return list;
    }

    public void setData(List<Stock> data) {
        list = data;
    }

    public void setNetWorth(float netWorth) {
        this.netWorth = netWorth;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        DecimalFormat oneDf = new DecimalFormat("0.0");
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        Stock stock = list.get(position);
        SharedPreferences pref = itemHolder.tvStockName.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        itemHolder.tvStockName.setText(stock.name);
        if (pref.contains(stock.ticker)) {
            float amount = pref.getFloat(stock.ticker, 0);
            itemHolder.tvStockName.setText(oneDf.format(amount) + " shares");
        }
        itemHolder.tvStockTicker.setText(stock.ticker);
        DecimalFormat df = new DecimalFormat("0.00");
        String changeValue = stock.change < 0 ? "-$" + df.format(Math.abs(stock.change)).toString() : "$" + df.format(stock.change).toString();
        String priceValue = "$" + df.format(stock.price).toString();
        if (stock.change < 0) {
            itemHolder.ivArrow.setImageResource(R.drawable.ic_baseline_trending_down_24);
            itemHolder.tvStockChange.setTextColor(ContextCompat.getColor(itemHolder.tvStockChange.getContext(), R.color.red));
        }
        else if (stock.change > 0) {
            itemHolder.ivArrow.setImageResource(R.drawable.ic_twotone_trending_up_24);
            itemHolder.tvStockChange.setTextColor(ContextCompat.getColor(itemHolder.tvStockChange.getContext(), R.color.green));
        }
        itemHolder.tvStockChange.setText(changeValue);
        itemHolder.tvStockPrice.setText(priceValue);
        itemHolder.rootView.setOnClickListener(v ->
                clickListener.onItemRootViewClicked(this, itemHolder.getAdapterPosition())
        );
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText(title);

        // fetch portfolio value and append it
        DecimalFormat df = new DecimalFormat("0.00");
        String netWorthValue = df.format(netWorth).toString();
        headerHolder.tvNetWorth.setText("Net Worth");
        headerHolder.tvNetWorthValue.setText(netWorthValue);
    }

    interface ClickListener {
        void onItemRootViewClicked(@NonNull final StockSection section, final int itemAdapterPosition);
    }
}
