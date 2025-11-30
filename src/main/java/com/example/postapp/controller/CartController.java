package com.example.postapp.controller;

import com.example.postapp.data.AppData;
import com.example.postapp.model.CartItem;
import com.example.postapp.model.Item;
import com.example.postapp.model.TransactionItem;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartController {

    public List<CartItem> getCartItems() {
        return AppData.getCart();
    }

    private Optional<CartItem> findCartItemById(int itemId) {
        return AppData.getCart().stream()
                .filter(ci -> ci.getItem().getId() == itemId)
                .findFirst();
    }

    public boolean setQuantity(int qty, int itemId) {
        Optional<CartItem> existing = findCartItemById(itemId);

        if (qty == 0) {
            existing.ifPresent(ci -> AppData.getCart().remove(ci));
            return true;
        }

        Item item = null;

        for (Item it : AppData.getItems()) {
            if (it.getId() == itemId) {
                item = it;
                break;
            }
        }

        if (item == null) return false;

        if (qty > item.getStock()) return false;

        if (existing.isPresent()) {
            existing.get().setQuantity(qty);
            return true;
        }
        AppData.getCart().add(new CartItem(item, qty));
        return true;
    }

    public double getTotal() {
        return AppData.getCart().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public TransactionItem checkout() {
        List<CartItem> currentCart = AppData.getCart();

        if (currentCart.isEmpty()) {
            return null;
        }
        double total = getTotal();

        List<CartItem> snapshot = new ArrayList<>();
        for (CartItem ci : currentCart) {
            if (ci.getQuantity() > ci.getItem().getStock()) {
                return null;
            }

            snapshot.add(new CartItem(ci.getItem(), ci.getQuantity()));
        }

        for (CartItem ci : currentCart) {
            ci.getItem().reduceStock(ci.getQuantity());
        }

        int taxID = AppData.generateTransactionId();
        TransactionItem tax = new TransactionItem(taxID, snapshot, total);

        AppData.addTransaction(tax);
        AppData.clearCart();

        return tax;
    }

}
