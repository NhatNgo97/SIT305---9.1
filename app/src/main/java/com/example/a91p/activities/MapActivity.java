package com.example.a91p.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.a91p.R;
import com.example.a91p.data.DatabaseHelper;
import com.example.a91p.model.Advert;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<Advert> dbAlerts = db.GetAdverts();
        for (int i =0;i <dbAlerts.size();i++){
            String[] loc = dbAlerts.get(i).getAdvertLocation().split(",");
            try {
                LatLng temp = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                mMap.addMarker(new MarkerOptions().position(temp).title(dbAlerts.get(i).getAdvertPostType()+" "+dbAlerts.get(i).getAdvertName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));
            }catch(Exception e){ }//catch exception
        }
    }
}