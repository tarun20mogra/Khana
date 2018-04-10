package com.example.tarun.khana;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.io.Serializable;
import java.util.ArrayList;

public class SeekerClickedTodaysFoodInfo extends AppCompatActivity {
    private Singleton var = Singleton.getInstance();
    String quantity_number = null;
    String image = null;
    GetUserInfo currentUserInfo;


    private class SeekerClickedTodaysFoodInfoHolder {
        ImageView currentClickedFoodImage;
        TextView currentClickedFoodName, currentClickedFoodPrice, currentClickedFoodQuantity, providerAddress, descriptionOfTheFood;
        ElegantNumberButton quantity_of_order;
        Button order_now, add_to_cart;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating the view of the activity here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_clicked_todays_food_info);
        //getting the intent bundle from the previous activity
        final Bundle intent = getIntent().getExtras();
        currentUserInfo = intent.getParcelable("current_user_info");
        final ArrayList<SeekerGetTodayFoodInfo> list = (ArrayList<SeekerGetTodayFoodInfo>) intent.getSerializable("todays_food_info");
        //ArrayList<String> url =  getIntent().getStringArrayListExtra("image_of_food_clicked");

        final int i = intent.getInt("current_value");


        //initializig all the variables here
        final SeekerClickedTodaysFoodInfoHolder seekerClickedTodaysFoodInfoHolder = new SeekerClickedTodaysFoodInfoHolder();
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodImage = (ImageView) findViewById(R.id.currentClickedFoodImage);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodName = (TextView) findViewById(R.id.currentClickedFoodName);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodPrice = (TextView) findViewById(R.id.currentClickedFoodPrice);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodQuantity = (TextView) findViewById(R.id.currentClickedFoodQuantity);
        seekerClickedTodaysFoodInfoHolder.providerAddress = (TextView) findViewById(R.id.foodProviderAddress);
        seekerClickedTodaysFoodInfoHolder.descriptionOfTheFood = (TextView) findViewById(R.id.descriptionOfTheFood);
        seekerClickedTodaysFoodInfoHolder.quantity_of_order = (ElegantNumberButton) findViewById(R.id.dish_quantity);
        seekerClickedTodaysFoodInfoHolder.order_now = (Button) findViewById(R.id.orderNowButton);
        seekerClickedTodaysFoodInfoHolder.add_to_cart = (Button) findViewById(R.id.addToCart);

        //Setting all the views here
        //Image of the current clicked food
        Glide.with(SeekerClickedTodaysFoodInfo.this).load(var.urlOfTodaysFoodImage.get(list.get(i).user_name + "_" + list.get(i).dish_name)).into(seekerClickedTodaysFoodInfoHolder.currentClickedFoodImage);
        //Name,quantity,price,address of the current clicked food
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodName.setText(list.get(i).dish_name);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodQuantity.setText(list.get(i).dish_quantity);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodPrice.setText("$" + list.get(i).dish_price);
        seekerClickedTodaysFoodInfoHolder.providerAddress.setText(list.get(i).provider_address);
        seekerClickedTodaysFoodInfoHolder.descriptionOfTheFood.setText("This is as delicious as it looks in the photo. It is " + list.get(i).dist_type + " dish coocked properly and is mouthwatering taste.It is " + list.get(i).dish_spiciness + ".");

        seekerClickedTodaysFoodInfoHolder.quantity_of_order.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity_number = seekerClickedTodaysFoodInfoHolder.quantity_of_order.getNumber();
            }
        });
        seekerClickedTodaysFoodInfoHolder.order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(quantity_number) > Integer.parseInt(list.get(i).dish_quantity)) {
                    Toast.makeText(SeekerClickedTodaysFoodInfo.this, "Max quantity allowed" + list.get(i).dish_quantity, Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(quantity_number) == 0) {
                    Toast.makeText(SeekerClickedTodaysFoodInfo.this, "Select some quantity", Toast.LENGTH_SHORT).show();
                } else {
                    image = var.urlOfTodaysFoodImage.get(list.get(i).user_name + "_" + list.get(i).dish_name);
                    Intent intent1 = new Intent(SeekerClickedTodaysFoodInfo.this, SeekerOrderNow.class);
                    intent1.putExtra("food info", (Serializable) list.get(i));
                    intent1.putExtra("image", image);
                    intent1.putExtra("current_user_info", currentUserInfo);
                    intent1.putExtra("order_quantity", quantity_number);

                    startActivity(intent1);
                }

            }
        });
        seekerClickedTodaysFoodInfoHolder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(quantity_number) > Integer.parseInt(list.get(i).dish_quantity)) {
                    Toast.makeText(SeekerClickedTodaysFoodInfo.this, "Max quantity allowed" + list.get(i).dish_quantity, Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(quantity_number) == 0) {
                    Toast.makeText(SeekerClickedTodaysFoodInfo.this, "Select some quantity", Toast.LENGTH_SHORT).show();
                } else {

                    var.showCart = true;
                    var.cartFoodImageUrl.add(var.urlOfTodaysFoodImage.get(list.get(i).user_name + "_" + list.get(i).dish_name));
                    var.cartFoodInfo.add(list.get(i));
                    var.quantity.add(quantity_number);
                    Toast.makeText(SeekerClickedTodaysFoodInfo.this, "Added to cart", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seeker_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent intent = new Intent(SeekerClickedTodaysFoodInfo.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_cart) {
            if (var.showCart == true) {
                Intent intent = new Intent(SeekerClickedTodaysFoodInfo.this, Cart.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show();
            }

        }

        return true;
    }

}
