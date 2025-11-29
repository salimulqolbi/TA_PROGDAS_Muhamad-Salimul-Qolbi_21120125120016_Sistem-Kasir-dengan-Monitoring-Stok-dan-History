package com.example.postapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionItem {

    private int transactionId;
    private List<CartItem> items;
    private double total;
    private LocalDateTime dateTime;

    public TransactionItem(int transactionId, List<CartItem> items, double total) {
        this.transactionId = transactionId;
        this.items = items;
        this.total = total;
        this.dateTime = LocalDateTime.now();
    }

    public  int getTransactionId() {
        return transactionId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Transaksi #" + transactionId +
                " | Total: " + total +
                " | Waktu: " + dateTime;
    }
}
