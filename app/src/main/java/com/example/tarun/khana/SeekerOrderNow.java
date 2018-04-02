package com.example.tarun.khana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SeekerOrderNow extends AppCompatActivity {


    private Singleton var = Singleton.getInstance();

    private class SeekerOrderNowHolder{
        TextView orderPrice, orderName1,orderName2, orderTax, currentUserAddress;
        Button makePayment1,makePayment2;
        ImageView currentDishImage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_order_now);
        //getting all the info from the previous activity
        Bundle intent = getIntent().getExtras();
        SeekerGetTodayFoodInfo foodInfo = (SeekerGetTodayFoodInfo) intent.getSerializable("food info");
        String image = intent.getString("image");
        //final GetUserInfo currentUserInfo =  intent.getParcelable("current_user_info");
        Log.v("address",""+var.getUserInfo.user_address);

        //initialization
        SeekerOrderNowHolder seekerOrderNowHolder = new SeekerOrderNowHolder();
        seekerOrderNowHolder.orderPrice = (TextView) findViewById(R.id.currentOrderPrice);
        seekerOrderNowHolder.orderName1 = (TextView) findViewById(R.id.currentOrderName);

        seekerOrderNowHolder.orderName2 = (TextView) findViewById(R.id.orderDishName);
        seekerOrderNowHolder.orderTax = (TextView) findViewById(R.id.currentOrderTaxandFee);
        seekerOrderNowHolder.currentUserAddress = (TextView) findViewById(R.id.userShippingAddress);
        seekerOrderNowHolder.makePayment1 = (Button) findViewById(R.id.makePayment1);
        seekerOrderNowHolder.makePayment2 = (Button) findViewById(R.id.makePayment2);
        seekerOrderNowHolder.currentDishImage = (ImageView) findViewById(R.id.currentItemImage);

        //Setting all the variables
        seekerOrderNowHolder.orderPrice.setText(foodInfo.dish_price);
        seekerOrderNowHolder.orderName1.setText(foodInfo.dish_name);
        seekerOrderNowHolder.orderName2.setText(foodInfo.dish_name);
        seekerOrderNowHolder.currentUserAddress.setText(var.getUserInfo.user_address);

        //image load
        Glide.with(SeekerOrderNow.this).load(image).into(seekerOrderNowHolder.currentDishImage);

    }
}
