package com.electrosoft.newsapplication.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.electrosoft.newsapplication.fragments.General;
import com.electrosoft.newsapplication.pojos.Category;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    List<Category> categoryList;

    public PagerAdapter(@NonNull FragmentManager fm, List<Category> categoryList, int behavior) {
        super(fm, behavior);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

       return new General(categoryList.get(position).getId());
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return categoryList.get(position).getName().replace("&amp;","&");
    }
}
