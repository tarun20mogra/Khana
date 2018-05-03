package com.example.tarun.khana;


import java.util.ArrayList;
import java.util.HashMap;

public class Singleton {
    private static final Singleton instance = new Singleton();
    HashMap<String,String> urlOfTodaysFoodImage = new HashMap<>();
    HashMap<Integer,SeekerGetTodayFoodInfo> todayFoodInfoHashMap = new HashMap<>();
    boolean onResumeFlag = false;
    double cartSubotal =0 ;
    double tax = 0;
    double price = 0;

    ArrayList<String> cartFoodImageUrl = new ArrayList<>();
    ArrayList<SeekerGetTodayFoodInfo> cartFoodInfo = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    GetUserInfo getUserInfo = new GetUserInfo();
    boolean showCart = false;
    private Singleton(){}
    public static Singleton getInstance(){
        return instance;
    }

}
