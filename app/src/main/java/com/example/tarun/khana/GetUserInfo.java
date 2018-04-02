package com.example.tarun.khana;


import java.io.Serializable;

public class GetUserInfo implements Serializable{
    public String user_email;
    public String user_password;
    public String user_name;
    public String user_address;
    public String user_phone;
    public String user_fullName;
    public String getPassWord(){
        return user_password;
    }

}
