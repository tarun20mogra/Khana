package com.example.tarun.khana;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProviderHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static int GALLARY_INTENT = 2;
    public static transient Bitmap bitmap;
    String dish_name = null;
    String dish_price = null;
    String dish_quantity = null;
    String provider_address = null;
    String dish_type = null;
    String dish_spiciness = null;
    Uri uri;
    GetUserInfo userInfoLogin = new GetUserInfo();
    final ProviderHomeHolder providerHomeHolder = new ProviderHomeHolder();
    public class ProviderHomeHolder{
        EditText dishName, price, addressLine1,addressLine2,city,zipCode,country, quantity;
        RadioGroup type, spiciness;
        Button uploadPhoto, previewOrder;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onCreate method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);
        //Receiving the intent here
        final Bundle intent = getIntent().getExtras();
        userInfoLogin = (GetUserInfo) intent.getSerializable("username");
        //Initializing all the variables for the XML
        providerHomeHolder.dishName = (EditText) findViewById(R.id.dishName);
        providerHomeHolder.price = (EditText) findViewById(R.id.priceDish);
        providerHomeHolder.addressLine1 = (EditText) findViewById(R.id.addressProviderLine1);
        providerHomeHolder.quantity = (EditText) findViewById(R.id.quantityDish);
        providerHomeHolder.type = (RadioGroup) findViewById(R.id.typeOfDish);
        providerHomeHolder.spiciness = (RadioGroup) findViewById(R.id.spicyRadioGroup);
        providerHomeHolder.uploadPhoto = (Button) findViewById(R.id.uploadPhoto);
        providerHomeHolder.previewOrder = (Button) findViewById(R.id.previewOrder);
        providerHomeHolder.addressLine2 = (EditText) findViewById(R.id.addressProviderLine2);
        providerHomeHolder.city = (EditText) findViewById(R.id.providerCity);
        providerHomeHolder.zipCode = (EditText) findViewById(R.id.providerZipCode);
        providerHomeHolder.country = (EditText) findViewById(R.id.providerCountry);



        //Setting todays date
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        final String strDate = sdfDate.format(now);

        //setting the functionality for uploading the photo
        providerHomeHolder.uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1,GALLARY_INTENT);
            }
        });
        //Checking for the type and spiciness of the dish
        providerHomeHolder.type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.nonVegTye){
                    dish_type = "Non Vegetarian";

                }
                else if(i == R.id.vegTye){
                    dish_type = "Vegetarian";
                }
                else if(i == R.id.veganTye){
                    dish_type = "Vegan";
                }
            }
        });
        providerHomeHolder.spiciness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.lowSpiciness){
                    dish_spiciness = "Low Spicy";

                }
                else if(i == R.id.mediumSpiciness){
                    dish_spiciness = "Medium Spicy";
                }
                else if(i == R.id.spicySpiciness){
                    dish_spiciness = "Spicy";
                }
            }
        });
        //Setting the functionality for the preview button
        providerHomeHolder.previewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dish_name = providerHomeHolder.dishName.getText().toString();
                dish_price = String.valueOf(providerHomeHolder.price.getText());
                dish_quantity = String.valueOf(providerHomeHolder.quantity.getText());
                if(!String.valueOf(providerHomeHolder.addressLine2.getText()).isEmpty()){
                    if(!String.valueOf(providerHomeHolder.addressLine1.getText()).isEmpty() && !String.valueOf(providerHomeHolder.city.getText()).isEmpty() && !String.valueOf(providerHomeHolder.zipCode.getText()).isEmpty() && !String.valueOf(providerHomeHolder.country.getText()).isEmpty())
                    {provider_address = String.valueOf(providerHomeHolder.addressLine1.getText())+","+String.valueOf(providerHomeHolder.addressLine2.getText())+","+String.valueOf(providerHomeHolder.city.getText())+","+String.valueOf(providerHomeHolder.zipCode.getText())+","+String.valueOf(providerHomeHolder.country.getText());}
                    else {
                        provider_address = null;
                    }

                }
                else if(String.valueOf(providerHomeHolder.addressLine2.getText()).isEmpty())
                {
                    if(!String.valueOf(providerHomeHolder.addressLine1.getText()).isEmpty() || !String.valueOf(providerHomeHolder.city.getText()).isEmpty() || !String.valueOf(providerHomeHolder.zipCode.getText()).isEmpty() || !String.valueOf(providerHomeHolder.country.getText()).isEmpty())
                    {
                        provider_address = String.valueOf(providerHomeHolder.addressLine1.getText())+","+String.valueOf(providerHomeHolder.city.getText())+","+String.valueOf(providerHomeHolder.zipCode.getText())+","+String.valueOf(providerHomeHolder.country.getText());

                    }
                    else {
                        provider_address = null;
                    }

                }

                if(dish_name.isEmpty() || dish_price.isEmpty() || dish_quantity.isEmpty() || provider_address.isEmpty() || provider_address == null || dish_type == null || dish_quantity == null || uri == null){
                    Toast.makeText(ProviderHome.this, "Values can not be left empty", Toast.LENGTH_LONG).show();

                }
                else {
                    OrderPreviewSaveInfo orderPreviewSaveInfo = new OrderPreviewSaveInfo(dish_name,dish_price,dish_quantity,provider_address,dish_type,dish_spiciness,strDate,userInfoLogin.user_name);
                    Intent previeworder = new Intent(ProviderHome.this,PreviewOrder.class);
                    //intent sending the values for the order to preview activity
                    previeworder.putExtra("preview",orderPreviewSaveInfo);
                    //sending the username
                    previeworder.putExtra("username",userInfoLogin);
                    //intent sending the photo uri to preview activity
                    previeworder.putExtra("image",uri.toString());
                    startActivity(previeworder);
                }

            }
        });


        //Setting the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting the drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //setting the navigationg text here
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.navBarTextHeading);
        navUsername.setText(userInfoLogin.user_fullName);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        uri = data.getData();
        if(uri == null){
            Toast.makeText(this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "photo upcloaded", Toast.LENGTH_SHORT).show();
        }
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
        getMenuInflater().inflate(R.menu.provider_home, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this,ProviderHome.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(ProviderHome.this,UserProfile.class);
            intent.putExtra("userProfile",userInfoLogin);
            intent.putExtra("user_is","Provider");
            startActivity(intent);



        } else if (id == R.id.nav_history) {


        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(ProviderHome.this,MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        Intent intent = new Intent(ProviderHome.this,MainActivity.class);
        startActivity(intent);

        return true;
    }

}
