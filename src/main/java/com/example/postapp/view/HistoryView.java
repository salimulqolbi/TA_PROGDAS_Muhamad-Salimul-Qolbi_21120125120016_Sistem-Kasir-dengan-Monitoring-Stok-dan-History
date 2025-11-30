package com.example.postapp.view;

import com.example.postapp.controller.TransactionController;
import com.example.postapp.model.TransactionItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.time.format.DateTimeFormatter;

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

        Label title = new Label("History Transaksi");
        title.setFont(Font.font(20));
        title.setPadding(new Insets(15));

        HBox topBox = new HBox(title);
        topBox.setAlignment(Pos.CENTER_LEFT);
        layout.setTop(topBox);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Belum ada transaksi"));
        table.setStyle("""
        -fx-font-size: 14px;
        -fx-cell-size: 32px;
    """);

        TableColumn<TransactionItem, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTransactionId())
        );
        colId.setStyle("-fx-alignment: CENTER;");
        colId.setPrefWidth(120);

        TableColumn<TransactionItem, String> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDateTime().format(dtf)
                )
        );
        colTanggal.setStyle("-fx-alignment: CENTER;");
        colTanggal.setPrefWidth(240);

        TableColumn<TransactionItem, String> colCount = new TableColumn<>("Jumlah Item");
        colCount.setCellValueFactory(c -> {
            int count = c.getValue().getItems().size();
            String label = count + " item";
            return new javafx.beans.property.SimpleStringProperty(label);
        });
        colCount.setStyle("-fx-alignment: CENTER;");
        colCount.setPrefWidth(150);

        TableColumn<TransactionItem, String> colTotal = new TableColumn<>("Total (Rp)");
        colTotal.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        formatRupiah(c.getValue().getTotal())
                )
        );
        colTotal.setStyle("-fx-alignment: CENTER;");
        colTotal.setPrefWidth(150);

        table.getColumns().setAll(colId, colTanggal, colCount, colTotal);

        VBox container = new VBox(table);
        container.setPadding(new Insets(10, 15, 20, 15));
        layout.setCenter(container);
    }


    private void loadData() {
        data.setAll(controller.getAllTransactions());
        table.setItems(data);
        table.refresh();
    }
}


