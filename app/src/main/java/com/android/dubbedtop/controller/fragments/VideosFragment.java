package com.android.dubbedtop.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dubbedtop.R;
import com.android.dubbedtop.controller.adapter.VideosAdapter;
import com.android.dubbedtop.helpers.BaseFragment;
import com.android.dubbedtop.model.Search;
import com.android.dubbedtop.network.response.SearchResponse;
import com.android.dubbedtop.network.retrofit.ApiRequests;
import com.android.dubbedtop.network.retrofit.Results;
import com.mrq.library.Stateful.StatefulLayout;
import com.mrq.library.Toasty.Toasty;
import com.paginate.Paginate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.HttpException;

public class VideosFragment extends BaseFragment
        implements Paginate.Callbacks, SwipeRefreshLayout.OnRefreshListener {

    public VideosFragment() {
    }

    public static VideosFragment newInstance(int position) {
        VideosFragment fragment = new VideosFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
    }

    private SwipeRefreshLayout swipeToRefresh;
    private StatefulLayout statefulLayout;
    private RecyclerView recyclerView;

    private VideosAdapter adapter;
    private ArrayList<Search> list = new ArrayList<>();

    private boolean loadingInProgress = false;
    private boolean isPaginationFinished = true;
    private String token = "";
    private Paginate pager;
    private int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_videos, container, false);

        initView(root);

        return root;
    }

    private void initView(View root) {
        swipeToRefresh = root.findViewById(R.id.swipe_to_refresh);
        statefulLayout = root.findViewById(R.id.stateful_layout);
        recyclerView = root.findViewById(R.id.recycler_view);

        swipeToRefresh.setOnRefreshListener(this);

        adapter = new VideosAdapter(list, requireActivity());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(position);

        pager = Paginate.with(recyclerView, this)
                .setLoadingTriggerThreshold(3)
                .addLoadingListItem(false)
                .build();

        initSearchVideos(token);

    }

    private void initSearchVideos(String pageToken) {
        loadingInProgress = true;
        swipeToRefresh.setRefreshing(true);
        new ApiRequests().search(
                pageToken,
                requireActivity(),
                new Results<SearchResponse>() {
                    @Override
                    public void onSuccess(SearchResponse response) {
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
                            Log.e("statefulLayout", "size = " + list.size());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull String message) {
                        loadingInProgress = false;
                        swipeToRefresh.setRefreshing(false);
                        statefulLayout.showOffline(v -> initSearchVideos(pageToken));
                        Toasty.warning(requireContext(), message).show();
                    }

                    @Override
                    public void onException(@NotNull HttpException e) {
                        loadingInProgress = false;
                        swipeToRefresh.setRefreshing(false);
                        statefulLayout.showError(v -> initSearchVideos(pageToken));
                    }
                });
    }

    @Override
    public void onLoadMore() {
        if (!isPaginationFinished) {
            initSearchVideos(token);
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
        initSearchVideos(token);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(() -> false);

        super.onCreateOptionsMenu(menu, inflater);
    }

}