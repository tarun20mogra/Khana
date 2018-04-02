package com.example.tarun.khana;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
    private class SeekerClickedTodaysFoodInfoHolder{
        ImageView currentClickedFoodImage;
        TextView currentClickedFoodName,currentClickedFoodPrice, currentClickedFoodQuantity, providerAddress,descriptionOfTheFood ;
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
        final GetUserInfo currentUserInfo =  intent.getParcelable("current_user_info");
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
        seekerClickedTodaysFoodInfoHolder.quantity_of_order = (ElegantNumberButton) findViewById(R.id.dish_quantity) ;
        seekerClickedTodaysFoodInfoHolder.order_now = (Button) findViewById(R.id.orderNowButton);
        seekerClickedTodaysFoodInfoHolder.add_to_cart = (Button) findViewById(R.id.addToCart);

        //Setting all the views here
        //Image of the current clicked food
        Glide.with(SeekerClickedTodaysFoodInfo.this).load(var.urlOfTodaysFoodImage.get(list.get(i).user_name+"_"+list.get(i).dish_name)).into(seekerClickedTodaysFoodInfoHolder.currentClickedFoodImage);
        image = var.urlOfTodaysFoodImage.get(list.get(i).user_name+"_"+list.get(i).dish_name);
        //Name,quantity,price,address of the current clicked food
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodName.setText(list.get(i).dish_name);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodQuantity.setText(list.get(i).dish_quantity);
        seekerClickedTodaysFoodInfoHolder.currentClickedFoodPrice.setText("$"+list.get(i).dish_price);
        seekerClickedTodaysFoodInfoHolder.providerAddress.setText(list.get(i).provider_address);
        seekerClickedTodaysFoodInfoHolder.descriptionOfTheFood.setText("This is as delicious as it looks in the photo. It is "+list.get(i).dist_type+" dish coocked properly and is mouthwatering taste.It is "+list.get(i).dish_spiciness+".");

        seekerClickedTodaysFoodInfoHolder.quantity_of_order.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity_number =  seekerClickedTodaysFoodInfoHolder.quantity_of_order.getNumber();
            }
        });
        seekerClickedTodaysFoodInfoHolder.order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(quantity_number) > Integer.parseInt(list.get(i).dish_quantity)){
                    Toast.makeText(SeekerClickedTodaysFoodInfo.this, "Max quantity allowed"+list.get(i).dish_quantity, Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(quantity_number) == 0){
                    Toast.makeText(SeekerClickedTodaysFoodInfo.this, "Select some quantity", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent1 = new Intent(SeekerClickedTodaysFoodInfo.this,SeekerOrderNow.class);
                    intent1.putExtra("food info",(Serializable) list.get(i));
                    intent1.putExtra("image",image);
                    intent1.putExtra("current_user_info",currentUserInfo);
                    intent1.putExtra("order_quantity",quantity_number);
                    startActivity(intent1);
                }

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seeker_home, menu);
        return true;
    }
}
