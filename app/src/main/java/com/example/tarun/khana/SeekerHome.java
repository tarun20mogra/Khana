package com.example.tarun.khana;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class SeekerHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{

    GoogleMap gMap;
    MapView mapViewForFoodLocation;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    String[] string = {"one","two","three","four","one","two","three","four","one","two","three","four"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_home);
        //getting the intent
        final Bundle intent = getIntent().getExtras();
        final GetUserInfo userInfoLogin = (GetUserInfo) intent.getSerializable("username");
        Toast.makeText(this, ""+userInfoLogin.user_name, Toast.LENGTH_SHORT).show();
        //Setting the map view here
        mapViewForFoodLocation = findViewById(R.id.mapViewForFoodLocations);
        if(mapViewForFoodLocation != null){
            mapViewForFoodLocation.onCreate(null);
            mapViewForFoodLocation.onResume();
            mapViewForFoodLocation.getMapAsync(this);
        }
        //setting the listview here
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) ;
        arrayAdapter.add("First");
        arrayAdapter.add("second");
        arrayAdapter.add("Third");
        arrayAdapter.add("Fourth");
        arrayAdapter.add("Fifth");
        arrayAdapter.add("Sixth");
        arrayAdapter.add("Seventh");
        arrayAdapter.add("Eighth");
        arrayAdapter.add("Ninth");
        arrayAdapter.add("Tenth");

        listView = (ListView) findViewById(R.id.listViewForFood);
        listView.setAdapter(arrayAdapter);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(34.028572,-117.910860) , 14.0f) );
    }
}
