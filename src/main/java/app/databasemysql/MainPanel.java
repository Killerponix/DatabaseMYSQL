package app.databasemysql;

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
        int currentTable =1;
        if (currentTable == 1){
           Object[] data;
           data=ADD_mitarbeiter();
            System.out.println(data[2]);
            System.out.println(data[2].toString());

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
        TextField sck = new TextField();
        Button generate = new Button();
        Label label1 = new Label("Daten 1:");
        Label label2 = new Label("Daten 2:");
        Label label3 = new Label("Datum: ");
        Label label4 = new Label("Your Secret Key:");
        generate.setText("Generate");
        dialogPane.add(label1, 0, 0);
        dialogPane.add(textField1, 1, 0);
        dialogPane.add(label2, 0, 1);
        dialogPane.add(textField2, 1, 1);
        dialogPane.add(label3,0,2);
        dialogPane.add(dateCell,1,2);
        dialogPane.add(sck,1,3);
        dialogPane.add(label4,0,3);
        dialogPane.add(generate,2,3);
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
                return new Object[]{textField1.getText(), textField2.getText(),dateCell.getValue(),sck.getText()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }

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

