package com.example.tarun.khana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    private String type = null;
    private class ForgotPasswordPlaceHolder{
        EditText emailForgotPassword;
        RadioGroup radioGroup;
        Button submitEmail;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Intent intent = getIntent();
        ForgotPasswordPlaceHolder forgotPasswordPlaceHolder = new ForgotPasswordPlaceHolder();

        forgotPasswordPlaceHolder.submitEmail = (Button) findViewById(R.id.submitEmail);
        forgotPasswordPlaceHolder.radioGroup = (RadioGroup) findViewById(R.id.RGroup);
        forgotPasswordPlaceHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioButtonSeeker){
                    type = "Seeker";

                }
                else if(i == R.id.radioButtonProvider){
                    type = "Provider";
                }
            }
        });

        forgotPasswordPlaceHolder.submitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForgotPassword.this, ""+type, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
