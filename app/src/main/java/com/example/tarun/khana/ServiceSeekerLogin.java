package com.example.tarun.khana;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;


public class ServiceSeekerLogin extends Fragment {
    String userNameSeeker = null, passwordSeeker = null;
    ServiceSeekerLogin context = this;
    GetUserInfo userInfo [] = null;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/userInfo/seeker");
    private boolean flag = false;


    private class SeekerLoginHolder{
        EditText seekerUserName;
        EditText seekerPassword;
        Button loginSeeker;
        Button signUpSeeker;
        TextView forgotPasswordSeeker;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.service_seeker, container, false);
        final SeekerLoginHolder seekerLoginHolder = new SeekerLoginHolder();


        seekerLoginHolder.seekerUserName = (EditText) rootView.findViewById(R.id.seekerUserName);
        seekerLoginHolder.seekerPassword = (EditText) rootView.findViewById(R.id.seekerPassword);
        seekerLoginHolder.loginSeeker = (Button) rootView.findViewById(R.id.loginSeekerButton);
        seekerLoginHolder.signUpSeeker = (Button) rootView.findViewById(R.id.seekerSignUpButton);
        seekerLoginHolder.forgotPasswordSeeker = (TextView) rootView.findViewById(R.id.forgotPasswordSeeker);

        seekerLoginHolder.loginSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userNameSeeker = String.valueOf(seekerLoginHolder.seekerUserName.getText());
                passwordSeeker = String.valueOf(seekerLoginHolder.seekerPassword.getText());
                if(userNameSeeker.isEmpty() || passwordSeeker.isEmpty()){
                    Toast.makeText(context.getActivity(), "Invalid username or password", Toast.LENGTH_LONG).show();
                }
                else
                {

                    signIn(userNameSeeker,passwordSeeker);
                }
            }

        });

        seekerLoginHolder.forgotPasswordSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),ForgotPassword.class);
                //intent.putExtra("type","Seeker");
                startActivity(intent);

            }
        });

        seekerLoginHolder.signUpSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SignUp.class);
                intent.putExtra("type","seeker");
                startActivity(intent);


            }
        });
        return rootView;
    }

    private void signIn(final String userNameSeeker, final String passwordSeeker) {

    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.child(userNameSeeker).exists()){
                if(!userNameSeeker.isEmpty()){
                    Log.v("Exixts:",""+dataSnapshot.child(userNameSeeker).getValue());
                    GetUserInfo login = dataSnapshot.child(userNameSeeker).getValue(GetUserInfo.class);
                    if(login.getPassWord().equals(passwordSeeker)){
                        Intent intent = new Intent(getActivity(), SeekerHome.class);
                        intent.putExtra("username",login);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(getActivity(), "Invalid User", Toast.LENGTH_SHORT).show();
                    }

                }
            }else{
                Toast.makeText(getActivity(), "Username is not registered", Toast.LENGTH_SHORT).show();}
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    });
    }

}



