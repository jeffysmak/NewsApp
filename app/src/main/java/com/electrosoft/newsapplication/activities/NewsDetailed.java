package com.electrosoft.newsapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.pojos.Post;
import com.electrosoft.newsapplication.pojos.WebHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsDetailed extends AppCompatActivity {

    private Toolbar toolbar;
    private Post article;

    private TextView  dateTxt, title;
    private WebView description;
    private ImageView newsImage;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detailed);

        toolbar = findViewById(R.id.detailedViewToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.article = (Post) getIntent().getSerializableExtra("Extra_NewsArticle");

        setTitle(String.valueOf(Html.fromHtml(article.getTitle())));

        title = findViewById(R.id.newsTitle);
        description = findViewById(R.id.newsDetailedDescription);
        dateTxt = findViewById(R.id.newsDetailedDate);
        newsImage = findViewById(R.id.newsImage);
        Log.d("NewsDetails",String.valueOf(Html.fromHtml(article.getTitle())));

        title.setText(String.valueOf(Html.fromHtml(article.getTitle())).replace("\n",""));





        String[] a = article.getDate().split("T");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.US);
        try {
            Date date = simpleDateFormat.parse(a[0] + a[1]);
            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.US);
                dateTxt.setText(sdf.format(date));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("errordate", e.getLocalizedMessage());
        }


        description.getSettings().setJavaScriptEnabled(true);
        description.setBackgroundColor(Color.TRANSPARENT);
        description.getSettings().setDefaultFontSize(16);
        description.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        description.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        Document doc =  Jsoup.parse(article.getContent());
        if(article.getImage_url().equals(""))
        {
            newsImage.setVisibility(View.GONE);
        }
        else {
            try {
                doc.select("img[src]").remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Glide.with(this).load(article.getImage_url()).into(newsImage);
        }
        String html = WebHelper.docToBetterHTML(doc, NewsDetailed.this);
        String link = "" + article.getId() + "/" + title;
        description.loadDataWithBaseURL(link, html, "text/html", "utf-8", "");


      //  description.setText(Html.fromHtml(article.getContent()));
    }

    private String getSourceUrl(String url) throws URISyntaxException {
        URI uri = new URI(url);
        if (uri.getHost() != null) {
            Log.i("HostName ->", "logo.clearbit.com/" + uri.getHost());
            return "https://logo.clearbit.com/" + uri.getHost();
        }
        return "";
    }
}
