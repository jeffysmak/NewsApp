package com.electrosoft.newsapplication.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.adapters.NewsAdapter;
import com.electrosoft.newsapplication.api.GetDataService;
import com.electrosoft.newsapplication.api.RetrofitClientInstance;
import com.electrosoft.newsapplication.pojos.Post;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class General extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noNews;

    private NewsAdapter newsAdapter;

    private List<Post> newsList;

    private int category_id;


    public General(int id) {

        category_id = id;
        newsList = new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_general, container, false);

        recyclerView = v.findViewById(R.id.mgeneralRecycler);
        refreshLayout = v.findViewById(R.id.mgeneralSwipeRefresh);
        progressBar = v.findViewById(R.id.mProgressBar);
        noNews = v.findViewById(R.id.noNews);
        newsList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        getTopHeadlinesNews();
        return v;
    }


    private void getTopHeadlinesNews() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        Log.d("NewsApp","Category: "+category_id);
        Call<JsonElement> dataCall = service.getNewsByCatID(String.valueOf(category_id));

        dataCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {
                    if (response.isSuccessful()) {

                        Log.d("NewsApp",response.body().toString());

                        newsList = new ArrayList<>();
                        progressBar.setVisibility(View.GONE);
                        JSONArray jsonArr = new JSONArray(response.body().toString());
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject mainObject = jsonArr.getJSONObject(i);
                            String id = mainObject.getString("id");
                            JSONObject objtitle = mainObject.getJSONObject("title");
                            String title = objtitle.getString("rendered");
                            String dateString = mainObject.getString("date");
                            JSONObject objContent = mainObject.getJSONObject("content");
                            String content = objContent.getString("rendered");
                            String img_url = mainObject.getString("jetpack_featured_media_url");
                            String link = mainObject.getString("link");

                            newsList.add(new Post(Integer.parseInt(id),title,dateString,img_url,content,link));

                        }


                    }
                    else
                    {

                        Log.d("NewsApp","Not Working bc");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                generateNewsListItems();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("LoginRes", t.toString());
            }
        });
    }

    private void generateNewsListItems() {
        if (newsList.size()==0)
            noNews.setVisibility(View.VISIBLE);
        else
            noNews.setVisibility(View.GONE);
        newsAdapter = new NewsAdapter(newsList, getActivity());
        recyclerView.setAdapter(newsAdapter);

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getTopHeadlinesNews();
    }
}
