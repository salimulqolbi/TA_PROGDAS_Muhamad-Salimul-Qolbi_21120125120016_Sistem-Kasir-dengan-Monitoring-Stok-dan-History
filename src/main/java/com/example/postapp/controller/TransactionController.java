package com.example.postapp.controller;

import com.example.postapp.data.AppData;
import com.example.postapp.model.TransactionItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class TransactionController {

    public List<TransactionItem> getAllTransactions() {
        Queue<TransactionItem> history = AppData.getHistory();
        return history.stream().toList();
    }
}
