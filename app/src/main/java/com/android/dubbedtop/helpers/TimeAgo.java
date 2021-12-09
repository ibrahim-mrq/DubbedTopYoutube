package com.android.dubbedtop.helpers;

import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import com.android.dubbedtop.R;

@SuppressWarnings("ALL")
public class TimeAgo {

    protected Context context;

    public TimeAgo(Context context) {
        this.context = context;
    }

    public String timeAgo(Date date) {
        return timeAgo(date.getTime());
    }

    @SuppressLint("StringFormatInvalid")
    public String timeAgo(long millis) {
        long diff = new Date().getTime() - millis;

        Resources r = context.getResources();

        String prefix = r.getString(R.string.time_ago_prefix);
//        String suffix = r.getString(R.string.time_ago_suffix);
        String suffix = "";

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String words = "";

        if (seconds < 45) {
            words = Math.round(seconds) + " " + r.getString(R.string.time_ago_seconds);
        } else if (seconds < 90) {
            words = 1 + " " + r.getString(R.string.time_ago_minute);
        } else if (minutes < 45) {
            words = Math.round(minutes) + " " + r.getString(R.string.time_ago_minutes);
        } else if (minutes < 90) {
            words = 1 + " " + r.getString(R.string.time_ago_hour);
        } else if (hours < 24) {
            words = Math.round(hours) + " " + r.getString(R.string.time_ago_hours);
        } else if (hours < 42) {
            words = 1 + " " + r.getString(R.string.time_ago_day);
        } else if (days < 30) {
            words = Math.round(days) + " " + r.getString(R.string.time_ago_days);
        } else if (days < 45) {
            words = 1 + " " + r.getString(R.string.time_ago_month);
        } else if (days < 365) {
            words = Math.round(days / 30) + " " + r.getString(R.string.time_ago_months);
        } else if (years < 1.5) {
            words = 1 + " " + r.getString(R.string.time_ago_year);
        } else {
            words = Math.round(years) + " " + r.getString(R.string.time_ago_years);
        }
        StringBuilder sb = new StringBuilder();
        if (prefix != null && prefix.length() > 0) {
            sb.append(prefix).append(" ");
        }
        sb.append(words);
        if (suffix != null && suffix.length() > 0) {
            sb.append(" ").append(suffix);
        }
        return sb.toString().trim();
    }
}