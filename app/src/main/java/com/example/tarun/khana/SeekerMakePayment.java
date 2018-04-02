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

public class SeekerMakePayment extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

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
        Button button =(Button) findViewById(R.id.btn_pay);
        button.setBackgroundResource(R.drawable.preview_button);
        button.setText(R.string.payment);
        textView.setText(Double.toString(totalAmount));

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(SeekerMakePayment.this, "Payment Made", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(), "Order Placed",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Some error occurred try again after sometime.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }}
}
