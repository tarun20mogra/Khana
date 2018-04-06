package com.example.tarun.khana;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProviderHistory extends AppCompatActivity {
    ArrayList<SeekerGetTodayFoodInfo> seekerGetTodayFoodInfo = new ArrayList<>();
    GetUserInfo currentUserInfo;
    RecyclerView historyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_history);
        Bundle intent = getIntent().getExtras();
        currentUserInfo = (GetUserInfo) intent.getSerializable("userName");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/providersFoodInfo").child(currentUserInfo.user_name);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.v("datasnapshot",""+dataSnapshot.getValue());
                SeekerGetTodayFoodInfo obj = dataSnapshot.getValue(SeekerGetTodayFoodInfo.class);
                seekerGetTodayFoodInfo.add(obj);
                listViewAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
                //----------------------------------------RecyclerView --------------------------------------------//

    }

    private void listViewAdapter() {
        historyView = (RecyclerView) findViewById(R.id.providerHistoryRecyclerView);
        ProviderHistoryListAdapter providerHistoryListAdapter = new ProviderHistoryListAdapter(ProviderHistory.this,currentUserInfo,seekerGetTodayFoodInfo);
        historyView.setLayoutManager(new LinearLayoutManager(ProviderHistory.this));
        historyView.setNestedScrollingEnabled(false);
        historyView.setAdapter(providerHistoryListAdapter);
    }
}
