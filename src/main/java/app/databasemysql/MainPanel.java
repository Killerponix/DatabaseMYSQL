package app.databasemysql;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.Base64;
import javax.swing.*;
import java.util.Optional;


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
    TableCell A1;

    @FXML
    public void setJCBTable() {
        ObservableList<String> datas = FXCollections.observableArrayList(
                new String("John Doe"),
                new String("Jane Smith")
        );

        TableColumn<String, String> column = new TableColumn<>("Name");
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        data.getColumns().add(column);

        JCBTable.setItems(datas);
    }

    public void ADD(/**/) {
        System.out.println("ADD");
        int currentTable =1;
        if (currentTable == 1){
           Object[] data;
           data=ADD_mitarbeiter();
           for (int i=0;i< data.length;i++){
               System.out.println(data[i]);
//               System.out.println(data[i].toString());
           }


        }
        setJCBTable();
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

    public void delete(){

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

