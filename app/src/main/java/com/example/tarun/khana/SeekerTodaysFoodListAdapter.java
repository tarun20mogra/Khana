package com.example.tarun.khana;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SeekerTodaysFoodListAdapter extends RecyclerView.Adapter<SeekerTodaysFoodListAdapter.TodaysFoodListViewHolder> {

    //arraylist of all the food within the some distance only
    private ArrayList<SeekerGetTodayFoodInfo> todayFoodInfo = new ArrayList<>();
    //List view activity Context to inflate the card view
    private SeekerHome context;
    //Storage reference
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference filepath;
    //for firebase authentication
    //Current user properties
    GetUserInfo currentUserInfo;
    //Arraylist of all the uri
    ArrayList<String> urls = new ArrayList<>();
    private Singleton var = Singleton.getInstance();

    public SeekerTodaysFoodListAdapter(SeekerHome seekerHome, ArrayList<SeekerGetTodayFoodInfo> seekerGetTodayFoodInfo, GetUserInfo getUserInfo) {
        todayFoodInfo = seekerGetTodayFoodInfo;
        context = seekerHome;
        currentUserInfo = getUserInfo;

    }

    @NonNull
    @Override
    public TodaysFoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.todays_food_card_list_view, parent, false);
        return new TodaysFoodListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodaysFoodListViewHolder holder, final int position) {
        //Setting todays date
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        final Date now = new Date();
        final String strDate = sdfDate.format(now);
        holder.foodPrice.setText("$" + todayFoodInfo.get(position).dish_price);
        holder.todaysFoodName.setText(todayFoodInfo.get(position).dish_name);
        //Getthig the image of the food
        filepath = storageReference.child("Todays Food").child(strDate).child(todayFoodInfo.get(position).user_name + "_" + todayFoodInfo.get(position).dish_name);
        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri.toString()).into(holder.todaysFoodImage);
                urls.add(uri.toString());
                var.urlOfTodaysFoodImage.put(todayFoodInfo.get(position).user_name + "_" + todayFoodInfo.get(position).dish_name, uri.toString());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeekerClickedTodaysFoodInfo.class);
                intent.putExtra("current_user_info", currentUserInfo);

                intent.putExtra("current_value", position);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return todayFoodInfo.size();
    }


    public static class TodaysFoodListViewHolder extends RecyclerView.ViewHolder {
        ImageView todaysFoodImage;
        TextView todaysFoodName;
        TextView foodPrice;

        public TodaysFoodListViewHolder(View itemView) {
            super(itemView);
            todaysFoodImage = (ImageView) itemView.findViewById(R.id.todaysFoodImage);
            todaysFoodName = (TextView) itemView.findViewById(R.id.todaysFoodName);
            foodPrice = (TextView) itemView.findViewById(R.id.foodPrice);


        }
    }
}
