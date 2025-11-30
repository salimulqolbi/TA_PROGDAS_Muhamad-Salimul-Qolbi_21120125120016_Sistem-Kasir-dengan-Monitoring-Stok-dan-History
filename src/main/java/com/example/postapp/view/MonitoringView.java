package com.example.postapp.view;

import com.example.postapp.Launcher;
import com.example.postapp.data.AppData;
import com.example.postapp.model.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MonitoringView {

    private BorderPane layout = new BorderPane();
    private TableView<Item> table;

    public MonitoringView() {
        setupUi();
    }

    public Pane getView() {
        return layout;
    }

    public void setupUi() {
        Label title = new Label("Monitoring Stock Barang");

        title.setFont(Font.font(20));
        title.setPadding(new Insets(15));

        HBox top = new HBox(title);
        top.setAlignment(Pos.CENTER_LEFT);

        layout.setTop(top);

        table = createTable();
        refreshTable();

        VBox center = new VBox(table);
        center.setPadding(new Insets(15));

        layout.setCenter(center);
    }

    private TableView<Item> createTable() {

        TableView<Item> tbl = new TableView<>();
        tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbl.setPlaceholder(new Label("Belum ada data barang"));

        TableColumn<Item, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()));

        TableColumn<Item, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Item, Number> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStock()));

        TableColumn<Item, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(getStatus(data.getValue().getStock())));

        colStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(status);
                setStyle("-fx-font-weight: bold; -fx-alignment: CENTER;");

                switch (status) {
                    case "Habis" -> setStyle(getStyle() + "-fx-text-fill: #dc2626;");
                    case "Menipis" -> setStyle(getStyle() + "-fx-text-fill: #ea580c;");
                    default -> setStyle(getStyle() + "-fx-text-fill: #16a34a;");
                }
            }
        });

        tbl.getColumns().addAll(colId, colName, colStock, colStatus);

        return tbl;
    }

    private void refreshTable() {
        table.getItems().setAll(AppData.getItems());
    }

    private String getStatus(int stock) {
        if (stock == 0) return "Habis";
        if (stock < 5) return "Menipis";
        return "Aman";
    }

}
