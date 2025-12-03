package com.example.postapp.model;

public class Item {
    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected String imgPath;

    public Item (int id, String name, double price, int stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imgPath = imgPath;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public void reduceStock(int qty) {
        this.stock -= qty;
    }
}
