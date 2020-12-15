package com.example.stocksearch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.skyhope.showmoretextview.ShowMoreTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private static final int MAX_LINE_COUNT = 2;
    private RequestQueue requestQueue;
    private String ticker, name;
    private double sharesOwned, marketValue;
    private Description description;
    private List<News> news;
    private Price latestPrice;
    private List<String> portfolio;
    private List<String> watchlist;
    private boolean inWatchlist, inPortfolio;
    private Menu myMenu;
    private RecyclerView recyclerView;
    private NewsAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent myIntent = getIntent();
        ticker = myIntent.getStringExtra("ticker");
        initWebView();
        fetchPortfolioAndWatchlist();
        requestQueue = Volley.newRequestQueue(this);
//        Toast.makeText(this, ticker, Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.rvNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        myAdapter = new NewsAdapter(new ArrayList<News>());
        recyclerView.setAdapter(myAdapter);
        makeApiCalls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu_view, menu);
        myMenu = menu;
        initToolbar();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnWatchlist:
//                Toast.makeText(this, "toggle Watchlist", Toast.LENGTH_SHORT).show();
                toggleStar(item);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        savePortfolioAndWatchlist();
        super.onDestroy();
    }

    private void fetchPortfolioAndWatchlist() {
        SharedPreferences pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        Type type = new TypeToken<List<String>>(){}.getType();

        // fetch portfolio
        json = pref.getString("portfolio", "");
        portfolio = json.isEmpty() ? new ArrayList<String>() : gson.fromJson(json, type);
        if (portfolio.contains(getSaveString(ticker, name))) {
            inPortfolio = true;
        }

        // fetch watchlist
        json = pref.getString("watchlist", "");
        watchlist = json.isEmpty() ? new ArrayList<String>() : gson.fromJson(json, type);
        if (watchlist.contains(getSaveString(ticker, name))) {
            inWatchlist = true;
        }
    }

    private void savePortfolioAndWatchlist() {
        updatePortfolio();
        updateWatchlist();
    }

    private void updateWatchlist() {
        Gson gson = new Gson();
        SharedPreferences pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("watchlist", gson.toJson(watchlist));
        editor.commit();
    }

    private void toggleStar(@NonNull MenuItem item) {
        if (inWatchlist) {
            // turn it off
            item.setIcon(R.drawable.ic_baseline_star_border_24);
            Toast.makeText(this, "\"" + name + "\" removed from favorites.", Toast.LENGTH_SHORT).show();
            watchlist.remove(getSaveString(ticker, name));
            updateWatchlist();
            inWatchlist = false;
        }
        else {
            // turn it on
            item.setIcon(R.drawable.ic_baseline_star_24);
            Toast.makeText(this, "\"" + name + "\" added to favorites.", Toast.LENGTH_SHORT).show();
            watchlist.add(getSaveString(ticker, name));
            updateWatchlist();
            inWatchlist = true;
        }
    }

    private void updatePortfolio() {
        Gson gson = new Gson();
        SharedPreferences pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("portfolio", gson.toJson(portfolio));
        editor.commit();
    }

    private void makeApiCalls() {
        fetchDescription();
        fetchPrice();
        fetchNews();
    }

    private void fetchPrice() {
        String url = getResources().getString(R.string.latestPriceUrl) + ticker;
        JsonArrayRequest arReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject obj = response.getJSONObject(0);
                            latestPrice = new Price(obj);
//                            Log.e("price", latestPrice.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            populateGrid();
                            initHeaderPrice();
                            initPortfolio();
//                            setVisible();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(arReq);
    }

    private void fetchDescription() {
        Log.e("action", "fetching description");
        String url = getResources().getString(R.string.descriptionUrl) + ticker;
        JsonObjectRequest obReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        description = new Description(response);
//                        Log.e("description", description.toString());
                        name = description.getName();
                        updateStar();
                        initHeaderName();
                        initAbout();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(obReq);
    }

    private void fetchNews() {
        news = new ArrayList<News>();
        String url = getResources().getString(R.string.newsUrl) + ticker;
        JsonArrayRequest arReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                News newNews = new News(obj);
//                                Log.e("news", newNews.toString());
                                news.add(newNews);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            myAdapter.setData(news);
                            myAdapter.notifyDataSetChanged();
                            setVisible();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(arReq);
    }

    public void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_black);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        tv.setLayoutParams(lp);
        tv.setText("Stocks");
        tv.setTextSize(25);
        tv.setTextColor(Color.BLACK);
        tv.setTypeface(typeface);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
        actionBar.setElevation(0);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void updateStar() {
        if (watchlist.contains(getSaveString(ticker, name))) {
            inWatchlist = true;
            MenuItem star = myMenu.getItem(0);
            star.setIcon(R.drawable.ic_baseline_star_24);
        }
        else {
            inWatchlist = false;
            MenuItem star = myMenu.getItem(0);
            star.setIcon(R.drawable.ic_baseline_star_border_24);
        }
    }

    public void populateGrid() {
        DecimalFormat df = new DecimalFormat("0.00");
        // add current price
        TextView tvCurrentPrice = (TextView) findViewById(R.id.tvCurrentPrice);
        tvCurrentPrice.setText("Current Price: " + df.format(latestPrice.getLast()));
        // add low
        TextView tvLow = (TextView) findViewById(R.id.tvLow);
        tvLow.setText("Low: " + df.format(latestPrice.getLow()));
        // add bidPrice
        TextView tvBidPrice = (TextView) findViewById(R.id.tvBidPrice);
        tvBidPrice.setText("Bid Price: " + df.format(latestPrice.getBidPrice()));
        // add openPrice
        TextView tvOpenPrice = (TextView) findViewById(R.id.tvOpenPrice);
        tvOpenPrice.setText("Open Price: " + df.format(latestPrice.getOpen()));
        // add mid
        TextView tvMid = (TextView) findViewById(R.id.tvMid);
        tvMid.setText("Mid: " + df.format(latestPrice.getMid()));
        // add high
        TextView tvHigh = (TextView) findViewById(R.id.tvHigh);
        tvHigh.setText("High: " + df.format(latestPrice.getHigh()));
        // add volume
        TextView tvVolume = (TextView) findViewById(R.id.tvVolume);
        tvVolume.setText("Volume: " + latestPrice.getVolume());
    }

    public void initAbout() {
//        ShowMoreTextView tvAbout = findViewById(R.id.tvAbout);
//        tvAbout.setText(description.getDescription());
//        tvAbout.setShowingLine(MAX_LINE_COUNT);
//        tvAbout.addShowMoreText("Show more");
//        tvAbout.addShowLessText("Show less");
//        tvAbout.setShowMoreColor(Color.GRAY);
//        tvAbout.setShowLessTextColor(Color.GRAY);

//        ReadMoreTextView tvAbout = findViewById(R.id.tvAbout);
//        tvAbout.setText(description.getDescription());

        ExpandableTextView tvAbout = (ExpandableTextView) findViewById(R.id.tvAbout);
        tvAbout.setText(description.getDescription());
    }

    public void refreshPortfolio() {
        TextView tvShares = (TextView) findViewById(R.id.tvShares);
        TextView tvSharesActivity = (TextView) findViewById(R.id.tvSharesActivity);
        Button btnTrade = (Button) findViewById(R.id.btnTrade);

        SharedPreferences pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        DecimalFormat oneDf = new DecimalFormat("0.0"), twoDf = new DecimalFormat("0.00");

        if (pref.contains(ticker)) {
            // we have some stock
            sharesOwned = pref.getFloat(ticker, 0);
            marketValue = latestPrice.getLast() * sharesOwned;
            tvSharesActivity.setText("Market Value: $" + twoDf.format(marketValue).toString());
        }
        else {
            // we have no stock
            sharesOwned = 0;
            marketValue = 0;
            tvSharesActivity.setText("Start Trading!");
        }

        tvShares.setText("Shares Owned: " + oneDf.format(sharesOwned).toString());
    }

    public void initPortfolio() {
        TextView tvShares = (TextView) findViewById(R.id.tvShares);
        TextView tvSharesActivity = (TextView) findViewById(R.id.tvSharesActivity);
        Button btnTrade = (Button) findViewById(R.id.btnTrade);

        SharedPreferences pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        DecimalFormat oneDf = new DecimalFormat("0.0"), twoDf = new DecimalFormat("0.00");

        if (pref.contains(ticker)) {
            // we have some stock
            sharesOwned = pref.getFloat(ticker, 0);
            marketValue = latestPrice.getLast() * sharesOwned;
            tvSharesActivity.setText("Market Value: $" + twoDf.format(marketValue).toString());
        }
        else {
            // we have no stock
            sharesOwned = 0;
            marketValue = 0;
            tvSharesActivity.setText("Start Trading!");
        }

        tvShares.setText("Shares Owned: " + oneDf.format(sharesOwned).toString());

        //TODO: Set buttons to open dialog
        btnTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                float netWorth = pref.getFloat("netWorth", 20000);
                DecimalFormat oneDf = new DecimalFormat("0.0"), twoDf = new DecimalFormat("0.00");
                final Dialog dialog = new Dialog(DetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.trade_dialog);
                TextView tvTotal = (TextView) dialog.findViewById(R.id.tvTotal);
                tvTotal.setText("0.00 x $" + twoDf.format(latestPrice.getLast()).toString() + "/share = $0.0");
                TextView tvMoneyAvailable = (TextView) dialog.findViewById(R.id.tvMoneyAvailable);
                tvMoneyAvailable.setText("$" + twoDf.format(netWorth).toString() + " available to buy " + ticker);
                EditText editTxtShares = (EditText) dialog.findViewById(R.id.editTxtShares);
                editTxtShares.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        float amount = 0;
                        if (!s.toString().isEmpty()) {
                            amount = Float.parseFloat(s.toString());
                        }
                        tvTotal.setText(twoDf.format(amount).toString() + " x $" + twoDf.format(latestPrice.getLast()).toString() + "/share = $" + twoDf.format(latestPrice.getLast() * amount).toString());
                    }
                });

                Button btnBuy = (Button) dialog.findViewById(R.id.btnBuy);
                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTxtShares.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Please input a number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        float amount = Float.valueOf(editTxtShares.getText().toString());
                        String response = buy(amount);
                        if (response.equals("success")) {
                            refreshPortfolio();
                            final Dialog tradeDialog = new Dialog(DetailsActivity.this);
                            tradeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            tradeDialog.setContentView(R.layout.congratulations_dialog);
                            TextView tvTradeText = tradeDialog.findViewById(R.id.tvTradeText);
                            tvTradeText.setText("You have successfully bought " + twoDf.format(amount).toString() + " of " + ticker);
                            Button btnDone = (Button) tradeDialog.findViewById(R.id.btnDone);
                            btnDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tradeDialog.dismiss();
                                }
                            });
                            dialog.dismiss();
                            tradeDialog.show();
                        }
                        else {
                            Toast.makeText(DetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Button btnSell = (Button) dialog.findViewById(R.id.btnSell);
                btnSell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTxtShares.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Please input a number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        float amount = Float.valueOf(editTxtShares.getText().toString());
                        String response = sell(amount);
                        if (response.equals("success")) {
                            refreshPortfolio();
                            final Dialog tradeDialog = new Dialog(DetailsActivity.this);
                            tradeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            tradeDialog.setContentView(R.layout.congratulations_dialog);
                            TextView tvTradeText = tradeDialog.findViewById(R.id.tvTradeText);
                            tvTradeText.setText("You have successfully sold " + twoDf.format(amount).toString() + " of " + ticker);
                            Button btnDone = (Button) tradeDialog.findViewById(R.id.btnDone);
                            btnDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tradeDialog.dismiss();
                                }
                            });
                            dialog.dismiss();
                            tradeDialog.show();
                        }
                        else {
                            Toast.makeText(DetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    public void initHeaderPrice() {
        DecimalFormat df = new DecimalFormat("0.00");
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);
        TextView tvChange = (TextView) findViewById(R.id.tvChange);
        tvPrice.setText("$" + df.format(latestPrice.getLast()).toString());
        double change = latestPrice.getLast() - latestPrice.getPrevClose();
        String changeValue = change < 0 ? "-$" + df.format(Math.abs(change)).toString() : "$" + df.format(change).toString();
        tvChange.setText(changeValue);
        if (change < 0) {
            tvChange.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        else if (change > 0) {
            tvChange.setTextColor(ContextCompat.getColor(this, R.color.green));
        }
    }

    public void initHeaderName() {
        TextView tvTicker = (TextView) findViewById(R.id.tvTicker);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvTicker.setText(description.getTicker());
        tvName.setText(description.getName());
    }

    public String buy(double amount) {
        SharedPreferences pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        float totalCost = (float) (amount * latestPrice.getLast());
        float netWorth = pref.getFloat("netWorth", 20000);

        // check conditions
        if (totalCost > netWorth) {
            return "Not enough money to buy";
        }
        if (amount <= 0) {
            return "Cannot buy less than 0 shares";
        }


        if (!portfolio.contains(getSaveString(ticker, name))) {
            // not yet in portfolio
            portfolio.add(getSaveString(ticker, name));
        }
        float resultAmount = pref.getFloat(ticker, 0) + (float) (amount);
        editor.putFloat(ticker, resultAmount);

        editor.putFloat("netWorth", netWorth - totalCost);
        updatePortfolio();
        editor.commit();
        return "success";
    }

    public String sell(double amount) {
        SharedPreferences pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        float totalCost = (float) (amount * latestPrice.getLast());
        float netWorth = pref.getFloat("netWorth", 20000);
        float sharesOwned = pref.getFloat(ticker, 0);

        // checkConditions
        if (amount > sharesOwned) {
            return "Not enough shares to sell";
        }
        if (amount <= 0) {
            return "Cannot sell less than 0 shares";
        }

        float resultAmount = pref.getFloat(ticker, 0) - (float) (amount);
        if (resultAmount <= 0) {
            portfolio.remove(getSaveString(ticker, name));
            editor.remove(ticker);
        }
        else {
            editor.putFloat(ticker, resultAmount);
        }

        editor.putFloat("netWorth", totalCost + netWorth);
        updatePortfolio();
        editor.commit();
        return "success";
    }

    public String getSaveString(String ticker, String name) {
        return ticker + ".,." + name;
    }

    public void initWebView() {
        String url = "file:///android_asset/charts.html?ticker=" + ticker;
        WebView wvChart = (WebView) findViewById(R.id.wvChart);
        WebSettings settings = wvChart.getSettings();
        settings.setJavaScriptEnabled(true);
        wvChart.clearCache(true);
        settings.setDomStorageEnabled(true);
        wvChart.setWebViewClient(new WebViewClient());
        wvChart.loadUrl(url);
    }

    private void setVisible() {
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarDetails);
        pb.setVisibility(View.GONE);
        Group group = (Group) findViewById(R.id.groupDetails);
        group.setVisibility(View.VISIBLE);

//        TextView tv = (TextView) findViewById(R.id.expandable_text);
//        ImageButton iBtn = (ImageButton) findViewById(R.id.expand_collapse);
//
//        tv.setVisibility(View.VISIBLE);
//        iBtn.setVisibility(View.VISIBLE);
    }

}