package com.example.a91p.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a91p.R;
import com.example.a91p.data.DatabaseHelper;
import com.example.a91p.model.Advert;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


import com.google.android.gms.maps.model.LatLng;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.model.Place;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

public class CreateAdvertActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    RadioButton lostRadioButton;
    RadioButton foundButton;
    EditText nameEditText;
    EditText phoneEditText;
    EditText descriptionEditText;
    EditText dateEditText;
    EditText locationEditText;
    Button saveButton, currentLocationButton;
    private FusedLocationProviderClient fusedLocationClient;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        dbHelper = new DatabaseHelper(this);

        lostRadioButton = findViewById(R.id.lostRadioButton);
        foundButton = findViewById(R.id.foundButton);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        locationEditText = findViewById(R.id.locationEditText);
        saveButton = findViewById(R.id.saveButton);
        currentLocationButton = findViewById(R.id.currentLocationButton);


        String API_KEY = "AIzaSyDH4ohQAFof08m_LGpExVi51cYIS6Vvwyc";
        Places.initialize(getApplicationContext(), API_KEY);
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.LAT_LNG));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(CreateAdvertActivity.this, "Error: "+status, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latlng = place.getLatLng();
                locationEditText.setText(latlng.latitude+","+ latlng.longitude);
            }
        });

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationEditText.setText(location.getLatitude()+","+location.getLongitude());
            }
        };

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(CreateAdvertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateAdvertActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateAdvertActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Clicked", "Save Button");
                if(!ValidPostType())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please select a post type."
                            , Toast.LENGTH_SHORT).show();
                }
                else if(!ValidName())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a name of at least 3 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidPhone())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a phone number of at least 8 digits.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidDescription())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a description of at least 10 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidDate())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a date in the format dd/MM/yyyy",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidLocation())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a location with at least 4 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CreateAdvertActivity.this, "Successfully add new data",
                            Toast.LENGTH_SHORT).show();
                    Log.i("Success", "Success");
                    SaveAdvert();
                    finish();
                }
            }
        });
    }

    private void SaveAdvert()
    {
        Advert advert = new Advert(nameEditText.getText().toString(), phoneEditText.getText().toString(),
                descriptionEditText.getText().toString(), dateEditText.getText().toString(), locationEditText.getText().toString(),
                GetPostType());

        dbHelper.InsertAdvert(advert);
    }

    private String GetPostType()
    {
        if(lostRadioButton.isChecked())
        {
            return lostRadioButton.getText().toString();
        }
        else
        {
            return foundButton.getText().toString();
        }
    }

    private boolean ValidPostType()
    {
        return lostRadioButton.isChecked() || foundButton.isChecked();
    }

    private boolean ValidName()
    {
        String name = nameEditText.getText().toString();
        return name != null && name.trim().length() > 2;
    }

    private boolean ValidPhone()
    {
        String phone = phoneEditText.getText().toString();
        return phone != null && phone.trim().length() > 7;
    }

    private boolean ValidDescription()
    {
        String description = descriptionEditText.getText().toString();
        return description != null && description.trim().length() > 9;
    }

    private boolean ValidDate()
    {
        String dateString = dateEditText.getText().toString();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean ValidLocation()
    {
        String location = locationEditText.getText().toString();
        return location != null && location.trim().length() > 3;
    }


}