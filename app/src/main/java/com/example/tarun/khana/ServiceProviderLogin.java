package com.example.tarun.khana;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;


public class ServiceProviderLogin extends Fragment {
    ServiceProviderLogin context = this;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://khana-7272.firebaseio.com/userInfo/provider");

    private class ProviderLoginHolder{
        EditText providerUserName;
        EditText providerPassword;
        Button loginProvider;
        Button signUpProvider;
        TextView forgotPasswordProvider;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.service_provider, container, false);
        final ProviderLoginHolder providerLoginHolder = new ProviderLoginHolder();

        //initializing all the variables of the providerLoginHolder
        providerLoginHolder.providerUserName = (EditText) rootView.findViewById(R.id.providerUserName);
        providerLoginHolder.providerPassword = (EditText) rootView.findViewById(R.id.providerPassword);
        providerLoginHolder.loginProvider = (Button) rootView.findViewById(R.id.loginProviderButton);
        providerLoginHolder.signUpProvider = (Button) rootView.findViewById(R.id.providerSignUpButton);
        providerLoginHolder.forgotPasswordProvider = (TextView) rootView.findViewById(R.id.forgotPasswordProvider);


        providerLoginHolder.loginProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(providerLoginHolder.providerUserName.getText()).isEmpty() || String.valueOf(providerLoginHolder.providerPassword.getText()).isEmpty()){
                    Toast.makeText(context.getActivity(), "Invalid username or password", Toast.LENGTH_LONG).show();
                }
                else
                {
                    signIn(String.valueOf(providerLoginHolder.providerUserName.getText()),String.valueOf(providerLoginHolder.providerPassword.getText()));
                }
            }
        });

        providerLoginHolder.forgotPasswordProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ForgotPassword.class);
                //intent.putExtra("type","Seeker");
                startActivity(intent);


            }
        });

        providerLoginHolder.signUpProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SignUp.class);
                intent.putExtra("type","provider");
                startActivity(intent);

            }
        });


        return rootView;
    }

    private void signIn(final String userName, final String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(userName).exists()){
                    if(!userName.isEmpty()){
                        Log.v("Exixts:",""+dataSnapshot.child(userName).getValue());
                        GetUserInfo login = dataSnapshot.child(userName).getValue(GetUserInfo.class);
                        if(login.getPassWord().equals(password)){
                            Intent intent1 = new Intent(getActivity(),ProviderHome.class);
                            intent1.putExtra("username", login);

                            startActivity(intent1);

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
