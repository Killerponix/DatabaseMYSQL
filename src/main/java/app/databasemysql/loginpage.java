package app.databasemysql;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class loginpage implements Initializable {
    @FXML
    private TextField usernameField, addressField;
    @FXML
    private PasswordField passwordField;
    private ScreenController screenController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Hier kannst du Initialisierungen vornehmen
    }
    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    public void connect(ActionEvent actionEvent) {
        MySQL db = new MySQL();
        String s= usernameField.getText();
        System.out.println(s);
  //      db.connect("killerponix","DBKillerponix","localhost");
        db.connect(usernameField.getText(),passwordField.getText(),addressField.getText());
        if (db.isConnected()){
            screenController.activate("MainPanel");
            MainPanel mainPanel = new MainPanel();
            mainPanel.changeStageSize();
        }else {
            System.out.println("WRONG");

        }
//        openDialog();



    }




//    private void openDialog() {
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.setTitle("Daten eingeben");
//
//        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
//        dialog.initOwner(null);
//        dialog.initModality(Modality.APPLICATION_MODAL);
//
//        GridPane dialogPane = new GridPane();
//        dialogPane.setPadding(new Insets(10));
//        dialogPane.setHgap(10);
//        dialogPane.setVgap(10);
//
//        TextField textField = new TextField();
//        Label label = new Label("Daten:");
//        dialogPane.add(label, 0, 0);
//        dialogPane.add(textField, 1, 0);
//
//        Button submitButton = new Button("Bestätigen");
//        submitButton.setOnAction(event -> {
//            // Hier kannst du die Eingaben aus dem Textfeld verarbeiten
//            String enteredData = textField.getText();
//            System.out.println("Eingegebene Daten: " + enteredData);
//            dialog.close();
//        });
//
//        dialogPane.add(submitButton, 1, 1);
//
//        dialog.getDialogPane().setContent(dialogPane);
//        dialog.showAndWait();
//
//    }

    private Object[] openDialog() {
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
        Label label1 = new Label("Daten 1:");
        Label label2 = new Label("Daten 2:");
        dialogPane.add(label1, 0, 0);
        dialogPane.add(textField1, 1, 0);
        dialogPane.add(label2, 0, 1);
        dialogPane.add(textField2, 1, 1);

        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{textField1.getText(), textField2.getText()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }

}
