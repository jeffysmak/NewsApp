package com.electrosoft.newsapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.adapters.NewsAdapter;
import com.electrosoft.newsapplication.api.GetDataService;
import com.electrosoft.newsapplication.api.RetrofitClientInstance;
import com.electrosoft.newsapplication.pojos.Article;
import com.electrosoft.newsapplication.pojos.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForYou extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private NewsAdapter newsAdapter;

    private List<Article> newsList;

    private int currentPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_for_you, container, false);

        recyclerView = v.findViewById(R.id.mHeadlinesRecycler);
        refreshLayout = v.findViewById(R.id.mHeadlinesSwipeRefresh);
        progressBar = v.findViewById(R.id.mProgressBar);
        newsList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        getTopHeadlinesNews();

        return v;
    }

    private void getTopHeadlinesNews() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<News> dataCall = service.getTopHeadlineNews();

        if (!dataCall.isExecuted()) {
            dataCall.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    progressBar.setVisibility(View.GONE);
                    generateNewsListItems(newsAdapter == null, response.body());
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.i("FUCK", t.getLocalizedMessage());
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void generateNewsListItems(boolean newInstance, News news) {
        newsList.addAll(news.getArticles());
        if (newInstance) {
            newsAdapter = new NewsAdapter(newsList, getActivity());
            recyclerView.setAdapter(newsAdapter);
            Toast.makeText(getActivity(), "Fetched -> " + newsList.size(), Toast.LENGTH_SHORT).show();
        } else {
            newsAdapter.setNewsList(newsList);
            Toast.makeText(getActivity(), "Fetched New -> " + newsList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        getTopHeadlinesNews();
    }
}
