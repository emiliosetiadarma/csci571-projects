package com.example.stocksearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class HomeFragment extends Fragment implements StockSection.ClickListener  {
    private RequestQueue requestQueue;
    private SectionedRecyclerViewAdapter sectionedAdapter;
    private Set<String> stockSet;
    private List<String> portfolio, watchlist;
    private boolean isLoading = true;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private int mInterval = 15000;
    private Handler handler;
    private Runnable intervalCall;
    private Map<String, List<Stock>> stockMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBarHome);
        initSections(view);
        requestQueue = Volley.newRequestQueue(this.getContext());

//        stockMap = testInitStockMap();

        enableSwipeToDelete();
        enableDragAndDrop();

        handler = new Handler();
//        startRepeatingTask();
        return view;
    }


    @Override
    public void onResume() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Log.e("onResume", "fragment resumed");
        super.onResume();
        stockMap = testInitStockMap();
        startRepeatingTask();
        sectionedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.e("onPause", "fragment paused");
        stopRepeatingTask();
        updateWatchlist();
        updatePortfolio();
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.e("onStop", "fragment stopped");
        stopRepeatingTask();
        updateWatchlist();
        updatePortfolio();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.e("onDestroyView", "fragment destroyed");
        stopRepeatingTask();
        updatePortfolio();
        updateWatchlist();
    }

    @Override
    public void onItemRootViewClicked(@NonNull final StockSection section, final int itemAdapterPosition) {
//        List<Stock> data = section.getData();
//        int index = sectionedAdapter.getPositionInSection(itemAdapterPosition);
//        Stock stock = data.get(index);
//        String ticker = stock.ticker;
//        Toast.makeText(getContext(), ticker + " clicked", Toast.LENGTH_SHORT).show();
////        Log.d("Click", "Clicked");
    }

    private Map<String, List<Stock>> testInitStockMap() {
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        Type type = new TypeToken<List<String>>(){}.getType();
        stockSet = new HashSet<>();
        Map<String, List<Stock>> stockMap = new HashMap<String, List<Stock>>();
//        String[] stockTickers = getResources().getStringArray(R.array.sample_stock_combo);
//        List<String> dummy = Arrays.asList(stockTickers);


        // fetch portfolio
        json = pref.getString("portfolio", "");
        portfolio = json.isEmpty() ? new ArrayList<String>() : gson.fromJson(json, type);
        Log.e("portfolio", portfolio.toString());
//        portfolio = json.isEmpty() ? new ArrayList<String>(dummy) : new ArrayList<String>(dummy);
        // fetch watchlist
        json = pref.getString("watchlist", "");
        watchlist = json.isEmpty() ? new ArrayList<String>() : gson.fromJson(json, type);
        Log.e("watchlist", watchlist.toString());
//        watchlist = json.isEmpty() ? new ArrayList<String>(dummy) : new ArrayList<String>(dummy);

        for (int i = 0; i < portfolio.size(); i++) {
            String[] arr = portfolio.get(i).split(".,.");
            String ticker = arr[0];
            stockSet.add(ticker);
        }
        for (int i = 0; i < watchlist.size(); i++) {
            String[] arr = watchlist.get(i).split(".,.");
            String ticker = arr[0];
            stockSet.add(ticker);
        }
        updateWatchlist();
        updatePortfolio();
        makeApiCall(stockMap, portfolio, watchlist);
        return stockMap;
    }

    private void makeApiCall (Map<String, List<Stock>> stockMap, List<String> portfolio, List<String> watchlist) {
        String allStocks = stockSet.isEmpty() ? "AAPL" : String.join(",", stockSet);
        String url = getResources().getString(R.string.latestPriceUrl) + allStocks;
        Map<String, JSONObject> priceMap = new HashMap<>();
        JsonArrayRequest arReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String ticker = obj.getString("ticker");
                                priceMap.put(ticker, obj);
                            }
                            // add portfolio
                            List<Stock> portfolioStock = new ArrayList<Stock>();
//                            Log.e("add portfolio", portfolio.toString());
                            for (String item: portfolio) {
                                String[] arr = item.split(".,.");
                                String ticker = arr[0];
                                String name = arr[1];
                                JSONObject obj = priceMap.get(ticker);
                                double price = obj.isNull("last") ? 0 : obj.getDouble("last");
                                double prevClose = obj.isNull("prevClose") ? 0 : obj.getDouble("prevClose");
                                double change = price - prevClose;
                                portfolioStock.add(new Stock(ticker, name, price, change));
                            }
                            // add watchlist
                            List<Stock> watchlistStock = new ArrayList<Stock>();
