package com.electrosoft.newsapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class SearchActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noNews;

    private NewsAdapter newsAdapter;

    private List<Post> newsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("searchKey"));
        newsList = new ArrayList<>();
        recyclerView = findViewById(R.id.mSearchRecycler);
        progressBar = findViewById(R.id.mProgressBar);
        noNews = findViewById(R.id.noNews);
        newsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        getSearchResults();
    }



    private void getSearchResults() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        Call<JsonElement> dataCall = service.searchPosts(getIntent().getStringExtra("searchKey"));

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
        newsAdapter = new NewsAdapter(newsList, this);
        recyclerView.setAdapter(newsAdapter);

    }


}
