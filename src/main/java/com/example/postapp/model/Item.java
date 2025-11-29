package com.example.postapp.model;

public class Item {
    protected int id;
    protected String name;
    protected double price;
    protected int stock;

    public Item (int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
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

    public String getName() {
        return name;
    }

    public void reduceStock(int qty) {
        this.stock -= qty;
    }
}
