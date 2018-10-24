package com.techdoctorbd.locationtrackerfnf;

public class CreateUser {

    public CreateUser()
    {}

    public CreateUser(String e6_name, String email, String password, String code, String isSharing, String lat, String lng, String imageUrl, String userid) {
        this.name = e6_name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.isSharing = isSharing;
        this.lat = lat;
        this.lng = lng;
        this.userid = userid;
        this.imageUrl = imageUrl;
    }

    public String name,email,password,code,lat,lng,imageUrl,isSharing,userid;
}
