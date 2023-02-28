package com.example.taazakhabar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailNews extends AppCompatActivity {

    private String heading, content, desc, imageURL, url;
    private TextView title, subDesc, fullcontent;
    private ImageView img;
    private Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        title  =findViewById(R.id.detailtxt);
        subDesc = findViewById(R.id.detailsubtxt);
        fullcontent = findViewById(R.id.detailcontenttxt);
        img = findViewById(R.id.detailimg);
        btn = findViewById(R.id.btn);

        heading = getIntent().getStringExtra("heading");
        content = getIntent().getStringExtra("content");
        desc = getIntent().getStringExtra("desc");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        title.setText(heading);
        subDesc.setText(desc);
        fullcontent.setText(content);
        Picasso.get().load(imageURL).into(img);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}