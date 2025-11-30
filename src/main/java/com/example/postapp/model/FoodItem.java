package com.example.postapp.model;

public class FoodItem extends Item{

    private String expDate;

    public FoodItem(int id, String name, double price, int stock, String expDate, String imgPath) {
        super(id, name, price, stock, imgPath);
        this.expDate = expDate;
    }

    public String getExpDate() {
        return expDate;
    }
}
