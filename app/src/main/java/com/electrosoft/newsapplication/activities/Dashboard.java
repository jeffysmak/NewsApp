package com.electrosoft.newsapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.electrosoft.newsapplication.Common.Common;
import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.adapters.PagerAdapter;
import com.electrosoft.newsapplication.api.GetDataService;
import com.electrosoft.newsapplication.api.RetrofitClientInstance;
import com.electrosoft.newsapplication.pojos.Category;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

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


    private DrawerLayout drawer;
    private NavigationView navigationView;

    private TextView userName, userEmail;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        MobileAds.initialize(this, Common.app_id);

        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.mToolbar);
        progressBar = findViewById(R.id.mProgressBar);
        viewPager = findViewById(R.id.mViewPager);
        tabLayout = findViewById(R.id.mTabBarLayout);
        searchView = findViewById(R.id.mSearchView);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        userName = headerView.findViewById(R.id.mUserName);
        userEmail = headerView.findViewById(R.id.mUserEmail);

        tabLayout.setupWithViewPager(viewPager);
        userName.setText(mAuth.getCurrentUser().getDisplayName());
        Log.d("Username",mAuth.getCurrentUser().getDisplayName());
        userEmail.setText(mAuth.getCurrentUser().getEmail());
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


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        EditText txtSearch = ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(getResources().getString(R.string.search_hint));
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.WHITE);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                AlertDialog dialog = new AlertDialog.Builder(Dashboard.this).create();
                drawer.closeDrawer(GravityCompat.START);
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        d.dismiss();
                    }
                });
                View dialogView;
                switch (menuItem.getItemId()) {

                    case R.id.nav_contact:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"contact@eyeopener.in"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from app!");
                        intent.putExtra(Intent.EXTRA_TEXT, "Help!");

                        try {
                            startActivity(Intent.createChooser(intent, "How to send mail?"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            //do something else
                        }
                        return true;

                    case R.id.nav_share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Hey ! check out the Latest News at: https://play.google.com/store/apps/details?id=" + getPackageName() );
                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, "Share to"));
                        return true;
                    case R.id.nav_review:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                        return true;
                    case R.id.nav_logout:
                        dialog.setTitle("Are you sure");
                        dialog.setMessage("are you sure you want to logout.");
                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {
                                d.dismiss();
                            }
                        });
                        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {
                                d.dismiss();
                                mAuth.signOut();
                                startActivity(new Intent(Dashboard.this, LoginActivity.class));
                                finish();
                            }
                        });
                        dialog.show();
                }
                return false;
            }
        });
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
