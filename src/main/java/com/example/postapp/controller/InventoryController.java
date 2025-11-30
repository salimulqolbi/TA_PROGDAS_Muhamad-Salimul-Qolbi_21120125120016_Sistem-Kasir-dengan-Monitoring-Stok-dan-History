package com.example.postapp.controller;

import com.example.postapp.data.AppData;
import com.example.postapp.model.FoodItem;
import com.example.postapp.model.Item;
import com.example.postapp.model.NonFoodItem;

public class InventoryController {

    public void addItem(String name, double price, int stock, boolean isFood, String expDate, String imgPath){
        int id = AppData.generateItemId();

        Item item;
        if (isFood) {
            item = new FoodItem(id, name, price, stock, expDate, imgPath);
        } else {
            item = new NonFoodItem(id, name, price, stock, imgPath);
        }

        AppData.addItem(item);
    }

    public void deleteItem(int itemId) {
        AppData.removeItem(itemId);
    }
}
