package com.example.tarun.khana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SeekerHistory extends AppCompatActivity {
    private Singleton var = Singleton.getInstance();
    ArrayList<GetSeekerHistoryInfo> getSeekerHistoryInfos = new ArrayList<>();
    RecyclerView seekerHistroyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_history);
        TextView backButton = (TextView) findViewById(R.id.backButton);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/Seeker_History").child(var.getUserInfo.user_name);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GetSeekerHistoryInfo obj = dataSnapshot.getValue(GetSeekerHistoryInfo.class);
                getSeekerHistoryInfos.add(obj);
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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(SeekerHistory.this,SeekerHome.class);
                intent1.putExtra("username",var.getUserInfo);
                startActivity(intent1);
            }
        });

    }
    private void listViewAdapter() {
        seekerHistroyView = (RecyclerView) findViewById(R.id.seekeHistoryList);
        SeekerHistoryListAdapter seekerHistoryListAdapter = new SeekerHistoryListAdapter(SeekerHistory.this,getSeekerHistoryInfos);
        seekerHistroyView.setLayoutManager(new LinearLayoutManager(SeekerHistory.this));
        seekerHistroyView.setNestedScrollingEnabled(false);
        seekerHistroyView.setAdapter(seekerHistoryListAdapter);
    }
}
