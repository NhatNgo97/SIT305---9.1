package com.example.a91p.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a91p.R;
import com.example.a91p.data.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RemoveAdvertActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    TextView showAdvertTextView;
    TextView showDateTextView;
    TextView showLocationTextView;
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_advert);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        int advertId = intent.getIntExtra("advertId", -1);
        String advertName = intent.getStringExtra("advertName");
        String advertPhone = intent.getStringExtra("advertPhone");
        String advertDescription = intent.getStringExtra("advertDescription");
        String advertDate = intent.getStringExtra("advertDate");
        String advertLocation = intent.getStringExtra("advertLocation");
        String advertPostType = intent.getStringExtra("advertPostType");

        showAdvertTextView = findViewById(R.id.showAdvertTextView);
        showDateTextView = findViewById(R.id.showDateTextView);
        showLocationTextView = findViewById(R.id.showLocationTextView);
        removeButton = findViewById(R.id.removeButton);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();
        Date todaysDate = cal.getTime();

        Date convertedDate = null;
        try {
            convertedDate = simpleDateFormat.parse(advertDate);
        } catch (ParseException e) {
            convertedDate = todaysDate;
        }

        long milliSecondsDifference = Math.abs(todaysDate.getTime() - convertedDate.getTime());
        int daysDifference = (int) TimeUnit.DAYS.convert(milliSecondsDifference, TimeUnit.MILLISECONDS);

        showAdvertTextView.setText(advertPostType + " " + advertName + ": " + advertDescription);
        showDateTextView.setText(String.valueOf(daysDifference + " days ago"));
        showLocationTextView.setText("At " + advertLocation + " Phone: " + advertPhone);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.DeleteAdvert(advertId);
                finish();
            }
        });
    }
}