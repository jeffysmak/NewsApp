package com.electrosoft.newsapplication.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.adapters.CricketAdapter;
import com.electrosoft.newsapplication.adapters.NewsAdapter;
import com.electrosoft.newsapplication.api.GetDataService;
import com.electrosoft.newsapplication.api.RetrofitClientInstance;
import com.electrosoft.newsapplication.pojos.Constants;
import com.electrosoft.newsapplication.pojos.Post;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class General extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noNews;

    private NewsAdapter newsAdapter;

    private List<Post> newsList;

    private int category_id;
    private String category_name;
    public ArrayList<Constants> cricketList;
    private Constants constants;
    private CricketAdapter cricketAdapter;

    ListView cricketListView;


    public General(int id,String name) {

        category_id = id;
        category_name = name;
        newsList = new ArrayList<>();
        cricketList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_general, container, false);

        recyclerView = v.findViewById(R.id.mgeneralRecycler);
        refreshLayout = v.findViewById(R.id.mgeneralSwipeRefresh);
        progressBar = v.findViewById(R.id.mProgressBar);
        noNews = v.findViewById(R.id.noNews);
         cricketListView = v.findViewById(R.id.cricketList);
        // listView.setAdapter(listAdapter);
        newsList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        checkTitle();
        return v;
    }

    private void checkTitle() {
        if(category_name.equalsIgnoreCase("cricket")) {
            getCricketDetails();
        }
        else {
            getTopHeadlinesNews();
        }

        cricketListView.setVisibility(View.GONE);
    }

    private void getCricketDetails() {

        GetDataService service = new retrofit2.Retrofit.Builder()
                .baseUrl("http://ams.mapps.cricbuzz.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GetDataService.class);

        Call<JsonElement> dataCall = service.getCricket();
        dataCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("Cricket",response.body().toString());

                cricketList = new ArrayList<>();
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject header = null;
                    JSONArray jsonArray = new JSONArray(response.body().toString());
                    int numberOfItemsInResp = jsonArray.length();
                    for (int i = 0; i < numberOfItemsInResp; i++) {
                        constants = new Constants();
                        JSONObject match = jsonArray.getJSONObject(i);
                        constants.matchId = match.getString("matchId");
                        String srsid = match.getString("srsid");
                        constants.srs = match.getString("srs");
                        constants.datapath = match.getString("datapath");
                        if (match.has("header")) {
                            header = match.getJSONObject("header");
                            String start_time = header.getString("start_time");
                            String end_time = header.getString("end_time");
                            String startdte = header.getString("startdt");
                            constants.stTme = header.getString("stTme");
                            String stTmeGMT = header.getString("stTmeGMT");
                            String enddt = header.getString("enddt");
                            constants.mnum = header.getString("mnum");
                            constants.type = header.getString("type");
                            constants.mchDesc = header.getString("mchDesc");
                            constants.mchState = header.getString("mchState");
                            constants.TW = header.getString("TW");
                            constants.decisn = header.getString("decisn");
                            String vzone = header.getString("vzone");
                            constants.vcity = header.getString("vcity");
                            constants.vcountry = header.getString("vcountry");
                            constants.status = header.getString("status");
                            String addnStatus = header.getString("addnStatus");
                            String MOM = header.getString("MOM");
                            String NoOfIngs = header.getString("NoOfIngs");
                            //Toast.makeText(MainActivity.this, constants.TW+constants.decisn, Toast.LENGTH_SHORT).show();
                        }
                        if (match.has("miniscore")) {
                            JSONObject miniscore = match.getJSONObject("miniscore");
                            constants.batteamid = miniscore.getString("batteamid");
                            constants.batteamscore = miniscore.getString("batteamscore");
                            constants.bowlteamid = miniscore.getString("bowlteamid");
                            constants.bowlteamscore = miniscore.getString("bowlteamscore");
                            constants.overs = miniscore.getString("overs");
                            String bowlteamovers = miniscore.getString("bowlteamovers");
                            String rrr = miniscore.getString("rrr");
                            String crr = miniscore.getString("crr");
                            String cprtshp = miniscore.getString("cprtshp");
                            String prevOvers = miniscore.getString("prevOvers");
                            String lWkt = miniscore.getString("lWkt");
                            String oversleft = miniscore.getString("oversleft");

                            JSONObject striker = miniscore.getJSONObject("striker");
                            String fullName = striker.getString("fullName");
                            constants.runs = striker.getString("runs");
                            String balls = striker.getString("balls");
                            String fours = striker.getString("fours");
                            String sixes = striker.getString("sixes");
                            JSONObject nonStriker = miniscore.getJSONObject("nonStriker");
                            String nonStriker_fullName = nonStriker.getString("fullName");
                            String nonStriker_runs = nonStriker.getString("runs");
                            String nonStriker_balls = nonStriker.getString("balls");
                            String nonStriker_fours = nonStriker.getString("fours");
                            String nonStriker_sixes = nonStriker.getString("sixes");
                            JSONObject bowler = miniscore.getJSONObject("bowler");
                            String bowler_fullName = bowler.getString("fullName");
                            String bowler_overs = bowler.getString("overs");
                            String bowler_maidens = bowler.getString("maidens");
                            String bowler_runs = bowler.getString("runs");
                            String bowler_wicket = bowler.getString("wicket");
                            JSONObject nsbowler = miniscore.getJSONObject("nsbowler");
                            String nsbowler_fullName = nsbowler.getString("fullName");
                            String nsbowler_overs = nsbowler.getString("overs");
                            String nsbowler_maidens = nsbowler.getString("maidens");
                            String nsbowler_runs = nsbowler.getString("runs");
                            String nsbowler_wicket = nsbowler.getString("wicket");
                        }
                        if (match.has("team1")) {
                            JSONObject team1 = match.getJSONObject("team1");
                            constants.team1_id = team1.getString("id");
                            String team1_name = team1.getString("name");
                            constants.team1_fName = team1.getString("fName");
                            constants.team1_sName = team1.getString("sName");
                            constants.flag = team1.getString("flag");

                        }
                        if (match.has("team2")) {
                            JSONObject team2 = match.getJSONObject("team2");
                            constants.team2_id = team2.getString("id");
                            String team2_name = team2.getString("name");
                            constants.team2_fName = team2.getString("fName");
                            constants.team2_sName = team2.getString("sName");
                            constants.team2_flag = team2.getString("flag");

                        }
                        if (match.has("valueAdd")) {
                            JSONObject valueAdd = match.getJSONObject("valueAdd");
                            JSONObject alerts = valueAdd.getJSONObject("alerts");
                            String enabled = alerts.getString("enabled");
                            String alerts_type = alerts.getString("type");
                        }

                        cricketList.add(constants);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                generateNewsListItems();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


    private void getTopHeadlinesNews() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        Log.d("NewsApp","Category: "+category_id);
        Call<JsonElement> dataCall = service.getNewsByCatID(String.valueOf(category_id));

        dataCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {
                    if (response.isSuccessful()) {


                        newsList = new ArrayList<>();
                        progressBar.setVisibility(View.GONE);
                        JSONArray jsonArr = new JSONArray(response.body().toString());
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject mainObject = jsonArr.getJSONObject(i);
                            String id = mainObject.getString("id");
                            JSONObject objtitle = mainObject.getJSONObject("title");
                            String title = objtitle.getString("rendered");
                            String dateString = mainObject.getString("date");
                            JSONObject objContent = mainObject.getJSONObject("content");
                            String content = objContent.getString("rendered");
                            String img_url = mainObject.getString("jetpack_featured_media_url");
                            String link = mainObject.getString("link");

                            newsList.add(new Post(Integer.parseInt(id),title,dateString,img_url,content,link));

                        }


                    }
                    else
                    {

                        Log.d("NewsApp","Not Working bc");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                generateNewsListItems();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("LoginRes", t.toString());
            }
        });
    }

    private void generateNewsListItems() {
        if(category_name.equalsIgnoreCase("cricket"))
        {
            if(cricketList.size()==0)
            {
                noNews.setVisibility(View.VISIBLE);
            }
            else
                noNews.setVisibility(View.GONE);
            cricketAdapter = new CricketAdapter( getActivity(),cricketList);
            recyclerView.setAdapter(cricketAdapter);
        }
        else {
            if (newsList.size() == 0)
                noNews.setVisibility(View.VISIBLE);
            else
                noNews.setVisibility(View.GONE);
            newsAdapter = new NewsAdapter(newsList, getActivity());
            recyclerView.setAdapter(newsAdapter);
        }

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        checkTitle();
    }
}
