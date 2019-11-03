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

    @GET(Common.TOPHEADLINES + "?country=us&category=business&page=1&apiKey=" + Common.APIKEY)
    Call<News> getBusinessNews();

    @GET(Common.TOPHEADLINES + "?country=us&category=entertainment&page=1&apiKey=" + Common.APIKEY)
    Call<News> getEntertainmentNews();

    @GET(Common.TOPHEADLINES + "?country=us&category=general&page=1&apiKey=" + Common.APIKEY)
    Call<News> getgeneralNews();

    @GET(Common.TOPHEADLINES + "?country=us&category=health&page=1&apiKey=" + Common.APIKEY)
    Call<News> getHealthNews();

    @GET(Common.TOPHEADLINES + "?country=us&category=science&page=1&apiKey=" + Common.APIKEY)
    Call<News> getScienceNews();

    @GET(Common.TOPHEADLINES + "?country=us&category=sports&page=1&apiKey=" + Common.APIKEY)
    Call<News> getSportsNews();

    @GET(Common.TOPHEADLINES + "?country=us&category=technology&page=1&apiKey=" + Common.APIKEY)
    Call<News> getTechnologyNews();
}
