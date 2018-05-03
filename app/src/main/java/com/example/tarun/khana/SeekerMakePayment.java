package com.example.tarun.khana;


import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;

import java.util.Date;


public class SeekerMakePayment extends AppCompatActivity {

    private Singleton var = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_make_payment);
        //intent catch
        Bundle intent = getIntent().getExtras();


        CardForm cardForm = (CardForm) findViewById(R.id.paymentCardForm);
        TextView textView = (TextView) findViewById(R.id.payment_amount);
        TextView backbutton = (TextView) findViewById(R.id.backButton);
        Button button =(Button) findViewById(R.id.btn_pay);
        button.setBackgroundResource(R.drawable.preview_button);
        button.setText(R.string.payment);
        textView.setText(Double.toString(var.price));

        //permission for the phone to send message

        if(ContextCompat.checkSelfPermission(SeekerMakePayment.this, android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(SeekerMakePayment.this, android.Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(SeekerMakePayment.this,new String[]{android.Manifest.permission.SEND_SMS},1);
            } else {
                ActivityCompat.requestPermissions(SeekerMakePayment.this,new String[]{android.Manifest.permission.SEND_SMS},1);

            }
        } else {
            //do nothing
        }


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeekerMakePayment.super.onBackPressed();

            }
        });

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                //--------------------------------------------------------------------------------------------------------------------------------//
                //Setting todays date
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
                final Date now = new Date();
                final String strDate = sdfDate.format(now);
                Toast.makeText(SeekerMakePayment.this, "Payment Made", Toast.LENGTH_SHORT).show();
                // Saving the orders to user history
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("TodaysFood").child(strDate);

                final DatabaseReference seekerFoodOrderHistory = FirebaseDatabase.getInstance().getReference();
                for( int i =0; i<var.cartFoodInfo.size();i++ ){
                    final int position = i;
                    final String quantity = var.quantity.get(i);
                    seekerFoodOrderHistory.child("Seeker_History").child(var.getUserInfo.user_name).push().setValue(new SaveSeekerOrderFood(var.cartFoodInfo.get(i).dish_name,var.quantity.get(i),var.cartFoodInfo.get(i).dish_price,strDate));
                    databaseReference.child(var.cartFoodInfo.get(i).user_name+"_"+var.cartFoodInfo.get(i).dish_name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String previousQuantity = dataSnapshot.child("dish_quantity").getValue(String.class);
                            Log.v("previous quantity",""+previousQuantity);
                            Log.v("quantity ordered",""+quantity);
                            int newQuantity = Integer.parseInt(previousQuantity) - Integer.parseInt(quantity);
                            databaseReference.child(var.cartFoodInfo.get(position).user_name+"_"+var.cartFoodInfo.get(position).dish_name).child("dish_quantity").setValue(String.valueOf(newQuantity));

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                //-----------------------------------------------------Sending Message----------------------------------------------------------------------------//
                try {
                    String messageToSend = "You have an order from "+var.getUserInfo.user_fullName+". Your food is rolling out.";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("7329251694",null,messageToSend,null,null);
                    Toast.makeText(SeekerMakePayment.this, "Message Sent", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(SeekerMakePayment.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(SeekerMakePayment.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No permission Granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
