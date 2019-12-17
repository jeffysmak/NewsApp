package com.electrosoft.newsapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.electrosoft.newsapplication.Common.Common;
import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.adapters.PagerAdapter;
import com.electrosoft.newsapplication.api.GetDataService;
import com.electrosoft.newsapplication.api.RetrofitClientInstance;
import com.electrosoft.newsapplication.pojos.Category;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SearchView searchView;
    private ProgressBar progressBar;
    List <Category> categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        MobileAds.initialize(this, Common.app_id);

        toolbar = findViewById(R.id.mToolbar);
        progressBar = findViewById(R.id.mProgressBar);
        viewPager = findViewById(R.id.mViewPager);
        tabLayout = findViewById(R.id.mTabBarLayout);
        searchView = findViewById(R.id.mSearchView);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(Dashboard.this,SearchActivity.class).putExtra("searchKey",query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        EditText txtSearch = ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(getResources().getString(R.string.search_hint));
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.WHITE);

        getCategories();

    }


    private void getCategories() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<Category>> dataCall = service.getCategories();

        dataCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                progressBar.setVisibility(View.GONE);
                categoryList = response.body();
                viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), categoryList, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
                tabLayout.setupWithViewPager(viewPager);
               // Log.d("ItIsNotComing",response.toString());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    public void openSearch(View view) {
        searchView.setIconified(false);
    }
}
