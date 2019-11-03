package com.electrosoft.newsapplication.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.electrosoft.newsapplication.fragments.Business;
import com.electrosoft.newsapplication.fragments.Entertainment;
import com.electrosoft.newsapplication.fragments.ForYou;
import com.electrosoft.newsapplication.fragments.General;
import com.electrosoft.newsapplication.fragments.Health;
import com.electrosoft.newsapplication.fragments.Science;
import com.electrosoft.newsapplication.fragments.Sports;
import com.electrosoft.newsapplication.fragments.Technology;

public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ForYou.getInstance();
            case 1:
                return Business.getInstance();
            case 2:
                return Entertainment.getInstance();
            case 3:
                return General.getInstance();
            case 4:
                return Health.getInstance();
            case 5:
                return Science.getInstance();
            case 6:
                return Sports.getInstance();
            case 7:
                return Technology.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "For You";
            case 1:
                return "Business";
            case 2:
                return "Entertainment";
            case 3:
                return "General";
            case 4:
                return "Health";
            case 5:
                return "Science";
            case 6:
                return "Sports";
            case 7:
                return "Technology";
        }
        return null;
    }
}
