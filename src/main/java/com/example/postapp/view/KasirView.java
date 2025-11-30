package com.example.postapp.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.example.postapp.controller.CartController;
import com.example.postapp.data.AppData;
import com.example.postapp.model.CartItem;
import com.example.postapp.model.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.io.File;

public class KasirView {

    private BorderPane layout = new BorderPane();
    private CartController cartController = AppData.getCartController();

    private VBox cartPanel;
    private ListView<String> cartList;
    private Label totalLabel;

    public KasirView() {
        setupUI();
    }

    public Pane getView() {
        return layout;
    }

    private void setupUI() {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        FlowPane productGrid = createProductGrid(scrollPane);
        scrollPane.setContent(productGrid);

        layout.setCenter(scrollPane);

        cartPanel = createCartPanel();
        layout.setRight(cartPanel);
    }

    private FlowPane createProductGrid(ScrollPane scrollPane) {
        FlowPane grid = new FlowPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPrefWidth(Double.MAX_VALUE);
        grid.setColumnHalignment(javafx.geometry.HPos.LEFT);

        grid.prefWrapLengthProperty().bind(scrollPane.widthProperty().subtract(40));

        for (Item item : AppData.getItems()) {
            VBox card = createProductCard(item);
            grid.getChildren().add(card);
        }
        return grid;
    }

    private VBox createProductCard(Item item) {

        ImageView imgView;

        if (item.getImgPath() != null) {
            try {
                Image img = new Image(new File(item.getImgPath()).toURI().toString());
                imgView = new ImageView(img);
            } catch (Exception ex) {
                imgView = new ImageView();
                imgView.setStyle("-fx-background-color: #e2e8f0;");
            }
        } else {
            imgView = new ImageView();
            imgView.setStyle("-fx-background-color: #e2e8f0;");
        }

        imgView.setFitWidth(140);
        imgView.setFitHeight(100);
        imgView.setPreserveRatio(true);
        imgView.setSmooth(true);
        imgView.setCache(true);

        StackPane imageContainer = new StackPane(imgView);
        imageContainer.setPrefSize(140, 100);
        imageContainer.setAlignment(Pos.CENTER);

        Label name = new Label(item.getName());
        name.setFont(Font.font(14));

        Label price = new Label("Rp " + item.getPrice());
        price.setStyle("-fx-text-fill: #3b82f6; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label stock = new Label("Stok: " + item.getStock());
        stock.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");

        HBox infoRow = new HBox();
        infoRow.setSpacing(10);
        infoRow.setAlignment(Pos.CENTER_LEFT);
        infoRow.setPadding(new Insets(0, 5, 0, 5));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        infoRow.getChildren().addAll(price, spacer, stock);

        Button minus = new Button("-");
        Button plus = new Button("+");

        minus.setPrefWidth(30);
        plus.setPrefWidth(30);

        final int[] qty = {0};

        Label qtyLabel = new Label("0");
        qtyLabel.setPrefWidth(20);
        qtyLabel.setAlignment(Pos.CENTER);

        HBox qtyBox = new HBox();
        qtyBox.setAlignment(Pos.CENTER);
        qtyBox.setSpacing(10);
        qtyBox.setPadding(new Insets(5, 0, 10, 0));

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        minus.setDisable(true);

        qtyBox.getChildren().addAll(minus, leftSpacer, qtyLabel, rightSpacer, plus);
        qtyBox.setAlignment(Pos.CENTER);

        plus.setOnAction(e -> {
            if(qty[0] < item.getStock()) {
                qty[0]++;
                qtyLabel.setText(String.valueOf(qty[0]));

                minus.setDisable(false);

                cartController.setQuantity(qty[0], item.getId());
                updateCartPanel();
            }
        });

        minus.setOnAction(e -> {
            if(qty[0] > 0) {
                qty[0]--;
                qtyLabel.setText(String.valueOf(qty[0]));

                if (qty[0] == 0){
                    minus.setDisable(true);
                }

                cartController.setQuantity(qty[0], item.getId());
                updateCartPanel();
            }
        });


        VBox card = new VBox(10,
                imageContainer,
                name,
                infoRow,
                qtyBox
        );

        card.setPadding(new Insets(12));
        card.setPrefWidth(200);
        card.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 15;
            -fx-border-color: #e5e7eb;
            -fx-border-radius: 15;
            -fx-border-width: 1;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 6, 0, 0, 2);
            """);

        if (item.getStock() == 0) {
            card.setOpacity(0.5);
            card.setDisable(true);
        }
        System.out.println("BUILD CARD UNTUK: " + item.getName() + " HASH=" + this.hashCode());

        return card;
    }

    private VBox createCartPanel() {

        Label title = new Label("Keranjang");
        title.setFont(Font.font(16));

        cartList = new ListView<>();

        totalLabel = new Label("Total: Rp 0");
        totalLabel.setFont(Font.font(16));

        Button checkoutBtn = new Button("Selesaikan Transaksi");
        checkoutBtn.setPrefHeight(40);
        checkoutBtn.setMaxWidth(Double.MAX_VALUE);

        checkoutBtn.setOnAction(e -> {
            var tx = cartController.checkout();
            if (tx != null) {
                updateCartPanel();
                refreshProductGrid();
                showAlert("Transaksi berhasil!\nTotal: Rp " + tx.getTotal());
            } else {
                showAlert("Transaksi gagal. Cek stok atau keranjang kosong.");
            }
        });


        VBox box = new VBox(10, title, cartList, totalLabel, checkoutBtn);
        box.setPadding(new Insets(15));
        box.setPrefWidth(400);
        box.setStyle("-fx-background-color: #f9fafb;");

        return box;
    }

    private void updateCartPanel() {
        cartList.getItems().clear();

        for (CartItem ci : cartController.getCartItems()) {
            cartList.getItems().add(
                    ci.getItem().getName() + " x" + ci.getQuantity() +
                            "   = Rp " + ci.getSubtotal()
            );
        }
        cartList.setStyle("-fx-font-size: 16px;");
        totalLabel.setText("Total: Rp " + cartController.getTotal());
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    private void refreshProductGrid() {
        ScrollPane sp = (ScrollPane) layout.getCenter();
        FlowPane grid = createProductGrid(sp);
        sp.setContent(grid);
    }
}
