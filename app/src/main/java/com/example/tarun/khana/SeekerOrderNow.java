package com.example.tarun.khana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SeekerOrderNow extends AppCompatActivity {


    private Singleton var = Singleton.getInstance();
    double totalAmount = 0;
    double taxAmount = 0;

    private class SeekerOrderNowHolder{
        TextView orderPrice, orderName1,orderName2, orderTax,orderQuantity, currentUserAddress, totalPrice;
        Button makePayment1,makePayment2;
        ImageView currentDishImage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_order_now);
        //getting all the info from the previous activity
        Bundle intent = getIntent().getExtras();
        final SeekerGetTodayFoodInfo foodInfo = (SeekerGetTodayFoodInfo) intent.getSerializable("food info");
        String image = intent.getString("image");
        String quantity_number = intent.getString("order_quantity");
        //final GetUserInfo currentUserInfo =  intent.getParcelable("current_user_info");
        Log.v("address",""+var.getUserInfo.user_address);

        //initialization
        SeekerOrderNowHolder seekerOrderNowHolder = new SeekerOrderNowHolder();
        seekerOrderNowHolder.orderPrice = (TextView) findViewById(R.id.currentOrderPrice);
        seekerOrderNowHolder.orderName1 = (TextView) findViewById(R.id.currentOrderName);
        seekerOrderNowHolder.totalPrice = (TextView) findViewById(R.id.currentTotalAmount);
        seekerOrderNowHolder.orderName2 = (TextView) findViewById(R.id.orderDishName);
        seekerOrderNowHolder.orderTax = (TextView) findViewById(R.id.currentOrderTaxandFee);
        seekerOrderNowHolder.currentUserAddress = (TextView) findViewById(R.id.userShippingAddress);
        seekerOrderNowHolder.makePayment1 = (Button) findViewById(R.id.makePayment1);
        seekerOrderNowHolder.makePayment2 = (Button) findViewById(R.id.makePayment2);
        seekerOrderNowHolder.currentDishImage = (ImageView) findViewById(R.id.currentItemImage);
        seekerOrderNowHolder.orderQuantity = (TextView) findViewById(R.id.orderDishQuantity) ;

        //calculating the tax here
        taxAmount = Double.parseDouble(foodInfo.dish_price) * 0.1025;

        //caculating total price here
        totalAmount = taxAmount + Double.parseDouble(foodInfo.dish_price);

        //Setting all the variables
        seekerOrderNowHolder.orderPrice.setText(foodInfo.dish_price);
        seekerOrderNowHolder.orderTax.setText(Double.toString(taxAmount));
        seekerOrderNowHolder.orderName1.setText(foodInfo.dish_name);
        seekerOrderNowHolder.orderName2.setText(foodInfo.dish_name);
        seekerOrderNowHolder.totalPrice.setText(Double.toString(totalAmount));
        seekerOrderNowHolder.currentUserAddress.setText(var.getUserInfo.user_address);
        seekerOrderNowHolder.orderQuantity.setText(quantity_number);



        //image load
        Glide.with(SeekerOrderNow.this).load(image).into(seekerOrderNowHolder.currentDishImage);

        //make payment button click listner
        seekerOrderNowHolder.makePayment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SeekerOrderNow.this,SeekerMakePayment.class);
                intent1.putExtra("payment Price",totalAmount);
                startActivity(intent1);
            }
        });
        seekerOrderNowHolder.makePayment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SeekerOrderNow.this,SeekerMakePayment.class);
                intent1.putExtra("payment Price",totalAmount);
                startActivity(intent1);
            }
        });

    }
}