//                            Log.e("add Watchlist", watchlist.toString());
                            for (String item: watchlist) {
                                String[] arr = item.split(".,.");
                                String ticker = arr[0];
                                String name = arr[1];
                                JSONObject obj = priceMap.get(ticker);
                                double price = obj.isNull("last") ? 0 : obj.getDouble("last");
                                double prevClose = obj.isNull("prevClose") ? 0 : obj.getDouble("prevClose");
                                double change = price - prevClose;
                                watchlistStock.add(new Stock(ticker, name, price, change));
                            }

                            stockMap.put("Portfolio", portfolioStock);
                            stockMap.put("Favorites", watchlistStock);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            updateSections(stockMap);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        error.printStackTrace();
                        stockMap.put("Portfolio", new ArrayList<>());
                        stockMap.put("Favorites", new ArrayList<>());
                    }
                }
        );

        requestQueue.add(arReq);
    }

    private void initSections(View view) {
        sectionedAdapter = new SectionedRecyclerViewAdapter();
        recyclerView = view.findViewById(R.id.homerecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        recyclerView.setAdapter(sectionedAdapter);
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        float netWorth = pref.getFloat("netWorth", (float) 20000.00);
        editor.putFloat("netWorth", netWorth);
        editor.commit();
        sectionedAdapter.addSection("Portfolio", new StockSection("Portfolio", new ArrayList<Stock>(), HomeFragment.this, R.layout.section_home_portfolio_header, netWorth));
        sectionedAdapter.addSection("Favorites", new StockSection("Favorites", new ArrayList<Stock>(), HomeFragment.this, R.layout.section_home_header, netWorth));
    }
    private void updateSections(Map<String, List<Stock>> stockMap) {
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        float netWorth = pref.getFloat("netWorth", (float) 20000.00);
        editor.putFloat("netWorth", netWorth);
        editor.commit();
        for (Stock s: stockMap.get("Portfolio")) {
            //TODO:ADD PRICE OF EVERY STOCK TO NET WORTH
            netWorth += pref.getFloat(s.ticker, 0) * s.price;
//            Log.e("add to networth", netWorth + "");
        }
        for (Map.Entry<String, List<Stock>> entry: stockMap.entrySet()) {
            StockSection section = (StockSection) sectionedAdapter.getSection(entry.getKey());
            section.setData(entry.getValue());
            section.setNetWorth(netWorth);
        }
        isLoading = false;
        sectionedAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

    private void enableSwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this.getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (position <= portfolio.size() + 1) {
                    // if its in the portfolio section or its the watchlist header
                    sectionedAdapter.notifyDataSetChanged();
                    return;
                }
                // it is in the favorites section
                StockSection section = (StockSection) sectionedAdapter.getSectionForPosition(position);
                final int index = sectionedAdapter.getPositionInSection(position);
                List<Stock> data = section.getData();
                watchlist.remove(index);
                data.remove(index);
                updateWatchlist();
                sectionedAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    if (viewHolder instanceof ItemViewHolder) {
                        viewHolder.itemView.setBackgroundColor(Color.GRAY);
                    }
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (viewHolder instanceof ItemViewHolder) {
                    viewHolder.itemView.setBackgroundColor(Color.WHITE);
                }
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void updateWatchlist() {
        Gson gson = new Gson();
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("watchlist", gson.toJson(watchlist));
        editor.commit();
    }

    private void updatePortfolio() {
        Gson gson = new Gson();
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("portfolio", gson.toJson(portfolio));
        editor.commit();
    }

    private void startRepeatingTask() {
        intervalCall = new Runnable() {
            @Override
            public void run() {
                try {
                    makeApiCall(stockMap, portfolio, watchlist);
                }
                finally {
                    Log.e("Call", "15 SECONDS PASSED -- APICALL FETCH NEW DATA");
                    handler.postDelayed(intervalCall, mInterval);
                }
            }
        };

        intervalCall.run();
    }

    private void stopRepeatingTask() {
//        handler = new Handler();
//        handler.removeCallbacks(intervalCall);
        handler.removeCallbacksAndMessages(null);
    }

    private void enableDragAndDrop() {
        int dragDirs = ItemTouchHelper.UP | ItemTouchHelper.DOWN;// | ItemTouchHelper.START | ItemTouchHelper.END;
        ItemTouchHelper.Callback simpleCb = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof HeaderViewHolder) {
                    return makeMovementFlags(0, 0);
                }
                return makeMovementFlags(dragDirs, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                if (!(viewHolder instanceof ItemViewHolder)) {
                    return false;
                }
                if (!(target instanceof ItemViewHolder)) {
                    return false;
                }
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                int fromIndex = sectionedAdapter.getPositionInSection(fromPosition);
                int toIndex = sectionedAdapter.getPositionInSection(toPosition);

                StockSection fromSection = (StockSection) sectionedAdapter.getSectionForPosition(fromPosition);
                StockSection toSection = (StockSection) sectionedAdapter.getSectionForPosition(toPosition);

                if (fromSection == toSection) {
                    Collections.swap(fromSection.getData(), fromIndex, toIndex);
                    if (fromSection == sectionedAdapter.getSection("Portfolio")) {
                        // in portfolio
                        Collections.swap(portfolio, fromIndex, toIndex);
//                        Log.e("portfolio swap", portfolio.toString());
                    }
                    else {
                        // in watchlist
                        Collections.swap(watchlist, fromIndex, toIndex);
//                        Log.e("watchlist swap", watchlist.toString());
                    }
                    sectionedAdapter.notifyItemMoved(fromPosition, toPosition);
                }
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    if (viewHolder instanceof ItemViewHolder) {
                        viewHolder.itemView.setBackgroundColor(Color.GRAY);
                    }
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (viewHolder instanceof ItemViewHolder) {
                    viewHolder.itemView.setBackgroundColor(Color.WHITE);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCb);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
