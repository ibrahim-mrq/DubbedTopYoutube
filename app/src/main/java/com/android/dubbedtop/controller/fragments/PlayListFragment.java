package com.android.dubbedtop.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.dubbedtop.R;
import com.android.dubbedtop.controller.adapter.PlayListAdapter;
import com.android.dubbedtop.helpers.BaseFragment;
import com.android.dubbedtop.model.ListPlay;
import com.android.dubbedtop.network.response.PlayListResponse;
import com.android.dubbedtop.network.retrofit.ApiRequests;
import com.android.dubbedtop.network.retrofit.Results;
import com.mrq.library.Stateful.StatefulLayout;
import com.paginate.Paginate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.HttpException;

public class PlayListFragment extends BaseFragment
        implements Paginate.Callbacks, SwipeRefreshLayout.OnRefreshListener {

    public PlayListFragment() {
    }

    public static PlayListFragment newInstance() {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private SwipeRefreshLayout swipeToRefresh;
    private StatefulLayout statefulLayout;
    public RecyclerView recyclerView;

    public PlayListAdapter adapter;
    private ArrayList<ListPlay> list = new ArrayList<>();

    private boolean loadingInProgress = false;
    private boolean isPaginationFinished = true;
    private String token = "";
    private Paginate pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_play_list, container, false);

        initView(root);

        return root;
    }

    private void initView(View root) {
        swipeToRefresh = root.findViewById(R.id.swipe_to_refresh);
        statefulLayout = root.findViewById(R.id.stateful_layout);
        recyclerView = root.findViewById(R.id.recycler_view);

        swipeToRefresh.setOnRefreshListener(this);

        adapter = new PlayListAdapter(list, requireActivity());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        pager = Paginate.with(recyclerView, this)
                .setLoadingTriggerThreshold(3)
                .addLoadingListItem(false)
                .build();

        initPlayList(token);

    }

    private void initPlayList(String pageToken) {
        loadingInProgress = true;
        swipeToRefresh.setRefreshing(true);
        new ApiRequests().playList(
                pageToken,
                requireActivity(),
                new Results<PlayListResponse>() {
                    @Override
                    public void onSuccess(PlayListResponse response) {
                        loadingInProgress = false;
                        swipeToRefresh.setRefreshing(false);
                        if (token.equals("")) {
                            list.clear();
                        }
                        if (response.getNextPageToken() != null) {
                            token = response.getNextPageToken();
                            pager.setHasMoreDataToLoad(true);
                            isPaginationFinished = false;
                        } else {
                            loadingInProgress = false;
                            pager.setHasMoreDataToLoad(false);
                            isPaginationFinished = true;
                        }
                        list.addAll(response.getItems());
                        if (list.isEmpty())
                            statefulLayout.showEmpty();
                        else {
                            statefulLayout.showContent();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull String message) {
                        loadingInProgress = false;
                        swipeToRefresh.setRefreshing(false);
                        statefulLayout.showOffline(v -> initPlayList(pageToken));
                    }

                    @Override
                    public void onException(@NotNull HttpException e) {
                        loadingInProgress = false;
                        swipeToRefresh.setRefreshing(false);
                        statefulLayout.showError(v -> initPlayList(pageToken));
                    }
                });
    }

    @Override
    public void onLoadMore() {
        if (!isPaginationFinished) {
            initPlayList(token);
        }
    }

    @Override
    public boolean isLoading() {
        return loadingInProgress;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return isPaginationFinished;
    }

    @Override
    public void onRefresh() {
        token = "";
        initPlayList(token);
    }

}