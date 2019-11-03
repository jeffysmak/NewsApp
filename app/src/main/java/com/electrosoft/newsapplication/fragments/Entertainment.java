package com.electrosoft.newsapplication.fragments;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class Entertainment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static Entertainment Instance;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private NewsAdapter newsAdapter;

    private List<Article> newsList;

    private int currentPage = 1;

    public static Entertainment getInstance() {
        if (Instance == null) {
            Instance = new Entertainment();
        }
        return Instance;
    }

    public Entertainment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_entertainment, container, false);

        recyclerView = v.findViewById(R.id.mEntertainmentRecycler);
        refreshLayout = v.findViewById(R.id.mEntertainmentSwipeRefresh);
        progressBar = v.findViewById(R.id.mProgressBar);
        newsList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        getTopHeadlinesNews();

        return v;
    }

    private void getTopHeadlinesNews() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<News> dataCall = service.getEntertainmentNews();

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

    private void generateNewsListItems(boolean newInstance, News news) {
        newsAdapter = new NewsAdapter(news.getArticles(), getActivity());
        recyclerView.setAdapter(newsAdapter);
//        if (newInstance) {
//            newsList.addAll(news.getArticles());
//            newsAdapter = new NewsAdapter(newsList, getActivity());
//            recyclerView.setAdapter(newsAdapter);
//            Toast.makeText(getActivity(), "Fetched -> " + newsList.size(), Toast.LENGTH_SHORT).show();
//        } else {
//            int i = 0;
//            for (Article a : news.getArticles()) {
//                if (!newsList.contains(a) && newsList.indexOf(a) != -1) {
//                    newsList.add(a);
//                    newsAdapter.notifyDataSetChanged();
//                    i++;
//                }
//            }
//            Toast.makeText(getActivity(), "Fetched New -> " + i, Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onRefresh() {
        getTopHeadlinesNews();
    }

}
