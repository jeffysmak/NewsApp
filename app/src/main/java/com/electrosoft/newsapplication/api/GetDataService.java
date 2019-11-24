package com.electrosoft.newsapplication.api;

import com.electrosoft.newsapplication.Common.Common;
import com.electrosoft.newsapplication.pojos.Category;
import com.electrosoft.newsapplication.pojos.News;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET(Common.TOPHEADLINES + "?country=us&page=1&apiKey=" + Common.APIKEY)
    Call<News> getTopHeadlineNews();

    /*getAllLatestNews used for all latest news list*/
    @GET("wp-json/wp/v2/posts?_embed")
    Call<JsonElement> getAllLatestNews(@Query("categories") String catID, @Query("page") int per_page);

    @GET("wp-json/wp/v2/posts?status=publish&_embed")
    Call<JsonElement> getNewsByCatID(@Query("categories") String catID);


    /*search used for search all project content*/
    @GET("wp-json/wp/v2/posts/?_embed")
    Call<JsonElement> searchPosts(@Query("search") String world);

    /*category() used for fetch main category list*/
    @GET("wp-json/wp/v2/categories?per_page=100&_embed")
    Call<List<Category>> getCategories();

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
