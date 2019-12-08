package com.electrosoft.newsapplication.api;

import com.electrosoft.newsapplication.pojos.Category;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    /*category() used for fetch main category list*/
    @GET("wp-json/wp/v2/categories?per_page=100&_embed")
    Call<List<Category>> getCategories();

    //get posts by category id
    @GET("wp-json/wp/v2/posts?status=publish&_embed")
    Call<JsonElement> getNewsByCatID(@Query("categories") String catID);


    /*search used for search all project content*/
    @GET("wp-json/wp/v2/posts/?_embed")
    Call<JsonElement> searchPosts(@Query("search") String world);


    @GET("cbzandroid/2.0/currentmatches.json")
    Call<JsonElement> getCricket();



}
