package com.example.a91p.model;

public class Advert {
    private int advertId;
    private String _name;
    private String _phoneNumber;
    private String _description;
    private String _date;
    private String _location;
    private String _postType;

    public Advert(String name, String phone, String description, String date, String location, String postType) {
        this._name = name;
        this._phoneNumber = phone;
        this._description = description;
        this._date = date;
        this._location = location;
        this._postType = postType;
    }

    public Advert(int id, String name, String phone, String description, String date, String location, String postType) {
        this.advertId = id;
        this._name = name;
        this._phoneNumber = phone;
        this._description = description;
        this._date = date;
        this._location = location;
        this._postType = postType;
    }

    public int getAdvertId() {
        return advertId;
    }

    public String getAdvertName() {
        return _name;
    }

    public String getAdvertPhone() {
        return _phoneNumber;
    }

    public String getAdvertDescription() {
        return _description;
    }

    public String getAdvertDate() {
        return _date;
    }

    public String getAdvertLocation() {
        return _location;
    }

    public String getAdvertPostType() {
        return _postType;
    }
}
