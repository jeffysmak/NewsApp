package com.electrosoft.newsapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.pojos.Article;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsDetailed extends AppCompatActivity {

    private Toolbar toolbar;
    private Article article;

    private ImageView logo, image;
    private TextView source, description, dateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detailed);

        toolbar = findViewById(R.id.detailedViewToolbar);
        setSupportActionBar(toolbar);

        this.article = (Article) getIntent().getSerializableExtra("Extra_NewsArticle");

        setTitle(article.getTitle());

        logo = findViewById(R.id.newsDetailedThumbnail);
        source = findViewById(R.id.newsDetailedSource);
        image = findViewById(R.id.newsDetailedImage);
        description = findViewById(R.id.newsDetailedDescription);
        dateTxt = findViewById(R.id.newsDetailedDate);

        Glide.with(getApplicationContext()).load(article.getUrlToImage()).into(image);
        try {
            Glide.with(getApplicationContext()).load(getSourceUrl(article.getUrl())).into(logo);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String[] a = article.getPublishedAt().split("T");
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

        description.setText(article.getDescription());
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
