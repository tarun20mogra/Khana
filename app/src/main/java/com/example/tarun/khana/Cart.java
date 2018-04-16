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
        double cartSubtotalPrice = 0;
        double tax = 0;


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

            cartSubtotalPrice = (long) (cartSubtotalPrice + (Double.parseDouble(var.cartFoodInfo.get(i).dish_price) * Double.parseDouble(var.quantity.get(i))));
        }
        //Calculatinf the tax, applying 10.25% tax on the whole food
        tax = 3 + (long)(0.10 * cartSubtotalPrice);

        //calculating total price
        price = (long)(cartSubtotalPrice + tax);


        // Setting up all the texts
        cartSubtotal.setText("$"+Double.toString(cartSubtotalPrice));
        cartSubtotal2.setText("$"+Double.toString(cartSubtotalPrice));
        taxAndFee.setText("$"+Double.toString(tax));

        totalPrice.setText("$"+Double.toString(price));


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
                intent.putExtra("payment Price",price);
                startActivity(intent);

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(Cart.this,SeekerHome.class);
                intent1.putExtra("username",var.getUserInfo);
                startActivity(intent1);
            }
        });



    }
}
