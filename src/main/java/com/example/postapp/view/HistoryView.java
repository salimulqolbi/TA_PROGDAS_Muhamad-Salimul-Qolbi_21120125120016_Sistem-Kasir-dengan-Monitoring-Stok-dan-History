package com.example.postapp.view;

import com.example.postapp.controller.TransactionController;
import com.example.postapp.model.TransactionItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.time.format.DateTimeFormatter;

//public class HistoryView {
//
//    private final TransactionController controller = new TransactionController();
//    private final BorderPane layout = new BorderPane();
//    private final TableView<TransactionItem> table = new TableView<>();
//    private final ObservableList<TransactionItem> data = FXCollections.observableArrayList();
//    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    public HistoryView() {
//        System.out.println(">>> HISTORY VIEW CREATED");
//        System.out.println(">>> HISTORY SIZE NOW = " + controller.getAllTransactions().size());
//        setupUi();
//        loadData();
//    }
//
//    public Pane getView(){
//        return layout;
//    }
//
//    private String formatRupiah(double value) {
//        return String.format("Rp %,d", (long) value).replace(",", ".");
//    }
//
//    public void setupUi() {
//        Label title = new Label("History Transaksi");
//        title.setFont(Font.font(20));
//        title.setPadding(new Insets(10));
//
//        VBox topBox = new VBox(title);
//        topBox.setPadding(new Insets(5));
//        topBox.setAlignment(Pos.CENTER_LEFT);
//
//        VBox centerBox = new VBox(table);
//        centerBox.setPadding(new Insets(10));
//
//        layout.setTop(topBox);
//
//        TableColumn<TransactionItem, Integer> colId = new TableColumn<>("ID");
//        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTransactionId()).asObject());
//        colId.setPrefWidth(80);
//
//        TableColumn<TransactionItem, String> colTanggal = new TableColumn<>("Tanggal");
//        colTanggal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDateTime().format(dtf)));
//        colTanggal.setPrefWidth(220);
//
//        TableColumn<TransactionItem, Integer> colCount = new TableColumn<>("Jumlah Item");
//        colCount.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getItems().size()).asObject());
//        colCount.setPrefWidth(120);
//
//        TableColumn<TransactionItem, String> colTotal = new TableColumn<>("Total (Rp)");
//        colTotal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(formatRupiah(c.getValue().getTotal())));
//        colTotal.setPrefWidth(140);
//
//        table.getColumns().addAll(colId, colTanggal, colCount, colTotal);
//        table.setItems(data);
//        table.setPrefHeight(480);
//
//        VBox container = new VBox(table);
//        container.setPadding(new Insets(10, 10, 20, 10)); // padding kiri/kanan 40px
//        layout.setCenter(container);
//    }
//
//    private void loadData() {
//        System.out.println(">>> LOADING HISTORY...");
//        data.setAll(controller.getAllTransactions());
//        System.out.println(">>> HISTORY FOUND = " + data.size());
//        table.setItems(data);
//        table.refresh();
//    }
//
//}

public class HistoryView {

    private final TransactionController controller = new TransactionController();
    private final BorderPane layout = new BorderPane();
    private final TableView<TransactionItem> table = new TableView<>();
    private final ObservableList<TransactionItem> data = FXCollections.observableArrayList();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HistoryView() {
        setupUi();
        loadData();
    }

    public Pane getView() {
        return layout;
    }

    private String formatRupiah(double value) {
        return String.format("Rp %,d", (long) value).replace(",", ".");
    }

    public void setupUi() {

        // ==== TITLE ====
        Label title = new Label("History Transaksi");
        title.setFont(Font.font(20));
        title.setPadding(new Insets(15));

        HBox topBox = new HBox(title);
        topBox.setAlignment(Pos.CENTER_LEFT);
        layout.setTop(topBox);

        // ==== TABLE SETUP ====
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Belum ada transaksi"));
        table.setStyle("""
        -fx-font-size: 14px;
        -fx-cell-size: 32px;
    """);

        // ==== COLUMN: ID ====
        TableColumn<TransactionItem, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTransactionId())
        );
        colId.setStyle("-fx-alignment: CENTER;");
        colId.setPrefWidth(120);

        // ==== COLUMN: TANGGAL ====
        TableColumn<TransactionItem, String> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDateTime().format(dtf)
                )
        );
        colTanggal.setStyle("-fx-alignment: CENTER;");
        colTanggal.setPrefWidth(240);

        // ==== COLUMN: JUMLAH ITEM ====
        TableColumn<TransactionItem, String> colCount = new TableColumn<>("Jumlah Item");
        colCount.setCellValueFactory(c -> {
            int count = c.getValue().getItems().size();
            String label = count + " item";
            return new javafx.beans.property.SimpleStringProperty(label);
        });
        colCount.setStyle("-fx-alignment: CENTER;");
        colCount.setPrefWidth(150);

        // ==== COLUMN: TOTAL ====
        TableColumn<TransactionItem, String> colTotal = new TableColumn<>("Total (Rp)");
        colTotal.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        formatRupiah(c.getValue().getTotal())
                )
        );
        colTotal.setStyle("-fx-alignment: CENTER;");
        colTotal.setPrefWidth(150);

        // ==== ADD ONLY 4 COLUMNS ====
        table.getColumns().setAll(colId, colTanggal, colCount, colTotal);

        // ==== WRAP WITH PADDING ====
        VBox container = new VBox(table);
        container.setPadding(new Insets(10, 15, 20, 15)); // sama seperti MonitoringView
        layout.setCenter(container);
    }


    private void loadData() {
        data.setAll(controller.getAllTransactions());
        table.setItems(data);
        table.refresh();
    }
}


