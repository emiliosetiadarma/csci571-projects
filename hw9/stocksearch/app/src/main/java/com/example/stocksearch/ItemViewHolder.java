package com.example.stocksearch;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public View rootView;
    public TextView tvStockTicker, tvStockName, tvStockPrice, tvStockChange;
    public ImageButton detailsBtn;
    public ImageView ivArrow;

    ItemViewHolder(@NonNull View view) {
        super(view);

        rootView = view;
        tvStockTicker = view.findViewById(R.id.tvStockTicker);
        tvStockName = view.findViewById(R.id.tvStockName);
        tvStockPrice = view.findViewById(R.id.tvStockPrice);
        tvStockChange = view.findViewById(R.id.tvStockChange);
        detailsBtn = (ImageButton) view.findViewById(R.id.detailsButton);
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add intent to go to details page here
                String query = tvStockTicker.getText().toString();
                Intent myIntent = new Intent(view.getContext(), DetailsActivity.class);
                myIntent.putExtra("ticker",query);
                view.getContext().startActivity(myIntent);
//                Toast.makeText(v.getContext(), tvStockTicker.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        ivArrow = (ImageView) view.findViewById(R.id.ivArrow);

    }
}
