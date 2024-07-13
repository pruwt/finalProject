package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleDetailActivity extends AppCompatActivity {

    Button articlebackbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
//        articlebackbtn = findViewById(R.id.articlebackbtn);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        TextView titleTextView = findViewById(R.id.detail_title);
        TextView contentTextView = findViewById(R.id.detail_content);

        titleTextView.setText(title);
        contentTextView.setText(content);


//        articlebackbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ArticleDetailActivity.this, ArticleResource.class);
//                startActivity(intent);
//            }
//        });
    }
}
