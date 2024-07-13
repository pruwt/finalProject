package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ArticleResource extends AppCompatActivity {
    private ListView listView;
    private List<Article> articleList;
    private ArticleAdapter articleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_resource);

        listView = findViewById(R.id.list_view);

        articleList = new ArrayList<>();
        // Add sample articles to the list
        articleList.add(new Article("The Evolution of Smart Irrigation: A Game Changer for Modern Agriculture", "Smart irrigation systems represent a significant leap forward in agricultural practices, bringing about unprecedented efficiency and yield improvements. These advanced technologies use real-time data, sensors, and automated controls to optimize water usage, ensuring that crops receive the precise amount of water they need. This not only conserves water but also enhances crop health and productivity. As farmers embrace these innovative systems, the agricultural landscape is undergoing a profound transformation, marked by increased sustainability and profitability. From small-scale farms to large agricultural enterprises, smart irrigation is proving to be a pivotal tool in the quest for higher efficiency and yields.", R.drawable.smartirrigation));
        articleList.add(new Article("How Smart Irrigation is Helping Farmers in Water-Scarce Regions", "In regions where water scarcity is a critical issue, smart irrigation systems provide a lifeline for farmers. By leveraging advanced technologies such as soil moisture sensors, weather forecasts, and automated irrigation schedules, these systems ensure that every drop of water is used effectively. This targeted approach not only conserves precious water resources but also improves crop resilience and yields in challenging environments. As climate change continues to exacerbate water shortages, smart irrigation stands out as a vital solution for sustainable agriculture, helping farmers thrive even in the most arid conditions.", R.drawable.smartirrigation));
        articleList.add(new Article("How FarmFlow Smart Irrigation System is Revolutionizing How Farmers Manage Water", "The FarmFlow smart irrigation system is revolutionizing the way farmers manage their water resources, driving significant improvements in crop yields and sustainability. By utilizing cutting-edge sensors and automated controls, FarmFlow ensures optimal water delivery tailored to the specific needs of each crop. This precision irrigation approach minimizes water waste, reduces energy consumption, and promotes healthier, more productive crops. Farmers using FarmFlow benefit from increased efficiency, lower operational costs, and a smaller environmental footprint. As a leader in smart irrigation technology, FarmFlow is setting new standards in agricultural innovation, empowering farmers to achieve remarkable results in water management and crop production", R.drawable.smartirrigation));
//        articleList.add(new Article("Article 4", "Content of Article 4", R.drawable.smartirrigation));

        articleAdapter = new ArticleAdapter(this, articleList);
        listView.setAdapter(articleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = articleList.get(position);
                Intent intent = new Intent(ArticleResource.this, ArticleDetailActivity.class);
                intent.putExtra("title", article.getTitle());
                intent.putExtra("content", article.getContent());
                startActivity(intent);
            }
        });
    }
}