package com.example.tarun.khana;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ProviderHistoryListAdapter extends RecyclerView.Adapter<ProviderHistoryListAdapter.HistoryListAdapterHolder> {
    private ProviderHistory context;
    private GetUserInfo currentUserInfo;
    int count = 0;
    private ArrayList<SeekerGetTodayFoodInfo> seekerGetTodayFoodInfo = new ArrayList<>();

    public ProviderHistoryListAdapter(ProviderHistory obj, GetUserInfo useerInfo, ArrayList<SeekerGetTodayFoodInfo> getTodayFoodInfo) {
        context = obj;
        currentUserInfo = useerInfo;
        seekerGetTodayFoodInfo = getTodayFoodInfo;
    }

    @NonNull
    @Override
    public HistoryListAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.provider_history_list_card_view, parent, false);
        HistoryListAdapterHolder historyListAdapterHolder = new HistoryListAdapterHolder(v);
        return historyListAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryListAdapterHolder holder, int position) {
        holder.dishName.setText(seekerGetTodayFoodInfo.get(position).dish_name);
        holder.dishQuantity.setText(seekerGetTodayFoodInfo.get(position).dish_quantity);
        holder.date.setText(seekerGetTodayFoodInfo.get(position).today_date);

/*
        Log.v("values",""+seekerGetTodayFoodInfo.get(position).dish_name+"...."+seekerGetTodayFoodInfo.get(position).dish_quantity+"...."+seekerGetTodayFoodInfo.get(position).today_date);
*/

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference filepath = storageReference.child(currentUserInfo.user_name).child(seekerGetTodayFoodInfo.get(position).dish_name + "_" + seekerGetTodayFoodInfo.get(position).today_date);
        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.dishImage);
            }
        });


    }

    @Override
    public int getItemCount() {
        return seekerGetTodayFoodInfo.size();
    }

    public static class HistoryListAdapterHolder extends RecyclerView.ViewHolder {
        TextView dishName, dishQuantity, date;
        ImageView dishImage;

        public HistoryListAdapterHolder(View itemView) {
            super(itemView);
            dishImage = (ImageView) itemView.findViewById(R.id.image);
            dishName = (TextView) itemView.findViewById(R.id.dishNameHistory);
            dishQuantity = (TextView) itemView.findViewById(R.id.quantityHistory);
            date = (TextView) itemView.findViewById(R.id.dateHistory);
        }
    }

}
