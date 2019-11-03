package com.electrosoft.newsapplication.api;

import com.electrosoft.newsapplication.Common.Common;
import com.electrosoft.newsapplication.pojos.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET(Common.TOPHEADLINES + "?country=us&page=1&apiKey=" + Common.APIKEY)
    Call<News> getTopHeadlineNews();
}
