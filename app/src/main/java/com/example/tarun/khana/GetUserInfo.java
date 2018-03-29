package com.example.tarun.khana;


import java.io.Serializable;

public class GetUserInfo implements Serializable{
    String user_email;
    String user_password;
    String user_name;
    String user_address;
    String user_phone;
    String user_fullName;
    String getPassWord(){
        return user_password;
    }

}
