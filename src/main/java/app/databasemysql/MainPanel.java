package app.databasemysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import javax.swing.*;


public class MainPanel {

    @FXML
    Button addButton;
    @FXML
    TableView data;
    @FXML
    ComboBox JCBTable;

    @FXML
    TableColumn colA;

    @FXML
    public void setJCBTable() {
        ObservableList<String> data = FXCollections.observableArrayList(
                new String("John Doe"),
                new String("Jane Smith")
        );
        data.add("Test");
        JCBTable.setItems(data);

    }

    public void ADD(/**/) {
        System.out.println("ADD");
        setJCBTable();
    }

}

