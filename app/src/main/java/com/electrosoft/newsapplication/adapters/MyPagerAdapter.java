package com.electrosoft.newsapplication.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.electrosoft.newsapplication.fragments.frag_sginup;
import com.electrosoft.newsapplication.fragments.frag_signin;


public class MyPagerAdapter extends FragmentPagerAdapter {


    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new frag_sginup();
        } else {
            return new frag_signin();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Signup";
        } else {
            return "Login";
        }
    }
}
