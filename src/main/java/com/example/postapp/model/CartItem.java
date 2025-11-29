package com.example.postapp.model;

public class CartItem {

    private Item item;
    private int quantity;

    public CartItem (Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return item.getName() + " x" + quantity + " → " + getSubtotal();
    }
}
