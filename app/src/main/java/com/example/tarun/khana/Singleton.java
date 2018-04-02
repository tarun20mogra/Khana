package com.example.tarun.khana;


import java.util.ArrayList;
import java.util.HashMap;

public class Singleton {
    private static final Singleton instance = new Singleton();
   // ArrayList<String> urlOfTodaysFoodImage = new ArrayList<>();
    HashMap<String,String> urlOfTodaysFoodImage = new HashMap<>();
    private Singleton(){}
    public static Singleton getInstance(){
        return instance;
    }

}
