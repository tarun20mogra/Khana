package com.example.tarun.khana;


import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class SeekerGetTodayFoodInfo implements Serializable {
    public String dish_name;
    public  String dish_quantity;
    public String dish_price;
    public String dist_type;
    public String dish_spiciness;
    public String provider_address;
    public String today_date;
    public String user_name;
}
