package com.example.postapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.awt.*;
import javafx.scene.image.Image;
import java.util.Objects;

public class MainView {

    public  Stage stage;
    private BorderPane rootLayout = new BorderPane();
    private VBox activeNav;

    public MainView(Stage stage){
        this.stage = stage;
        setupUi();
    }

    private void setupUi() {
        stage.setTitle("POS Kasir - JavaFX");

        HBox bottomNav = new HBox(14);
        bottomNav.setPadding(new Insets(12));
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: transparent transparent #e5e7eb transparent;" +
                        "-fx-border-width: 1;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 6, 0, 0, -2);"
        );

        VBox kasirBtn = createNavItem(
                "/images/kasir.png",
                "/images/kasir_blue.png",
                "Kasir",
                true
        );

        VBox manageBtn = createNavItem(
                "/images/addProduct.png",
                "/images/addProduct_blue.png",
                "Produk",
                false
        );

        VBox monitoringBtn = createNavItem(
                "/images/monitoring.png",
                "/images/monitoring_blue.png",
                "Monitoring",
                false
        );

        VBox historyBtn = createNavItem(
                "/images/history.png",
                "/images/history_blue.png",
                "Riwayat",
                false
        );

        kasirBtn.setOnMouseClicked(e -> {
                switchPage(kasirBtn, new KasirView().getView());
            }
        );

        manageBtn.setOnMouseClicked(e -> {
            switchPage(manageBtn, new ManageView().getView());
        });

        monitoringBtn.setOnMouseClicked(e -> {
            switchPage(monitoringBtn, new MonitoringView().getView());
        });

        historyBtn.setOnMouseClicked(e -> {
            switchPage(historyBtn, new HistoryView().getView());
        });

        bottomNav.getChildren().addAll(kasirBtn, manageBtn, monitoringBtn, historyBtn);
        rootLayout.setBottom(bottomNav);

        activeNav = kasirBtn;
        rootLayout.setCenter(new KasirView().getView());

        stage.setScene(new Scene(rootLayout, 1200, 700));
        stage.show();
    }

    private VBox createNavItem(String inActiveIcon,  String activeIcon, String text, Boolean active) {

        ImageView icon = new ImageView( new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(active ? activeIcon : inActiveIcon))
        ));
        icon.setFitWidth(28);
        icon.setFitHeight(28);
        icon.setUserData(new String[]{inActiveIcon, activeIcon});

        Label label = new Label(text);
        label.setStyle(active
                ? "-fx-text-fill: #2563eb; -fx-font-size: 12px;"
                : "-fx-text-fill: #6b7280; -fx-font-size: 12px;"
        );

        VBox box = new VBox(4, icon, label);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(4, 15, 6, 15));
        box.setCursor(Cursor.HAND);

        if (active) {
            box.setStyle("-fx-background-color: #eef2ff; -fx-background-radius: 10;");
        }

        return box;
    }

    private void switchPage(VBox newActive, Pane view) {
        setActive(newActive);
        rootLayout.setCenter(view);
    }

    private void setActive(VBox newActive) {

        if (activeNav != null) {
            ImageView oldIcon = (ImageView) activeNav.getChildren().get(0);
            Label oldLabel = (Label) activeNav.getChildren().get(1);

            String[] paths = (String[]) oldIcon.getUserData();
            oldIcon.setImage(new Image(getClass().getResourceAsStream(paths[0])));

            oldLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
            activeNav.setStyle("-fx-background-color: transparent;");
        }

        ImageView newIcon = (ImageView) newActive.getChildren().get(0);
        Label newLabel = (Label) newActive.getChildren().get(1);

        String[] paths = (String[]) newIcon.getUserData();
        newIcon.setImage(new Image(getClass().getResourceAsStream(paths[1])));
        newLabel.setStyle("-fx-text-fill: #2563eb; -fx-font-size: 12px;");
        newActive.setStyle("-fx-background-color: #eef2ff; -fx-background-radius: 10;");

        activeNav = newActive;
    }
}
