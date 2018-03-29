package com.example.tarun.khana;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SeekerClickedTodaysFoodInfo extends AppCompatActivity {

    private class SeekerClickedTodaysFoodInfoHolder{
        ImageView currentClickedFoodImage;
        TextView currentClickedFoodName,currentClickedFoodPrice, currentClickedFoodQuantity, providerAddress;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating the view of the activity here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_clicked_todays_food_info);
        //getting the intent bundle from the previous activity
        Bundle intent = getIntent().getExtras();
        GetUserInfo currentUserInfo =  intent.getParcelable("current_user_info");
        ArrayList<SeekerGetTodayFoodInfo> list = (ArrayList<SeekerGetTodayFoodInfo>) intent.getSerializable("todays_food_info");
        ArrayList<String> url = (ArrayList<String>) intent.getSerializable("image_of_food_clicked");
        final int i = intent.getInt("current_value");
        //initializig all the variables here
        SeekerClickedTodaysFoodInfoHolder seekerClickedTodaysFoodInfoHolder = new SeekerClickedTodaysFoodInfoHolder();
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodImage = (ImageView) findViewById(R.id.currentClickedFoodImage);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodName = (TextView) findViewById(R.id.currentClickedFoodName);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodPrice = (TextView) findViewById(R.id.currentClickedFoodPrice);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodQuantity = (TextView) findViewById(R.id.currentClickedFoodQuantity);
        seekerClickedTodaysFoodInfoHolder.providerAddress = (TextView) findViewById(R.id.foodProviderAddress);


        //Setting all the views here
        //Image of the current clicked food
        Glide.with(SeekerClickedTodaysFoodInfo.this).load(url.get(i)).into(seekerClickedTodaysFoodInfoHolder.currentClickedFoodImage);
        //Name,quantity,price,address of the current clicked food
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodName.setText(list.get(i).dish_name);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodQuantity.setText(list.get(i).dish_quantity);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodPrice.setText(list.get(i).dish_price);
        seekerClickedTodaysFoodInfoHolder.providerAddress.setText(list.get(i).provider_address);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seeker_home, menu);
        return true;
    }
}
