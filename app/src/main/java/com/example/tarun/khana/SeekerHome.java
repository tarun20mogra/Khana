package com.example.tarun.khana;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SeekerHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
    //Global Variables
    GoogleMap gMap;
    MapView mapViewForFoodLocation;
    private Singleton var = Singleton.getInstance();
    RecyclerView recyclerView;
    String currentUserAddress = null;
    DatabaseReference databaseReference;
    SeekerGetTodayFoodInfo [] seekerGetTodayFoodInfo = null;
    ArrayList<SeekerGetTodayFoodInfo> todayFoodNearBy = new ArrayList<>();
    LatLng currentUserAddressLattitudeandLongitude,foodAddress;
    ArrayList<LatLng> foodproviderAddressLattitdeandLongitude = new ArrayList<>();
    GetUserInfo userInfoLogin = new GetUserInfo();
    double distanceForFoodNearby = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setting the view for the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_home);
        //getting the intent and setting the variables
        final Bundle intent = getIntent().getExtras();
        userInfoLogin = (GetUserInfo) intent.getSerializable("username");
        var.getUserInfo = userInfoLogin;
        currentUserAddress = userInfoLogin.user_address;
        //First thing first calculate the longitute and lattitude of the current user address
        Log.v("Current Address"," :"+currentUserAddress);
        currentUserAddressLattitudeandLongitude = getLocationFromAddress(SeekerHome.this,currentUserAddress);
        Log.v("latt and long is"," :"+currentUserAddressLattitudeandLongitude);
        //setting the Recycler View here
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTodaysFood);

        //Setting todays date
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        final Date now = new Date();
        final String strDate = sdfDate.format(now);
        //Database Reference
         databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/TodaysFood/2018-03-23");//strDate
         databaseReference.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //applying jackson object mapper to deserialize
                 ObjectMapper mapper = new ObjectMapper();
                 String json = "";
                 try {
                     json  = "["+mapper.writeValueAsString(dataSnapshot.getValue())+"]";
                 } catch (JsonProcessingException e) {
                     e.printStackTrace();
                 }
                 mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                 try {
                     seekerGetTodayFoodInfo = mapper.readValue(json,SeekerGetTodayFoodInfo[].class);
                     Log.v("dish name is", ""+seekerGetTodayFoodInfo[0].provider_address);
                     foodAddress = getLocationFromAddress(SeekerHome.this,seekerGetTodayFoodInfo[0].provider_address);
                     foodproviderAddressLattitdeandLongitude.add(foodAddress);
                     distanceForFoodNearby = distance(currentUserAddressLattitudeandLongitude.latitude,currentUserAddressLattitudeandLongitude.longitude,foodAddress.latitude,foodAddress.longitude);
                     Log.v("distance is",""+distanceForFoodNearby);
                     if(distanceForFoodNearby < 30.00){
                         todayFoodNearBy.add(seekerGetTodayFoodInfo[0]);
                         //gMap.addMarker(new MarkerOptions().position(new LatLng(foodAddress.latitude,foodAddress.longitude)));
                     }
                     createListView();

                 }catch (JsonParseException exception){
                     Log.v("error","Json Parser"+exception);
                 } catch (JsonMappingException e){
                     Log.v("error","Mapping: "+e);
                 }
                 catch (IOException e) {
                     Log.v("error","IoException"+e);
                     e.printStackTrace();
                 }

             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

        //Setting the map view methods here
        mapViewForFoodLocation = findViewById(R.id.mapViewForFoodLocations);
        if(mapViewForFoodLocation != null){
            mapViewForFoodLocation.onCreate(null);
            mapViewForFoodLocation.onResume();
            mapViewForFoodLocation.getMapAsync(this);
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seeker_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            Intent intent = new Intent(SeekerHome.this,MainActivity.class);
            startActivity(intent);
        }
       else if(id == R.id.action_cart){
            if(var.showCart == true){
                Intent intent = new Intent(SeekerHome.this,Cart.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show();
            }

        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_home) {
           Intent intent = new Intent(SeekerHome.this, SeekerHome.class);
           startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(SeekerHome.this,UserProfile.class);
            intent.putExtra("userProfile",userInfoLogin);
            intent.putExtra("user_is","Seeker");
            startActivity(intent);

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_logout) {
           Intent intent = new Intent(SeekerHome.this,MainActivity.class);
           startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Seeting the coordinates to show on the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(currentUserAddressLattitudeandLongitude.latitude,currentUserAddressLattitudeandLongitude.longitude) , 14.0f) );
        gMap.addMarker(new MarkerOptions().position(new LatLng(currentUserAddressLattitudeandLongitude.latitude,currentUserAddressLattitudeandLongitude.longitude)).icon(bitmapDescriptorFromVector(R.drawable.ic_location_dot)));
        if(distanceForFoodNearby < 30.00){
            Log.v("distance in marker",""+distanceForFoodNearby);
            gMap.addMarker(new MarkerOptions().position(new LatLng(foodAddress.latitude,foodAddress.longitude)));
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int ic_location_dot) {
        Drawable vectorDrawable = ContextCompat.getDrawable(SeekerHome.this, ic_location_dot);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    //Function to calculate longitude and lattitude
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context, Locale.getDefault());
        List<Address> address ;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            for(int i=0;i<address.size();i++)
            {


                Address location = address.get(i);

                if(location.getLatitude()!=0 && location.getLongitude()!=0)
                {
                    location.getLatitude();
                    location.getLongitude();
                    p1 = new LatLng(location.getLatitude(),location.getLongitude());

                    break;
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    //Calculating the distance between two addresses here
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    //List view adapter
    void createListView(){
        SeekerTodaysFoodListAdapter seekerTodaysFoodListAdapter = new SeekerTodaysFoodListAdapter(SeekerHome.this,todayFoodNearBy,userInfoLogin);
        recyclerView.setLayoutManager(new LinearLayoutManager(SeekerHome.this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(seekerTodaysFoodListAdapter);
    }

}
