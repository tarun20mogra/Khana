package com.example.tarun.khana;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SeekerClickedTodaysFoodInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_clicked_todays_food_info);
        Bundle intent = getIntent().getExtras();
        GetUserInfo currentUserInfo =  intent.getParcelable("current_user_info");
        //SeekerGetTodayFoodInfo todaysFoodInfoClicked = intent.getParcelable("todays_food_info");
        String url = intent.getString("image_of_food_clicked");
        ImageView imageView = (ImageView) findViewById(R.id.clicked_food_image);
        Glide.with(SeekerClickedTodaysFoodInfo.this).load(url).into(imageView);

    }
}
