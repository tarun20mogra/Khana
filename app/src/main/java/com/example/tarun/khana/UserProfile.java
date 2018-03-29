package com.example.tarun.khana;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.Serializable;

public class UserProfile extends AppCompatActivity {
    public class UserProfileHolder{
        ImageView profilePhoto;
        TextView userName, fullName, userEmail, userPhone, userType;
        Button saveInfo;
    }
    //Global Variables
    Bitmap bitmap = null;
    private final static int GALLARY_INTENT = 2;
    Uri uri;
    final UserProfileHolder userProfileHolder = new UserProfileHolder();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    GetUserInfo userInfoLogin = new GetUserInfo();
    private ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final Bundle intent = getIntent().getExtras();
        userInfoLogin = (GetUserInfo) intent.getSerializable("userProfile");
        final String userType = intent.getString("user_is");
        final StorageReference filepath = storageReference.child("user_profile_photos").child(userType).child(userInfoLogin.user_name).child("photo");
        progressDialog = new ProgressDialog(UserProfile.this);
        //Holder class initialization for all the view variables
        userProfileHolder.fullName = (TextView) findViewById(R.id.fullName);
        userProfileHolder.userName = (TextView) findViewById(R.id.username);
        userProfileHolder.userEmail = (TextView) findViewById(R.id.useremail);
        userProfileHolder.userPhone = (TextView) findViewById(R.id.userphone);
        userProfileHolder.userType = (TextView) findViewById(R.id.userType);
        userProfileHolder.profilePhoto = (ImageView) findViewById(R.id.profilePhoto);
        userProfileHolder.saveInfo = (Button) findViewById(R.id.saveInfo);
        //Setting the view for all the variables
        userProfileHolder.userName.setText(userInfoLogin.user_name);
        userProfileHolder.userEmail.setText(userInfoLogin.user_email);
        userProfileHolder.userPhone.setText(userInfoLogin.user_phone);
        userProfileHolder.userType.setText("Your are a "+userType);
        userProfileHolder.fullName.setText(userInfoLogin.user_fullName);
        //If profile photo exists
        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               /* try{bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                Bitmap conv_bm = getRoundedRectBitmap(resized, 100);
                userProfileHolder.profilePhoto.setImageBitmap(conv_bm);}catch (IOException e) {
                    e.printStackTrace();
                }*/
                //Glide.with(UserProfile.this).load(uri.toString()).into(userProfileHolder.profilePhoto);
                Glide.with(UserProfile.this).load(uri.toString()).apply(RequestOptions.circleCropTransform()).into(userProfileHolder.profilePhoto);
            }
        });

        //If user clicks to change the profile photo
        userProfileHolder.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1,GALLARY_INTENT);


            }
        });
        //Save the information edited
        userProfileHolder.saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Saving Info...");
                progressDialog.show();
                if(userType.equals("Seeker")){
                    if(uri == null)
                    {
                        Toast.makeText(UserProfile.this, "Information Saved", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UserProfile.this,SeekerHome.class);
                        intent1.putExtra("username",(Serializable) userInfoLogin);
                        progressDialog.dismiss();
                        startActivity(intent1);

                    }
                    else {
                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(UserProfile.this, "Information Saved", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(UserProfile.this,SeekerHome.class);
                                intent1.putExtra("username",(Serializable) userInfoLogin);
                                progressDialog.dismiss();
                                startActivity(intent1);

                            }
                        });

                    }

                }
                else if(userType.equals("Provider")){
                    if(uri==null){
                        Toast.makeText(UserProfile.this, "Information Saved", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UserProfile.this,ProviderHome.class);
                        intent1.putExtra("username",(Serializable) userInfoLogin);
                        progressDialog.dismiss();
                        startActivity(intent1);
                    }
                    else {
                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(UserProfile.this, "Information Saved", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(UserProfile.this,SeekerHome.class);
                                intent1.putExtra("username",(Serializable) userInfoLogin);
                                progressDialog.dismiss();
                                startActivity(intent1);
                            }
                        });

                    }


                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        uri = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            Bitmap conv_bm = getRoundedRectBitmap(resized, 100);
            userProfileHolder.profilePhoto.setImageBitmap(conv_bm);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
             result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);
            int roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (NullPointerException e) {
// return bitmap;
        } catch (OutOfMemoryError o){}
        return result;
    }
}
