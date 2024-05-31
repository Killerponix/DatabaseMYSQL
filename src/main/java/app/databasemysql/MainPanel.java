package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import javax.swing.*;
import java.util.Optional;
import java.util.jar.Attributes;


public class MainPanel {

    @FXML
    Button addButton;
    @FXML
    TableView<Object> data;
    @FXML
    ComboBox <String> JCBTable;


    @FXML
    private void initialize() {
        // Initialisierung des ComboBox mit Auswahloptionen
        JCBTable.setItems(FXCollections.observableArrayList("Angestellte", "Titel","Gehalt"));
        JCBTable.setOnAction(event -> {
            String selected = JCBTable.getSelectionModel().getSelectedItem();
            if ("Angestellte".equals(selected)) {
                setJCBTableAngestellte();
            } else if ("Titel".equals(selected)) {
                setJCBTableTitel();
            }else if ("Gehalt".equals(selected)){
                setJCBTableGehalt();
            }else if ("Titelgehalt".equals(selected)){
                setJCBTabletitgeh();
            } else if ("angestellter_gehaelter_no_titel".equals(selected)) {
                setJCBTableangegeh();
            } else if ("gesamt".equals(selected)) {
                setJCBTableGesamt();
            }

        });
    }

    private void setJCBTableGesamt() {

    }

    private void setJCBTableangegeh() {

    }

    private void setJCBTabletitgeh() {

    }

    private void setJCBTableGehalt() {
        data.getColumns().clear();
        // Spalten erstellen
        TableColumn<Object, Integer> nummerColumn = new TableColumn<>("Nummer");
        nummerColumn.setCellValueFactory(new PropertyValueFactory<>("nummer"));

        TableColumn<Object, Integer> gehaltColumn = new TableColumn<>("Gehalt");
        gehaltColumn.setCellValueFactory(new PropertyValueFactory<>("Gehalt"));


        TableColumn<Object, Date> fromColumn = new TableColumn<>("from_date");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from_date"));

        TableColumn<Object, Date> toColumn = new TableColumn<>("to_date");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to_date"));
        data.getColumns().addAll(nummerColumn,gehaltColumn,fromColumn, toColumn);
    }

    public void setJCBTableTitel() {
        data.getColumns().clear();
        // Spalten erstellen
        TableColumn<Object, Integer> nummerColumn = new TableColumn<>("Nummer");
        nummerColumn.setCellValueFactory(new PropertyValueFactory<>("nummer"));

        TableColumn<Object, String> TitelColumn = new TableColumn<>("Titel");
        TitelColumn.setCellValueFactory(new PropertyValueFactory<>("Titel"));


        TableColumn<Object, Date> fromColumn = new TableColumn<>("from_date");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from_date"));

        TableColumn<Object, Date> toColumn = new TableColumn<>("to_date");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to_date"));
        data.getColumns().addAll(nummerColumn,TitelColumn,fromColumn, toColumn);


    }


