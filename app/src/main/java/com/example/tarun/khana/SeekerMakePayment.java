package com.example.tarun.khana;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Singleton var = Singleton.getInstance();
    String phoneNo = "7329251694";
    String sms = "Hi there";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_make_payment);
        //intent catch
        Bundle intent = getIntent().getExtras();
        double totalAmount = (double) intent.get("payment Price");
        CardForm cardForm = (CardForm) findViewById(R.id.paymentCardForm);
        TextView textView = (TextView) findViewById(R.id.payment_amount);
        TextView backbutton = (TextView) findViewById(R.id.backButton);
        Button button =(Button) findViewById(R.id.btn_pay);
        button.setBackgroundResource(R.drawable.preview_button);
        button.setText(R.string.payment);
        textView.setText(Double.toString(totalAmount));

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeekerMakePayment.super.onBackPressed();

            }
        });

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                //Setting todays date
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
                final Date now = new Date();
                final String strDate = sdfDate.format(now);
                Toast.makeText(SeekerMakePayment.this, "Payment Made", Toast.LENGTH_SHORT).show();
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

                if (ContextCompat.checkSelfPermission(SeekerMakePayment.this,
                        android.Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SeekerMakePayment.this,
                            android.Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(SeekerMakePayment.this,
                                new String[]{android.Manifest.permission.SEND_SMS},
                                PERMISSION_REQUEST_CODE);
                    }
                }

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Some error occurred try again after sometime.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }}
}
