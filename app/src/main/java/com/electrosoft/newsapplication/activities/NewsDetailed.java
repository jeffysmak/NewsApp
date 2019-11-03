package com.electrosoft.newsapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.pojos.Article;

public class NewsDetailed extends AppCompatActivity {

    private Toolbar toolbar;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detailed);

        toolbar = findViewById(R.id.detailedViewToolbar);
        setSupportActionBar(toolbar);

        this.article = (Article) getIntent().getSerializableExtra("Extra_NewsArticle");

        setTitle(article.getTitle());
    }
}
