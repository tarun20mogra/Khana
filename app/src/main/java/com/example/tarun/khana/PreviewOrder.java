package com.example.tarun.khana;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class PreviewOrder extends AppCompatActivity {
    Bitmap bitmap;
    String user_name;
    GetUserInfo userInfoLogin = new GetUserInfo();
    StorageReference storageReferenceForProvider = FirebaseStorage.getInstance().getReference();
    private ProgressDialog progressDialog;



    public class PreviewOrderHolder{
        ImageView imageView;
        TextView dish_name, dish_price, dish_quantity,dish_spiciness,dish_type, provider_address;
        Button submitOrder;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setting the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_order);
        //Getting the intent value here
        final Bundle intent = getIntent().getExtras();
        final Uri uri = Uri.parse(intent.getString("image"));
        final OrderPreviewSaveInfo orderPreviewSaveInfo = (OrderPreviewSaveInfo) intent.getSerializable("preview");
        userInfoLogin = (GetUserInfo) intent.getSerializable("username");
        user_name = intent.getString("username");

        //Initializing all the variables here
        progressDialog = new ProgressDialog(PreviewOrder.this);
        PreviewOrderHolder previewOrderHolder = new PreviewOrderHolder();
        previewOrderHolder.imageView = (ImageView) findViewById(R.id.orderPhoto);
        previewOrderHolder.dish_name = (TextView) findViewById(R.id.dishName);
        previewOrderHolder.dish_price = (TextView) findViewById(R.id.dishPrice);
        previewOrderHolder.dish_quantity = (TextView) findViewById(R.id.dishQuantity);
        previewOrderHolder.provider_address = (TextView) findViewById(R.id.providerAddress);
        previewOrderHolder.submitOrder = (Button) findViewById(R.id.submitProviderOrder);
        previewOrderHolder.dish_spiciness = (TextView) findViewById(R.id.spiciness);
        previewOrderHolder.dish_type = (TextView) findViewById(R.id.dishType);
        // Setting the image view from the URI obtained from Intent
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            previewOrderHolder.imageView.setImageBitmap(bitmap);
            previewOrderHolder.imageView.setRotation(90);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Setting all the fields of the activity
        previewOrderHolder.dish_name.setText(orderPreviewSaveInfo.dish_name);
        previewOrderHolder.dish_quantity.setText(orderPreviewSaveInfo.dish_quantity);
        previewOrderHolder.dish_price.setText(orderPreviewSaveInfo.dish_price);
        previewOrderHolder.provider_address.setText(orderPreviewSaveInfo.provider_address);
        previewOrderHolder.dish_spiciness.setText(orderPreviewSaveInfo.dish_spiciness);
        previewOrderHolder.dish_type.setText(orderPreviewSaveInfo.dist_type);

        previewOrderHolder.submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReferenceForUser = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/providersFoodInfo/"+userInfoLogin.user_name);
                StorageReference filepath = storageReferenceForProvider.child(userInfoLogin.user_name).child(orderPreviewSaveInfo.dish_name+"_"+orderPreviewSaveInfo.today_date);
                final DatabaseReference generalDatabaseRefrence = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/TodaysFood/");
                progressDialog.setMessage("Uploading...");
                progressDialog.show();

                //Saving the data for providers only
                databaseReferenceForUser.push().setValue(new OrderPreviewSaveInfo(orderPreviewSaveInfo.dish_name,orderPreviewSaveInfo.dish_price,orderPreviewSaveInfo.dish_quantity,orderPreviewSaveInfo.provider_address,orderPreviewSaveInfo.dist_type,orderPreviewSaveInfo.dish_spiciness,orderPreviewSaveInfo.today_date));
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.v("upload done","upload done");
                    }
                });
                //Saving the food for today in different section
                generalDatabaseRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        generalDatabaseRefrence.child(orderPreviewSaveInfo.today_date).child(userInfoLogin.user_name+"_"+orderPreviewSaveInfo.dish_name).setValue(new OrderPreviewSaveInfo(orderPreviewSaveInfo.dish_name,orderPreviewSaveInfo.dish_price,orderPreviewSaveInfo.dish_quantity,orderPreviewSaveInfo.provider_address,orderPreviewSaveInfo.dist_type,orderPreviewSaveInfo.dish_spiciness,orderPreviewSaveInfo.today_date));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                StorageReference filepathToTodaysFood = storageReferenceForProvider.child("Todays Food").child(orderPreviewSaveInfo.today_date).child(userInfoLogin.user_name+"_"+orderPreviewSaveInfo.dish_name);
                filepathToTodaysFood.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Intent intent2 = new Intent(PreviewOrder.this,ProviderHome.class);
                        intent2.putExtra("username",userInfoLogin);
                        startActivity(intent2);

                    }
                });





            }
        });
    }


}
