package com.example.stocksearch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle, tvNetWorth, tvNetWorthValue, tvTodayDate;
    private Calendar today;

    HeaderViewHolder(@NonNull View view) {
        super(view);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvNetWorth = view.findViewById(R.id.tvNetWorth);
        tvNetWorthValue = view.findViewById(R.id.tvNetWorthValue);
        tvTodayDate = view.findViewById(R.id.tvTodayDate);
        today = Calendar.getInstance();
        tvTodayDate.setText(getTodayDateString());
    }

    private String getTodayDateString() {
        String result;
        int month = today.get(Calendar.MONTH) + 1;
        switch (month) {
            case 1:
                result = "January";
                break;
            case 2:
                result = "February";
                break;
            case 3:
                result = "March";
                break;
            case 4:
                result = "April";
                break;
            case 5:
                result = "May";
                break;
            case 6:
                result = "June";
                break;
            case 7:
                result = "July";
                break;
            case 8:
                result = "August";
                break;
            case 9:
                result = "September";
                break;
            case 10:
                result = "October";
                break;
            case 11:
                result = "November";
                break;
            case 12:
                result = "December";
                break;
            default:
                result = "";
                break;
        }
        result += " " + today.get(Calendar.DATE) + ", " + today.get(Calendar.YEAR);

        return result;
    }
}
