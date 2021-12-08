package com.android.dubbedtop.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.dubbedtop.R;
import com.android.dubbedtop.helpers.Constants;
import com.android.dubbedtop.helpers.TimeAgo;
import com.android.dubbedtop.model.*;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder>
        implements Filterable {

    private Context mContext;
    private ArrayList<Search> list;
    private ArrayList<Search> exampleListFull;

    public VideosAdapter(ArrayList<Search> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        exampleListFull = list;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_video, parent, false);
        return new VideosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, final int position) {
        final Search model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder {

        ImageView customVideoImage;
        TextView customVideoTvTime;
        TextView customVideoTvTitle;
        TextView customVideoTvDate;

        private VideosViewHolder(@NonNull View itemView) {
            super(itemView);

            customVideoImage = itemView.findViewById(R.id.customVideo_image);
            customVideoTvTitle = itemView.findViewById(R.id.customVideo_tv_title);
            customVideoTvDate = itemView.findViewById(R.id.customVideo_tv_date);
            customVideoTvTime = itemView.findViewById(R.id.customVideo_tv_time);
        }

        @SuppressLint("SetTextI18n")
        private void bind(Search model) {

            Glide.with(mContext)
                    .load(model.getSnippet().getThumbnails().getMedium().getUrl())
                    .placeholder(R.drawable.logo).into(customVideoImage);

            customVideoTvTitle.setText("" + model.getSnippet().getTitle());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            Date parsedDate = null;
            try {
                parsedDate = inputFormat.parse(model.getSnippet().getPublishedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeAgo timeAgo = new TimeAgo(mContext);
            customVideoTvDate.setText("" + timeAgo.timeAgo(parsedDate.getTime()));
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Search> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Search item : exampleListFull) {
                    if (item.getSnippet().getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<Search>) results.values;
            notifyDataSetChanged();
        }
    };

}

