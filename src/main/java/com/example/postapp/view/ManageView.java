package com.example.postapp.view;

import com.example.postapp.controller.InventoryController;
import com.example.postapp.data.AppData;
import com.example.postapp.model.FoodItem;
import com.example.postapp.model.Item;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import java.io.File;

public class ManageView {

    private InventoryController controller = new InventoryController();
    private BorderPane layout = new BorderPane();
    private TableView<Item> table;

    public ManageView() {
        setupUi();
    }

    public Pane getView(){
        return layout;
    }

    private void setupUi() {
        Label title = new Label("Manage Barang");
        title.setFont(Font.font(20));
        title.setPadding(new Insets(10));

        HBox topBox = new HBox(title);
        topBox.setPadding(new Insets(5));
        topBox.setAlignment(Pos.CENTER_LEFT);

        layout.setTop(topBox);

        table = createTable();
        table.setPrefHeight(500);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Tidak ada data barang"));

        refreshTable();

        VBox centerBox = new VBox(table);
        centerBox.setPadding(new Insets(10));

        layout.setCenter(centerBox);

        VBox form = createForm();
        form.setPrefWidth(350);
        layout.setRight(form);

    }

    private TableView<Item> createTable () {
        TableView<Item> tbl = new TableView<>();

        TableColumn<Item, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Item, String> colName = new TableColumn<>("NAMA");
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Item, Double> colHarga = new TableColumn<>("Harga");
        colHarga.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        TableColumn<Item, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStock()).asObject());

        TableColumn<Item, String> colType = new TableColumn<>("Tipe");
        colType.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((data.getValue() instanceof FoodItem) ? "Food" : "Non Food")
        );

        TableColumn<Item, String> colDate = new TableColumn<>("Exp Date");
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue() instanceof FoodItem ?
                ((FoodItem) data.getValue()).getExpDate(): "-"));

        TableColumn<Item, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(getStatus(data.getValue().getStock())));

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

        tbl.getColumns().addAll(colId, colName, colHarga, colStock, colType, colDate, colStatus);
        tbl.setPrefWidth(600);

        return tbl;
    }

    private void refreshTable() {
        table.getItems().setAll(AppData.getItems());
    }

    private VBox createForm() {

        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setPrefWidth(350);
        form.setStyle("-fx-background-color: #f1f5f9;");

        Label formTitle = new Label("Tambah");
        formTitle.setFont(Font.font(16));

        TextField nameField = new TextField();
        nameField.setPromptText("Nama Barang");

        TextField priceField = new TextField();
        priceField.setPromptText("Harga");

        TextField stockField = new TextField();
        stockField.setPromptText("Stok");

        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Food", "NonFood");
        typeBox.setPromptText("Pilih Tipe");

        DatePicker expiryPicker = new DatePicker();
        expiryPicker.setPromptText("Expiry Date");
        expiryPicker.setVisible(false);

        Button chooseImg = new Button("Pilih Gambar");
        chooseImg.setMaxWidth(Double.MAX_VALUE);

        Label imgLabel = new Label("Belum ada gambar");
        imgLabel.setStyle("-fx-text-fill: #6b7280;");

        final String[] imgPath = { null };

        typeBox.setOnAction(e -> {
            String type = typeBox.getValue();
            expiryPicker.setVisible("Food".equals(type));
        });

        Button addBtn = new Button("Tambah Barang");
        addBtn.setPrefHeight(35);
        addBtn.setMaxWidth(Double.MAX_VALUE);

        addBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                boolean isFood = "Food".equals(typeBox.getValue());

                if (name.isEmpty() || typeBox.getValue() == null) {
                    showAlert("Semua field harus diisi.");
                    return;
                }

                if (isFood) {
                    controller.addItem(name, price, stock, isFood, isFood ? expiryPicker.getValue().toString() : null,
                            imgPath[0]);
                } else {
                    controller.addItem(name, price, stock, false, null, imgPath[0]);
                }

                refreshTable();
                clearForm(nameField, priceField, stockField, typeBox, expiryPicker);

                showAlert("Barang berhasil ditambahkan!");

            } catch (Exception ex) {
                showAlert("Input tidak valid! Pastikan harga dan stok angka.");
            }
        });

        Button deleteBtn = new Button("Hapus Barang");
        deleteBtn.setPrefHeight(35);
        deleteBtn.setMaxWidth(Double.MAX_VALUE);

        deleteBtn.setOnAction(e -> {
            Item selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Pilih barang yang ingin dihapus!");
                return;
            }
            controller.deleteItem(selected.getId());
            refreshTable();
            showAlert("Barang berhasil dihapus.");
        });

        chooseImg.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

            File file = fc.showOpenDialog(chooseImg.getScene().getWindow());
            if(file != null) {
                imgPath[0] = file.getAbsolutePath();
                imgLabel.setText("✔ " + file.getName());
            }
        });

        form.getChildren().addAll(
                formTitle,
                nameField, priceField, stockField, typeBox, expiryPicker,
                chooseImg, imgLabel,
                addBtn, deleteBtn
        );

        return form;
    }

    private void clearForm(TextField name, TextField price, TextField stock, ComboBox<String> typeBox, DatePicker expiry) {
        name.clear();
        price.clear();
        stock.clear();
        typeBox.setValue(null);
        expiry.setValue(null);
        expiry.setVisible(false);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    private String getStatus(int stock) {
        if (stock == 0) return "Habis";
        if (stock < 5) return "Menipis";
        return "Aman";
    }
}
