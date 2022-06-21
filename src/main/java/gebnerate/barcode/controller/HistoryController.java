package gebnerate.barcode.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gebnerate.barcode.ConnectBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import gebnerate.barcode.SetFields;

public class HistoryController {

    ConnectBD connectBD = new ConnectBD();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<HistoryController> historytable;


    private ObservableList<HistoryController> setData = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {

        String sqlQuery = "select id,barcode,type from history;";
        connectBD.open();
        String resultQuery = connectBD.query(sqlQuery);
        connectBD.close();
        filltable(resultQuery);

        TableColumn<HistoryController, String> idCol = new TableColumn<HistoryController, String>("id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<HistoryController, String> barcodeCol = new TableColumn<HistoryController, String>("barcode");
        barcodeCol.setCellValueFactory(new PropertyValueFactory("barcode"));

        TableColumn<HistoryController, String> typeCol = new TableColumn<HistoryController, String>("type");
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));

        historytable.getColumns().setAll(idCol, barcodeCol, typeCol);

    }


    void filltable(String resultQuery) {

        int countRow = 0, id, countCol = 0;
        String barcode, type;

        for (int i = 0; i < resultQuery.length(); i++) {
            if (resultQuery.charAt(i) == ';') {
                countRow++;
            }
        }

        for (String str : resultQuery.split(";", 1)) {
            for (int i = 0; i < str.length(); i++) {
                if (resultQuery.charAt(i) == ':') {
                    countCol++;
                }
            }
        }

        for (String str : resultQuery.split(";", countRow)) {

            String[] tmp = str.split("\\|", countCol);

            id = Integer.parseInt(tmp[0]);
            barcode = tmp[1];
            type = tmp[2];


            setData.add(new SetFields(id, barcode, type));
            historytable.setItems(setData);

        }


    }

}
