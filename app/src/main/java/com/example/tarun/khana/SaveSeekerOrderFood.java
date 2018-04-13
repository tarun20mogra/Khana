package com.example.tarun.khana;

/**
 * Created by Tarun on 4/12/2018.
 */

public class SaveSeekerOrderFood {
    public String dish_name_ordered, dish_quantity_ordered, price_paid, date_order_made;
    public SaveSeekerOrderFood(String dish_name, String quantity, String price, String date){
        dish_name_ordered = dish_name;
        dish_quantity_ordered = quantity;
        price_paid = price;
        date_order_made = date;

    }
}