    public void setJCBTableAngestellte() {
        // Spalten erstellen
        TableColumn<Object, Integer> nummerColumn = new TableColumn<>("Nummer");
        nummerColumn.setCellValueFactory(new PropertyValueFactory<>("nummer"));

        TableColumn<Object, String> vornameColumn = new TableColumn<>("Vorname");
        vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        TableColumn<Object, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Object, Date> birthColumn = new TableColumn<>("Geburtsdatum");
        birthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));

        TableColumn<Object, String> geschlechtColumn = new TableColumn<>("Geschlecht");
        geschlechtColumn.setCellValueFactory(new PropertyValueFactory<>("geschlecht"));

        TableColumn<Object, Date> hiredateColumn = new TableColumn<>("Einstellungsdatum");
        hiredateColumn.setCellValueFactory(new PropertyValueFactory<>("hiredate"));

        data.getColumns().clear();
        data.getColumns().addAll(nummerColumn, vornameColumn,nameColumn, birthColumn, geschlechtColumn, hiredateColumn);

        // Daten zum Testen
        ObservableList<angestellte> andata = getAngestellteData();
        data.setItems(FXCollections.observableArrayList(andata.toArray()));
    }


    @FXML
    private Object readTable() {
        Object selectedItem = data.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem instanceof angestellte) {
                angestellte selectedAngestellte = (angestellte) selectedItem;
                System.out.println("Ausgewählter Angestellter: " + selectedAngestellte.getVorname());

                // Wert einer bestimmten Zelle aus der ausgewählten Zeile erhalten
                TableColumn<Object, ?> column = data.getColumns().get(0); // Zum Beispiel die erste Spalte
                Object value = column.getCellData(selectedItem);
                System.out.println("Wert der ersten Zelle: " + value);
                return value; //ID
            } else if (false) {
                // Handle für AndereKlasse
                return null;
            }
        } else {
            System.out.println("Keine Zeile ausgewählt");
            return null;
        }
        return null;
    }



    private static ObservableList<angestellte> getAngestellteData() {
        ObservableList<angestellte> angd = FXCollections.observableArrayList();
        angd.add(new angestellte(1, "Simon","Garb", Date.valueOf("1999-02-24"), "M", Date.valueOf("2012-12-11")));

        return angd;
    }




    public void ADD(/**/) {
        System.out.println("ADD");
        int currentTable =2;
        if (currentTable == 1){
           Object[] data;
           data=ADD_mitarbeiter();
           for (int i=0;i< data.length;i++){
               System.out.println(data[i]);
//               System.out.println(data[i].toString());
           }
        } else if (currentTable==2) {
            Object [] data;
            data = ADD_gehalt();
            for (int i=0;i< data.length;i++){
                System.out.println(data[i]);
//               System.out.println(data[i].toString());
            }
        }
    }

    private Object[] ADD_gehalt(){
        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Gehalt eingeben");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialog.initOwner(null);
        dialog.initModality(Modality.APPLICATION_MODAL);

        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        TextField textField1 = new TextField();
        DatePicker dateCell = new DatePicker();
        DatePicker todatum = new DatePicker();
        CheckBox checkBox1 = new CheckBox();
        CheckBox checkBox2 = new CheckBox();
        CheckBox checkBox3 = new CheckBox();
        checkBox1.setText("Verschlüsseln");
        checkBox2.setText("Verschlüsseln");
        checkBox3.setText("Verschlüsseln");

        TextField sck = new TextField();
        Button generate = new Button();
        Label label4 = new Label("Your Secret Key:");
        Label label1 = new Label("Gehalt: ");
        Label label2 = new Label("Von: ");
        Label label3 = new Label("Bis: ");
        generate.setText("Generate");
        dialogPane.add(label1, 0, 0);
        dialogPane.add(textField1, 1, 0);
        dialogPane.add(label2, 0, 1);
        dialogPane.add(dateCell,1,1);
        dialogPane.add(label3,0,2);
        dialogPane.add(todatum,1,2);
        dialogPane.add(checkBox1,2,0);
        dialogPane.add(checkBox2,2,1);
        dialogPane.add(checkBox3,2,2);
        dialogPane.add(sck,1,3);
        dialogPane.add(generate,2,3);
        dialogPane.add(label4,0,3);

        generate.setOnAction(event -> {
            try {
                SecretKey key = generateKey();
                String keyString = getKeyAsString(key);
                sck.setText(keyString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{textField1.getText(),dateCell.getValue(),todatum.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }


    private Object[] ADD_titel(){
        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Titel eingeben");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialog.initOwner(null);
        dialog.initModality(Modality.APPLICATION_MODAL);

        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        TextField textField1 = new TextField();
        DatePicker dateCell = new DatePicker();
        DatePicker todatum = new DatePicker();
        CheckBox checkBox1 = new CheckBox();
        CheckBox checkBox2 = new CheckBox();
        CheckBox checkBox3 = new CheckBox();
        checkBox1.setText("Verschlüsseln");
        checkBox2.setText("Verschlüsseln");
        checkBox3.setText("Verschlüsseln");

        TextField sck = new TextField();
        Button generate = new Button();
        Label label4 = new Label("Your Secret Key:");
        Label label1 = new Label("Titel: ");
        Label label2 = new Label("Von: ");
        Label label3 = new Label("Bis: ");
        generate.setText("Generate");
        dialogPane.add(label1, 0, 0);
        dialogPane.add(textField1, 1, 0);
        dialogPane.add(label2, 0, 1);
        dialogPane.add(dateCell,1,1);
        dialogPane.add(label3,0,2);
        dialogPane.add(todatum,1,2);
        dialogPane.add(checkBox1,2,0);
        dialogPane.add(checkBox2,2,1);
        dialogPane.add(checkBox3,2,2);
        dialogPane.add(sck,1,3);
        dialogPane.add(generate,2,3);
        dialogPane.add(label4,0,3);

        generate.setOnAction(event -> {
            try {
                SecretKey key = generateKey();
                String keyString = getKeyAsString(key);
                sck.setText(keyString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{textField1.getText(),dateCell.getValue(),todatum.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }



    private Object[] ADD_mitarbeiter() {
        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Daten eingeben");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialog.initOwner(null);
        dialog.initModality(Modality.APPLICATION_MODAL);

        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        DatePicker dateCell = new DatePicker();
        TextField geschlecht = new TextField();
        DatePicker anstelldatum = new DatePicker();
        CheckBox checkBox1 = new CheckBox();
        CheckBox checkBox2 = new CheckBox();
        CheckBox checkBox3 = new CheckBox();
        CheckBox checkBox4 = new CheckBox();
        CheckBox checkBox5 = new CheckBox();
        checkBox1.setText("Verschlüsseln");
        checkBox2.setText("Verschlüsseln");
        checkBox3.setText("Verschlüsseln");
        checkBox4.setText("Verschlüsseln");
        checkBox5.setText("Verschlüsseln");
        TextField sck = new TextField();
        Button generate = new Button();
        Label label1 = new Label("Vorname: ");
        Label label2 = new Label("Name: ");
        Label label3 = new Label("Geburtsdatum");
        Label label4 = new Label("Your Secret Key:");
        Label label5 = new Label("Geschlecht M/F: ");
        Label label6 = new Label("Anstelldatum: ");
        generate.setText("Generate");
        dialogPane.add(label1, 0, 0);
        dialogPane.add(textField1, 1, 0);
        dialogPane.add(label2, 0, 1);
        dialogPane.add(textField2, 1, 1);
        dialogPane.add(label3,0,2);
        dialogPane.add(dateCell,1,2);
        dialogPane.add(geschlecht,1,3);
        dialogPane.add(label5,0,3);
        dialogPane.add(anstelldatum,1,4);
        dialogPane.add(label6,0,4);

        dialogPane.add(sck,1,5);
        dialogPane.add(label4,0,5);
        dialogPane.add(generate,2,5);
        dialogPane.add(checkBox1,2,0);
        dialogPane.add(checkBox2,2,1);
        dialogPane.add(checkBox3,2,2);
        dialogPane.add(checkBox4,2,3);
        dialogPane.add(checkBox5,2,4);
        generate.setOnAction(event -> {
            try {
                SecretKey key = generateKey();
                String keyString = getKeyAsString(key);
                sck.setText(keyString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{textField1.getText(), textField2.getText(),dateCell.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }
    public String getCurrentTableName() {
        Object selectedTable = JCBTable.getSelectionModel().getSelectedItem();
        if (selectedTable != null) {
            return selectedTable.toString();
        } else {
            return null;
        }
    }
    public void delete(String name) {
    	MySQL mysql = new MySQL();
    	data.getColumns().clear();
        String tableName = getCurrentTableName();
        if (tableName != null && !name.isEmpty()) {
            String query = "DELETE FROM " + tableName + " WHERE name = " + readtable.value;
            PreparedStatement stmt = con.prepareStatement(query);
        } else {
            System.out.println("Tabelle oder Name nicht angegeben.");
        }
        
    }
    public void Filter(){
        Filter_angstellte();
        System.out.println("Test");
    }

    public void Filter_angstellte(){
        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Daten eingeben");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialog.initOwner(null);
        dialog.initModality(Modality.APPLICATION_MODAL);

        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        CheckBox id = new CheckBox("ID");
        CheckBox vorname = new CheckBox("Vorname");
        CheckBox name = new CheckBox("Name");
        CheckBox birth = new CheckBox("Birth");
        CheckBox geschlecht = new CheckBox("Geschlecht");
        CheckBox hired = new CheckBox("hired");

        dialogPane.add(id,0,0);
        dialogPane.add(vorname,0,1);
        dialogPane.add(name,0,2);
        dialogPane.add(birth,0,3);
        dialogPane.add(geschlecht,0,4);
        dialogPane.add(hired,0,5);

        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{id.isSelected(),vorname.isSelected(),name.isSelected(),birth.isSelected(),geschlecht.isSelected(),hired.isSelected()};
            }
            return null;
        });
    }






//    <<<<<<<<<<<<<<<<<<<---------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // Method to generate a random AES-256 key
    public SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // for AES-256
        return keyGen.generateKey();
    }
    // Method to get the key as a Base64 encoded string
    public String getKeyAsString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    // Method to convert a Base64 encoded key string to SecretKey
    public SecretKey getKeyFromString(String keyStr) {
        byte[] decodedKey = Base64.getDecoder().decode(keyStr);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    // Method to encrypt a message
    public String encrypt(String message, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(encryptedMessage);
    }

    // Method to decrypt a message
    public String decrypt(String encryptedMessage, SecretKey key) throws Exception {
        String[] parts = encryptedMessage.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] originalMessage = cipher.doFinal(encryptedBytes);
        return new String(originalMessage);
    }

}

