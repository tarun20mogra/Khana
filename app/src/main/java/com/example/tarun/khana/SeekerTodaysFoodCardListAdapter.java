package com.example.tarun.khana;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SeekerTodaysFoodCardListAdapter extends BaseAdapter {
    ArrayList<SeekerGetTodayFoodInfo> todayFoodInfo = new ArrayList<>();
    SeekerHome context;
    private static LayoutInflater inflater = null;
    public SeekerTodaysFoodCardListAdapter(SeekerHome seekerHome,ArrayList<SeekerGetTodayFoodInfo> seekerGetTodayFoodInfo){
        todayFoodInfo = seekerGetTodayFoodInfo;
        context = seekerHome;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return todayFoodInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class SeekerTodaysFoodInfoHolder{
        ImageView todaysFoodImage;
        TextView todaysFoodName;
        TextView foodPrice;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View todaysFoodView;
        SeekerTodaysFoodInfoHolder seekerTodaysFoodInfoHolder = new SeekerTodaysFoodInfoHolder();
        todaysFoodView = inflater.inflate(R.layout.todays_food_card_list_view,null);
        seekerTodaysFoodInfoHolder.todaysFoodImage = (ImageView) todaysFoodView.findViewById(R.id.todaysFoodImage);
        seekerTodaysFoodInfoHolder.todaysFoodName = (TextView) todaysFoodView.findViewById(R.id.todaysFoodName);
        seekerTodaysFoodInfoHolder.foodPrice = (TextView) todaysFoodView.findViewById(R.id.foodPrice);
        seekerTodaysFoodInfoHolder.todaysFoodName.setText(todayFoodInfo.get(i).dish_name);
        seekerTodaysFoodInfoHolder.foodPrice.setText(todayFoodInfo.get(i).dish_price);
        return todaysFoodView;
    }
}
