package com.electrosoft.newsapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.mToolbar);
        viewPager = findViewById(R.id.mViewPager);
        tabLayout = findViewById(R.id.mTabBarLayout);
        searchView = findViewById(R.id.mSearchView);

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        tabLayout.setupWithViewPager(viewPager);

    }

    public void openSearch(View view) {
        searchView.setIconified(false);
    }
}
