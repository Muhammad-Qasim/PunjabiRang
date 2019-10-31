package com.example.myfyp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.myfyp.Model.Post;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    String str_address;
    double latitude;
    double longitude;

   // Button button;

    private FusedLocationProviderClient client;
    private static final float DEFAULT_ZOOM = 15f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

 //       button = findViewById(R.id.btn_map);

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();
        str_address = intent.getStringExtra("address");
   //     Toast.makeText(this, "Adddress =" + str_address, Toast.LENGTH_SHORT).show();
/*

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
*/

                if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MapActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

     //                       Toast.makeText(MapActivity.this, "" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();


                            LatLng uskt = new LatLng(latitude, longitude);
                            map.addMarker(new MarkerOptions().position(uskt).title("University OF Sialkot"));
                            map.moveCamera(CameraUpdateFactory.newLatLng(uskt));

                            geoLocate();

                 /*           LatLng uskt = new LatLng(latitude, longitude);
                            map.addMarker(new MarkerOptions().position(uskt).title("University OF Sialkot"));
                            map.moveCamera(CameraUpdateFactory.newLatLng(uskt));
                      */  }

                    }
                });
            }
/*
        });
    }
*/

    private void geoLocate() {

        Geocoder geocoder= new Geocoder(MapActivity.this);
        List<Address> list= new ArrayList<>();
        try{
            list= geocoder.getFromLocationName(str_address,1);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(list.size() >0){
            Address address = list.get(0);
   //         Toast.makeText(this, ""+address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }
    private void moveCamera(LatLng latLng, float zoom, String title){
 //       Toast.makeText(this, "Move Camera function Error", Toast.LENGTH_SHORT).show();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options= new MarkerOptions()
                .position(latLng)
                .title(title);
        map.addMarker(options);
    }


    private void requestPermission() {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }
}
