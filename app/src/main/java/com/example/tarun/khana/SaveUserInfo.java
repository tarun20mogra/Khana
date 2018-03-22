package com.example.tarun.khana;

import android.util.Log;

/**
 * Created by Tarun on 3/13/2018.
 */

public class SaveUserInfo {
    public String user_name = null;
    public String user_password = null;
    public String user_email = null;
    public String user_phone = null;
    public String user_address = null;

public SaveUserInfo(String username, String password, String email, String phone, String address){

    user_name = username;
    user_password = password;
    user_email = email;
    user_phone = phone;
    user_address = address;
    Log.v("values","{"+username+password+email+phone+address+"}");
}
}
