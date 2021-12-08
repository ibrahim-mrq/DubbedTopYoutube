package com.android.dubbedtop.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.dubbedtop.R;
import com.android.dubbedtop.model.ListPlay;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    private Context mContext;
    private ArrayList<ListPlay> list;

    public PlayListAdapter(ArrayList<ListPlay> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_play_list, parent, false);
        return new PlayListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, final int position) {
        final ListPlay model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        ImageView customPlayListImage;
        TextView customPlayListTvCount;
        TextView customPlayListTvTitle;
        CardView parent;

        private PlayListViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.customPlayList_parent);
            customPlayListImage = itemView.findViewById(R.id.customPlayList_image);
            customPlayListTvCount = itemView.findViewById(R.id.customPlayList_tv_count);
            customPlayListTvTitle = itemView.findViewById(R.id.customPlayList_tv_title);
        }

        @SuppressLint("SetTextI18n")
        private void bind(ListPlay model) {

            Glide.with(mContext)
                    .load(model.getSnippet().getThumbnails().getMedium().getUrl())
                    .placeholder(R.drawable.logo).into(customPlayListImage);

            customPlayListTvCount.setText("" + model.getContentDetails().getItemCount());
            customPlayListTvTitle.setText("" + model.getSnippet().getTitle());

        }
    }

}

