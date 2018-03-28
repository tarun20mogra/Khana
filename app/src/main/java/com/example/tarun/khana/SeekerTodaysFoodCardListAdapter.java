package com.example.tarun.khana;


import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class SeekerTodaysFoodCardListAdapter extends BaseAdapter {
    //arraylist of all the food within the some distance only
    private ArrayList<SeekerGetTodayFoodInfo> todayFoodInfo = new ArrayList<>();
    //List view activity Context to inflate the card view
    private SeekerHome context;
    //Storage reference
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference filepath;
    //for firebase authentication
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //Bitmap to load image
    Bitmap bitmap;
    //Current user properties
    GetUserInfo currentUserInfo;
    //Arraylist of all the uri
    ArrayList<String> urls = new ArrayList<>();

    private static LayoutInflater inflater = null;
    public SeekerTodaysFoodCardListAdapter(SeekerHome seekerHome,ArrayList<SeekerGetTodayFoodInfo> seekerGetTodayFoodInfo,GetUserInfo getUserInfo){
        todayFoodInfo = seekerGetTodayFoodInfo;
        context = seekerHome;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        currentUserInfo = getUserInfo;

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
    //Class that hold all the variables of a single card
    private class SeekerTodaysFoodInfoHolder{
        ImageView todaysFoodImage;
        TextView todaysFoodName;
        TextView foodPrice;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        FirebaseUser user = mAuth.getCurrentUser();

        //View for this activity
        final View todaysFoodView;
        //Holder class initializing
        final SeekerTodaysFoodInfoHolder seekerTodaysFoodInfoHolder = new SeekerTodaysFoodInfoHolder();
        //Inflating the view
        todaysFoodView = inflater.inflate(R.layout.todays_food_card_list_view,null);
        //initializing all the variables of holder class
        seekerTodaysFoodInfoHolder.todaysFoodImage = (ImageView) todaysFoodView.findViewById(R.id.todaysFoodImage);
        seekerTodaysFoodInfoHolder.todaysFoodName = (TextView) todaysFoodView.findViewById(R.id.todaysFoodName);
        seekerTodaysFoodInfoHolder.foodPrice = (TextView) todaysFoodView.findViewById(R.id.foodPrice);
        //Setting the text for food name and price
        seekerTodaysFoodInfoHolder.todaysFoodName.setText(todayFoodInfo.get(i).dish_name);
        seekerTodaysFoodInfoHolder.foodPrice.setText("$"+todayFoodInfo.get(i).dish_price);

        //Getthig the image of the food
        filepath = storageReference.child("Todays Food").child("2018-03-23").child(todayFoodInfo.get(i).user_name+"_"+todayFoodInfo.get(i).dish_name);
        Log.v("filepath",""+filepath.toString());
        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.v("uri is",""+uri.toString());
                Glide.with(context).load(uri.toString()).into(seekerTodaysFoodInfoHolder.todaysFoodImage);
                urls.add(uri.toString());
            }
        });
        todaysFoodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SeekerClickedTodaysFoodInfo.class);
                intent.putExtra("current_user_info",currentUserInfo);
//                intent.putExtra("todays_food_info", (Parcelable) todayFoodInfo.get(i));
                intent.putExtra("image_of_food_clicked",urls.get(i));
                context.startActivity(intent);

            }
        });
        return todaysFoodView;
    }
}
