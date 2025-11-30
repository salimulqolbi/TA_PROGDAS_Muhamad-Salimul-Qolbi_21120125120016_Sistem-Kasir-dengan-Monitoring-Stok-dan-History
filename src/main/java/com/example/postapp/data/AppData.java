package com.example.postapp.data;

import com.example.postapp.controller.CartController;
import com.example.postapp.model.CartItem;
import com.example.postapp.model.Item;
import com.example.postapp.model.TransactionItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AppData {

    private static List<Item> items = new ArrayList<>();

    private static List<CartItem> cart = new ArrayList<>();

    private static Queue<TransactionItem> history = new LinkedList<>();

    private static int itemIdCounter = 1;
    private static int transactionIdCounter = 1;
    private static CartController cartController = new CartController();

    public static CartController getCartController() {
        return cartController;
    }

    static {
        addItem(new Item(generateItemId(), "Pulpen", 3000, 20, "src/main/java/com/example/postapp/images/pulpen.png"));
        addItem(new Item(generateItemId(), "Buku Tulis", 8000, 15, "src/main/java/com/example/postapp/images/buku_tulis.jpeg"));
        addItem(new Item(generateItemId(), "Air Mineral", 5000, 50, "src/main/java/com/example/postapp/images/air_mineral.jpeg"));
    }

    public static int generateItemId() {
        return itemIdCounter++;
    }

    public static int generateTransactionId() {
        return transactionIdCounter++;
    }

    public static List<Item> getItems() {
        return items;
    }

    public static List<CartItem> getCart() {
        return cart;
    }

    public static Queue<TransactionItem> getHistory() {
        return history;
    }

    public static void addItem(Item item) {
        items.add(item);
    }

    public static void removeItem(int id) {
        items.removeIf(i -> i.getId() == id);
    }

    public static void clearCart() {
        cart.clear();
    }

    public static void addTransaction(TransactionItem transactionItem) {
        history.add(transactionItem);
    }
}
