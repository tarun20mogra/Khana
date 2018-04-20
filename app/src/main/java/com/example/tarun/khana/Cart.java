package com.example.tarun.khana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

public class Cart extends AppCompatActivity {
    private Singleton var = Singleton.getInstance();
    double price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //total price



        //setting all the view variable for cart activity here
        TextView itemCount = (TextView) findViewById(R.id.itemCount);
        TextView cartSubtotal = (TextView) findViewById(R.id.cartSubtotal);
        TextView cartSubtotal2 = (TextView) findViewById(R.id.cartSubtotalPrice);
        TextView taxAndFee = (TextView) findViewById(R.id.taxAndFeePrice);

        TextView totalPrice = (TextView) findViewById(R.id.price);
        Button checkout = (Button) findViewById(R.id.proceedToCheckOut);
        TextView backButton = (TextView) findViewById(R.id.backButton);

        RecyclerView cartFoodList = (RecyclerView) findViewById(R.id.cartFoodRecyclerView);
        // calculating total price
        itemCount.setText("("+var.cartFoodInfo.size()+" items) :");
        Toast.makeText(this, ""+var.cartFoodInfo.size(), Toast.LENGTH_SHORT).show();
        for(int i = 0 ; i < var.cartFoodInfo.size();i++){
            var.cartSubotal = (long) (var.cartSubotal + (Double.parseDouble(var.cartFoodInfo.get(i).dish_price) * Double.parseDouble(var.quantity.get(i))));
        }
        //Calculatinf the tax, applying 10.25% tax on the whole food
        var.tax = 3 + (long)(0.10 * var.cartSubotal);

        //calculating total price
        var.price = (long)(var.cartSubotal + var.tax);


        // Setting up all the texts
        cartSubtotal.setText("$"+Double.toString(var.cartSubotal));
        cartSubtotal2.setText("$"+Double.toString(var.cartSubotal));
        taxAndFee.setText("$"+Double.toString(var.tax));

        totalPrice.setText("$"+Double.toString(var.price));


        //setting recycler view here
        for (int i = 0; i<var.cartFoodInfo.size();i++){
            CartListAdapter cartListAdapter = new CartListAdapter(Cart.this);
            cartFoodList.setLayoutManager(new LinearLayoutManager(Cart.this));
            cartFoodList.setNestedScrollingEnabled(false);
            cartFoodList.setAdapter(cartListAdapter);

        }

        //make payment button click listener
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this,SeekerMakePayment.class);

                startActivity(intent);

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.super.onBackPressed();
            }
        });



    }
}
