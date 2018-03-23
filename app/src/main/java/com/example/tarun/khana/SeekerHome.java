package com.example.tarun.khana;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeekerHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
    //Global Variables
    GoogleMap gMap;
    MapView mapViewForFoodLocation;
    ListView listView;
    String currentUserAddress = null;
    DatabaseReference databaseReference;
    SeekerGetTodayFoodInfo [] seekerGetTodayFoodInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setting the view for the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_home);
        //getting the intent and setting the variables
        final Bundle intent = getIntent().getExtras();
        final GetUserInfo userInfoLogin = (GetUserInfo) intent.getSerializable("username");
        currentUserAddress = userInfoLogin.user_address;
        //Setting todays date
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        final Date now = new Date();
        final String strDate = sdfDate.format(now);
        //Database Reference
         databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/TodaysFood/"+strDate);
         databaseReference.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 Log.v("Child value is", ""+dataSnapshot.getValue());
                 ObjectMapper mapper = new ObjectMapper();
                 String json = "";
                 String json1 = "";
                 try {
                     json  = mapper.writeValueAsString(dataSnapshot.getValue());
                     json1 = "["+json+"]";
                     Log.v("json is",""+json);
                 } catch (JsonProcessingException e) {
                     e.printStackTrace();
                 }
                 mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                 try {
                     seekerGetTodayFoodInfo = mapper.readValue(json1,SeekerGetTodayFoodInfo[].class);
                     Log.v("dish name is", ""+seekerGetTodayFoodInfo[0].dish_name);
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
        //setting the listview here
        listView = (ListView) findViewById(R.id.listViewForFood);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_home) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_logout) {

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
        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(34.028572,-117.910860) , 14.0f) );
    }
}
