package com.example.tarun.khana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    SignUp context = this;
    String user_name;
    String user_password;
    String user_email;
    String user_phone;
    String user_address;
    String userType;
    String user_fullName;
    private class SignUpHolder{
        EditText userName,password,emailAddress,phone,userAddressLine1,userAddressLine2,userCity,userCountry,userZipCode, userFullName;
        Button signUp;
        TextView loginBack;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final SignUpHolder signUpHolder = new SignUpHolder();
        Intent intent = getIntent();
        userType= intent.getStringExtra("type");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReferenceFromUrl("https://khana-7272.firebaseio.com/userInfo/"+userType+"/");
        final ArrayList<SaveUserInfo> saveUserInfo= new ArrayList<>();
        //initializing all the variables
        signUpHolder.userName =(EditText) findViewById(R.id.userNameInput);
        signUpHolder.password =(EditText) findViewById(R.id.passwordInput);
        signUpHolder.emailAddress =(EditText) findViewById(R.id.emailInput);
        signUpHolder.phone =(EditText) findViewById(R.id.phoneInput);
        signUpHolder.userAddressLine1 =(EditText) findViewById(R.id.userAddressLine1);
        signUpHolder.userAddressLine2 =(EditText) findViewById(R.id.userAddressLine2);
        signUpHolder.userCity =(EditText) findViewById(R.id.userCity);
        signUpHolder.userCountry =(EditText) findViewById(R.id.userCountry);
        signUpHolder.userZipCode =(EditText) findViewById(R.id.userZipCode);
        signUpHolder.signUp = (Button) findViewById(R.id.signUpButton);
        signUpHolder.loginBack = (TextView) findViewById(R.id.loginBack);
        signUpHolder.userFullName = (EditText) findViewById(R.id.fullNameInput);


        //setting on click listner and saving the data to database
        signUpHolder.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name = String.valueOf(signUpHolder.userName.getText());
                user_password = String.valueOf(signUpHolder.password.getText());
                user_email = String.valueOf(signUpHolder.emailAddress.getText());
                user_phone = String.valueOf(signUpHolder.phone.getText());
                user_fullName = String.valueOf(signUpHolder.userFullName.getText());
                if(!String.valueOf(signUpHolder.userAddressLine2.getText()).isEmpty())
                {
                    if(!String.valueOf(signUpHolder.userAddressLine1.getText()).isEmpty() && !String.valueOf(signUpHolder.userCity.getText()).isEmpty() && !String.valueOf(signUpHolder.userZipCode.getText()).isEmpty() && !String.valueOf(signUpHolder.userCountry.getText()).isEmpty())
                    {
                        user_address = String.valueOf(signUpHolder.userAddressLine1.getText())+","+String.valueOf(signUpHolder.userAddressLine2.getText())+","+String.valueOf(signUpHolder.userCity.getText())+","+String.valueOf(signUpHolder.userZipCode.getText())+","+String.valueOf(signUpHolder.userCountry.getText());

                    }
                    else {
                        user_address = null;
                    }
                }
                else if(String.valueOf(signUpHolder.userAddressLine2.getText()).isEmpty())
                {
                    if(!String.valueOf(signUpHolder.userAddressLine1.getText()).isEmpty() && !String.valueOf(signUpHolder.userCity.getText()).isEmpty() && !String.valueOf(signUpHolder.userZipCode.getText()).isEmpty() && !String.valueOf(signUpHolder.userCountry.getText()).isEmpty())
                    {
                        user_address = String.valueOf(signUpHolder.userAddressLine1.getText())+","+String.valueOf(signUpHolder.userCity.getText())+","+String.valueOf(signUpHolder.userZipCode.getText())+","+String.valueOf(signUpHolder.userCountry.getText());

                    }
                    else {
                        user_address = null;
                    }
                }
                if(user_fullName.isEmpty() ||user_name.isEmpty() || user_password.isEmpty() || user_email.isEmpty() || user_phone.isEmpty() || user_address == null){
                    Toast.makeText(context, "Values can not be left empty", Toast.LENGTH_LONG).show();
                }
                else {

                    //saveUserInfo.add(new SaveUserInfo(user_name,user_password,user_email,user_phone,user_address));
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(user_name).exists()){
                                Toast.makeText(context, "username already exists", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                ref.child(user_name).setValue(new SaveUserInfo(user_name,user_password,user_email,user_phone,user_address,user_fullName));
                                Intent intent1 = new Intent(context,MainActivity.class);
                                startActivity(intent1);
                                Toast.makeText(context, " Sign up Successfull ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }



            }
        });

        signUpHolder.loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(context,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
