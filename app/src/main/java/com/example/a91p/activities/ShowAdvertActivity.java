package com.example.a91p.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a91p.AdvertAdapter;
import com.example.a91p.R;
import com.example.a91p.data.DatabaseHelper;
import com.example.a91p.model.Advert;

import java.util.ArrayList;

public class ShowAdvertActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView advertListView;
    ArrayList advertArrayList;
    AdvertAdapter advertArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_advert);

        dbHelper = new DatabaseHelper(this);

        advertListView = findViewById(R.id.advertListView);
        advertArrayList = dbHelper.GetAdverts();
        advertArrayAdapter = new AdvertAdapter(this, advertArrayList);
        advertListView.setAdapter(advertArrayAdapter);

        advertListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Advert advert = advertArrayAdapter.getItem(i);

                Intent showAdvertIntent = new Intent(ShowAdvertActivity.this, RemoveAdvertActivity.class);

                showAdvertIntent.putExtra("advertId", advert.getAdvertId());
                showAdvertIntent.putExtra("advertName", advert.getAdvertName());
                showAdvertIntent.putExtra("advertPhone", advert.getAdvertPhone());
                showAdvertIntent.putExtra("advertDescription", advert.getAdvertDescription());
                showAdvertIntent.putExtra("advertDate", advert.getAdvertDate());
                showAdvertIntent.putExtra("advertLocation", advert.getAdvertLocation());
                showAdvertIntent.putExtra("advertPostType", advert.getAdvertPostType());
                startActivity(showAdvertIntent);
                finish();
            }
        });
    }
}