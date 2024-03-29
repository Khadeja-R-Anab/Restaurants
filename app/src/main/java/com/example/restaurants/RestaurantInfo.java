package com.example.restaurants;

public class RestaurantInfo {
    private String name;


    private String desc;
    private String loc;
    private String rating;
    private String phone;
    private int count;

    public RestaurantInfo() {
    }

    public RestaurantInfo(String name, String desc, String loc, String phone, String rating) {
        this.name = name;
        this.desc = desc;
        this.loc = loc;
        this.phone = phone;
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}