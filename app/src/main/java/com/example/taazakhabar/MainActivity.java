package com.example.taazakhabar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {

    private RecyclerView NewsRV, categoryRV;
    private ProgressBar pgbar;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsRV = findViewById(R.id.recycle2);
        categoryRV = findViewById(R.id.recycle1);
        pgbar = findViewById(R.id.pgbar);

        articlesArrayList = new ArrayList<>();
        categoryModelArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(articlesArrayList, this);
        categoryAdapter = new CategoryAdapter(categoryModelArrayList, this, this::onCategoryClick);

        NewsRV.setAdapter(newsAdapter);
        categoryRV.setAdapter(categoryAdapter);

        getCategories();

        getNews("All");
        newsAdapter.notifyDataSetChanged();
    }

    private void getCategories(){
        categoryModelArrayList.add(new CategoryModel("All","https://images.unsplash.com/photo-1504711434969-e33886168f5c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoryModelArrayList.add(new CategoryModel("Technology","https://images.unsplash.com/photo-1531746790731-6c087fecd65a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=806&q=80"));
        categoryModelArrayList.add(new CategoryModel("Science","https://images.unsplash.com/photo-1554475900-0a0350e3fc7b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=417&q=80"));
        categoryModelArrayList.add(new CategoryModel("Sports","https://images.unsplash.com/photo-1594470117722-de4b9a02ebed?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1129&q=80"));
        categoryModelArrayList.add(new CategoryModel("Business","https://images.unsplash.com/photo-1556761175-5973dc0f32e7?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1032&q=80"));
        categoryModelArrayList.add(new CategoryModel("Entertainment","https://images.unsplash.com/photo-1496337589254-7e19d01cec44?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoryModelArrayList.add(new CategoryModel("Health","https://images.unsplash.com/photo-1506126613408-eca07ce68773?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=499&q=80"));
        categoryAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        pgbar.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String catgoryURL = "https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=4039cb8bf4dd4c428ba23d86fe4a5e89";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&apiKey=4039cb8bf4dd4c428ba23d86fe4a5e89";
        String baseURL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else{
            call = retrofitAPI.getAllNews(category);
        }
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                pgbar.setVisibility(View.GONE);
                ArrayList<Articles> articles = null;
                if (newsModel != null) {
                    articles = newsModel.getArticles();
                }
                for(int i = 0; i < articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get News...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position){
        String category = categoryModelArrayList.get(position).getCategory();
        getNews(category);
    }
}

//4039cb8bf4dd4c428ba23d86fe4a5e89