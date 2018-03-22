package com.example.tarun.khana;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Tarun on 3/15/2018.
 */

public class OrderPreviewSaveInfo implements Serializable {
    String dish_name, dish_price, dish_quantity, provider_address, dist_type, dish_spiciness, today_date;

    public OrderPreviewSaveInfo(String name, String price, String quantity, String address, String type, String spiciness, String date){
        dish_name = name;
        dish_price = price;
        dish_quantity = quantity;
        provider_address = address;
        dist_type = type;
        dish_spiciness = spiciness;
        today_date = date;

    }

}
