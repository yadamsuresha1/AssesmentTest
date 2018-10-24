package com.autosenseindia.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable {

    private String name, designation, city, pincode, dateOfJoin, salary;

    private Bitmap userPhoto;
    private String photoTimeStamp;

    public User() {
    }

    public User(String name, String designation, String city, String pincode, String dateOfJoin, String salary) {
        this.name = name;
        this.designation = designation;
        this.city = city;
        this.pincode = pincode;
        this.dateOfJoin = dateOfJoin;
        this.salary = salary;
    }

    public String getPhotoTimeStamp() {
        return photoTimeStamp;
    }

    public void setPhotoTimeStamp(String photoTimeStamp) {
        this.photoTimeStamp = photoTimeStamp;
    }

    public Bitmap getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Bitmap userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(String dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
