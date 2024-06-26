package app.databasemysql;

import app.databasemysql.angestellte;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


public class MainPanel {
    MySQL db = new MySQL();
    @FXML
    Button addButton;
    @FXML
    TableView<Object> data;
    @FXML
    ComboBox <String> JCBTable;

   static Stage Primestage;


    @FXML
    private void initialize() {
        // Initialisierung des ComboBox mit Auswahloptionen
        JCBTable.setItems(FXCollections.observableArrayList("Angestellte", "Titel","Gehalt", "Gesamt"));
        JCBTable.setOnAction(event -> {
            String selected = JCBTable.getSelectionModel().getSelectedItem();
            if ("Angestellte".equals(selected)) {
                setJCBTableAngestellte();
                getTable();
            } else if ("Titel".equals(selected)) {
                setJCBTableTitel();
            }else if ("Gehalt".equals(selected)){
                setJCBTableGehalt();
            }else if ("Titelgehalt".equals(selected)){
                setJCBTabletitgeh();
            } else if ("angestellter_gehaelter_no_titel".equals(selected)) {
                setJCBTableangegeh();
            } else if ("Gesamt".equals(selected)) {
                setJCBTableGesamt();
            }else {
                getTable();
            }

        });

    }

    public void setStage(Stage stage){
        Primestage =stage;
    }

    public void changeStageSize(){
        this.Primestage.setMinWidth(1280);
        this.Primestage.setMinHeight(720);
    }

    private void setJCBTableGesamt() {
        data.getColumns().clear();
        // Spalten für den gesamt_information_view erstellen
        TableColumn<Object, Integer> angestelltenIdColumn = new TableColumn<>("Angestellten ID");
        angestelltenIdColumn.setCellValueFactory(new PropertyValueFactory<>("angNummer"));

        TableColumn<Object, String> vornameColumn = new TableColumn<>("Vorname");
        vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        TableColumn<Object, String> nachnameColumn = new TableColumn<>("Nachname");
        nachnameColumn.setCellValueFactory(new PropertyValueFactory<>("nachname"));

        TableColumn<Object, Date> geburtsdatumColumn = new TableColumn<>("Geburtsdatum");
        geburtsdatumColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));

        TableColumn<Object, String> geschlechtColumn = new TableColumn<>("Geschlecht");
        geschlechtColumn.setCellValueFactory(new PropertyValueFactory<>("geschlecht"));

        TableColumn<Object, Date> einstellungsdatumColumn = new TableColumn<>("Einstellungsdatum");
        einstellungsdatumColumn.setCellValueFactory(new PropertyValueFactory<>("hiredate"));

        TableColumn<Object, String> titelColumn = new TableColumn<>("Titel");
        titelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));

        TableColumn<Object, Date> titelVonColumn = new TableColumn<>("Titel_Von");
        titelVonColumn.setCellValueFactory(new PropertyValueFactory<>("tfrom_date"));

        TableColumn<Object, Date> titelBisColumn = new TableColumn<>("Titel_Bis");
        titelBisColumn.setCellValueFactory(new PropertyValueFactory<>("tto_date"));

        TableColumn<Object, Integer> gehaltColumn = new TableColumn<>("Gehalt");
        gehaltColumn.setCellValueFactory(new PropertyValueFactory<>("gehalt"));

        TableColumn<Object, Date> gehaltVonColumn = new TableColumn<>("Gehalt_Von");
        gehaltVonColumn.setCellValueFactory(new PropertyValueFactory<>("gfrom_date"));

        TableColumn<Object, Date> gehaltBisColumn = new TableColumn<>("Gehalt_Bis");
        gehaltBisColumn.setCellValueFactory(new PropertyValueFactory<>("gto_date"));
        data.getColumns().addAll(angestelltenIdColumn, vornameColumn, nachnameColumn, geburtsdatumColumn, geschlechtColumn, einstellungsdatumColumn, titelColumn, titelVonColumn, titelBisColumn, gehaltColumn, gehaltVonColumn, gehaltBisColumn);

        ObservableList<gesamt> andata = getgesamtData();
        data.setItems(FXCollections.observableArrayList(andata.toArray()));
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

        ObservableList<gehalt> andata = getgehaltData();
        data.setItems(FXCollections.observableArrayList(andata.toArray()));
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

        ObservableList<titel> andata = getTitelData();
        data.setItems(FXCollections.observableArrayList(andata.toArray()));
    }



    public void setJCBTableAngestellte() {
        // Spalten erstellen
        TableColumn<Object, Integer> nummerColumn = new TableColumn<>("Nummer");
        nummerColumn.setCellValueFactory(new PropertyValueFactory<>("angNummer"));

        TableColumn<Object, String> vornameColumn = new TableColumn<>("Vorname");
        vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nachname");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nachname"));

        TableColumn<Object, Date> birthColumn = new TableColumn<>("Geburtsdatum");
        birthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));

        TableColumn<Object, String> geschlechtColumn = new TableColumn<>("Geschlecht");
        geschlechtColumn.setCellValueFactory(new PropertyValueFactory<>("geschlecht"));

        TableColumn<Object, Date> hiredateColumn = new TableColumn<>("Einstellungsdatum");
        hiredateColumn.setCellValueFactory(new PropertyValueFactory<>("hiredate"));

        data.getColumns().clear();
        data.getColumns().addAll(nummerColumn, vornameColumn,nameColumn, birthColumn, geschlechtColumn, hiredateColumn);

        // Echte Daten
        ObservableList<angestellte> andata = getAngestellteData();
//        System.out.println(andata);
        data.setItems(FXCollections.observableArrayList(andata.toArray()));
    }

    public Object getTable(){
        Object Table=null;

        if (JCBTable.getSelectionModel().getSelectedItem().equals("Angestellte")){
            System.out.println("equal Angestellte Table");
            Table ="Angestellte";
        } else if (JCBTable.getSelectionModel().getSelectedItem().equals("Gehalt")) {
            Table="Gehalt";
        } else if (JCBTable.getSelectionModel().getSelectedItem().equals("Titel")) {
            Table="titel";
        }else if (JCBTable.getSelectionModel().getSelectedItem().equals("Gesamt")) {
            Table = "gesamt";
        }else {

        }




        return Table;
    }





    /**MItarbeiter (name,nname,bi,g,hire, ang_nr) <br>
     * gehlt & Tiel (nummer,g/t,from,to)
     * @return
     */
    private Object[] EditCell() {
        Object selectedItem = data.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem instanceof angestellte) {
                angestellte selectedAngestellte = (angestellte) selectedItem;
                System.out.println("Ausgewählter Angestellter: " + selectedAngestellte.getVorname());

                // Wert einer bestimmten Zelle aus der ausgewählten Zeile erhalten
                TableColumn<Object, ?> column = data.getColumns().get(0); // Zum Beispiel die erste Spalte
//                return new Object[]{data.getColumns().get(1),data.getColumns().get(2),data.getColumns().get(3),data.getColumns().get(4),data.getColumns().get(5)};
                return new Object[]{selectedAngestellte.getVorname(),selectedAngestellte.getNachname(),selectedAngestellte.getBirth(),selectedAngestellte.getGeschlecht(),selectedAngestellte.getHiredate(),selectedAngestellte.getAngNummer()};


            } else if (selectedItem instanceof gehalt) {
                TableColumn<Object, ?> column = data.getColumns().get(0); // Zum Beispiel die erste Spalte
                gehalt selectgehlt = (gehalt) selectedItem;
                Object value = column.getCellData(selectedItem);
                System.out.println("Wert der ersten Zelle: " + value);
                return new Object[]{selectgehlt.getNummer(),selectgehlt.getGehalt(),selectgehlt.getFrom_date(),selectgehlt.getTo_date()};
            } else if (selectedItem instanceof titel) {
                TableColumn<Object, ?> column = data.getColumns().get(0); // Zum Beispiel die erste Spalte
                titel selecttitel = (titel) selectedItem;
                Object value = column.getCellData(selectedItem);
                System.out.println("Wert der ersten Zelle: " + value);
                return new Object[]{selecttitel.getNummer(),selecttitel.getTitel(),selecttitel.getFrom_date(),selecttitel.getTo_date()};
            }
        } else {
            System.out.println("Keine Zeile ausgewählt");
            return null;
        }
        return null;
    }

    @FXML
    private Object readCell() {
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
            } else if (selectedItem instanceof gehalt) {
                TableColumn<Object, ?> column = data.getColumns().get(0); // Zum Beispiel die erste Spalte
                Object value = column.getCellData(selectedItem);
                System.out.println("Wert der ersten Zelle: " + value);
                return value; //ID
            } else if (selectedItem instanceof titel) {
                TableColumn<Object, ?> column = data.getColumns().get(0); // Zum Beispiel die erste Spalte
                Object value = column.getCellData(selectedItem);
                System.out.println("Wert der ersten Zelle: " + value);
                return value; //ID
            }
        } else {
            System.out.println("Keine Zeile ausgewählt");
            return null;
        }
        return null;
    }



    private  ObservableList<angestellte> getAngestellteData() {
        ObservableList<angestellte> angd = FXCollections.observableArrayList();
        angd.add(new angestellte(1, "Simon","Garb", Date.valueOf("1999-02-24"), "M", Date.valueOf("2012-12-11")));
        return db.getAngestellte();
    }

    private ObservableList<titel> getTitelData() {
        return db.gettitel();
    }
    private ObservableList<gehalt> getgehaltData() {
   return db.getgeh(); }

    private ObservableList<gesamt> getgesamtData() {
        return db.getGesamtInformation(); }



    public void ADD(/**/) {
        System.out.println("ADD");
        String currentTable = getTable().toString();
        if (currentTable.equalsIgnoreCase("Angestellte")){
           Object[] data;
           data=ADD_mitarbeiter();
           for (int i=0;i< data.length;i++){
               System.out.println(data[i]);
           }
            Date birthDate = Date.valueOf((LocalDate) data[2]);
            Date hireDate = Date.valueOf((LocalDate) data[4]);
            //Enrcypt
//
//            String encrypt ="";
//            try {
//                if (data[6].equals(true)){
//                    System.out.println(encrypt(data[0].toString(),getKeyFromString(data[5].toString())));
//                    System.out.println(encrypt("Test",getKeyFromString(data[5].toString()))) ;
//                    encrypt= encrypt(data[0].toString(),getKeyFromObject(data[5]) );
//                }
////                if (data[7].equals(true)){
////                    data[1]= encrypt(data[1].toString(),data[5].toString());
////                }
////                if (data[8].equals(true)){
////                    data[2]= encrypt(data[2].toString(),data[5].toString());
////                }
////                if (data[9].equals(true)){
////                    data[3]= encrypt(data[3].toString(),(data[5].toString()));
////                }
////                if (data[10].equals(true)){
////                    data[4]= encrypt(data[4].toString(),data[5].toString());
////                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
//            for (int i=0;i< data.length;i++){
//                System.out.println(data[i]);
//            }
//            db.addAngestellter(data[0].toString(), data[1].toString(), birthDate, data[3].toString(), hireDate);

            db.addAngestellter(data[0].toString(), data[1].toString(), birthDate, data[3].toString(), hireDate);
        } else if (currentTable.equalsIgnoreCase("gehalt")) {
            Object [] data;
            data = ADD_gehalt();
            for (int i=0;i< data.length;i++){
                System.out.println(data[i]);
//               System.out.println(data[i].toString());
            }
            Date fromDate = Date.valueOf((LocalDate) data[2]);
            Date toDate = Date.valueOf((LocalDate) data[3]);
            db.addGehalt(( Integer.valueOf(data[0].toString())) , Integer.valueOf(data[1].toString()),fromDate,toDate);

        }else if (currentTable.equalsIgnoreCase("titel")){
            Object [] data;
            data =ADD_titel();
            Date fromDate = Date.valueOf((LocalDate) data[2]);
            Date toDate = Date.valueOf((LocalDate) data[3]);
            db.addTitel((Integer.valueOf(data[0].toString())),data[1].toString(),fromDate,toDate);
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
        TextField angNr = new TextField();
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
        Label label5 = new Label("Angestellten NR: ");
        generate.setText("Generate");
        dialogPane.add(label1, 0, 1);
        dialogPane.add(textField1, 1, 1);
        dialogPane.add(label2, 0, 2);
        dialogPane.add(dateCell,1,2);
        dialogPane.add(label3,0,3);
        dialogPane.add(todatum,1,3);
        dialogPane.add(checkBox1,2,1);
        dialogPane.add(checkBox2,2,2);
        dialogPane.add(checkBox3,2,3);
        dialogPane.add(sck,1,4);
        dialogPane.add(generate,2,4);
        dialogPane.add(label4,0,4);
        dialogPane.add(label5,0,0);
        dialogPane.add(angNr,1,0);


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
                if (sck.getText().equalsIgnoreCase("")){
                    try {
                        SecretKey key = generateKey();
                        sck.setText(getKeyAsString(key));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return new Object[]{angNr.getText(), textField1.getText(),dateCell.getValue(),todatum.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected()};
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
        TextField angNr = new TextField();
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
        Label label5 = new Label("Angestellten NR: ");
        Label label4 = new Label("Your Secret Key:");
        Label label1 = new Label("Titel: ");
        Label label2 = new Label("Von: ");
        Label label3 = new Label("Bis: ");
        generate.setText("Generate");
        dialogPane.add(angNr,1,0);
        dialogPane.add(label5,0,0);
        dialogPane.add(label1, 0, 1);
        dialogPane.add(textField1, 1, 1);
        dialogPane.add(label2, 0, 2);
        dialogPane.add(dateCell,1,2);
        dialogPane.add(label3,0,3);
        dialogPane.add(todatum,1,3);
        dialogPane.add(checkBox1,2,1);
        dialogPane.add(checkBox2,2,2);
        dialogPane.add(checkBox3,2,3);
        dialogPane.add(sck,1,4);
        dialogPane.add(generate,2,4);
        dialogPane.add(label4,0,4);

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
                if (sck.getText().equalsIgnoreCase("")){
                    try {
                        SecretKey key = generateKey();
                        sck.setText(getKeyAsString(key));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return new Object[]{angNr.getText(), textField1.getText(),dateCell.getValue(),todatum.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }



    private Object[] ADD_mitarbeiter() {
        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Angestellten hinzufügen");

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
        Label label2 = new Label("Nachname: ");
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
                if (sck.getText().equalsIgnoreCase("")){
                    try {
                        SecretKey key = generateKey();
                        sck.setText(getKeyAsString(key));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return new Object[]{textField1.getText(), textField2.getText(),dateCell.getValue(),geschlecht.getText(),anstelldatum.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected(),checkBox4.isSelected(),checkBox5.isSelected()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }

    public void delete() {
        MySQL db = new MySQL();
        Object [] params = new Object[2];
        params[1] = readCell();
        params[0] = getTable();
       db.delete(params);

    }
    public void Filter(){
       Object[] data;

       if (getTable().toString().equalsIgnoreCase("Angestellte")){ //Wenn in Angestellter
           setJCBTableAngestellte();
           data= Filter_angstellte();
           ArrayList<Integer> index = new ArrayList<>();
           int d=0;
           for (int i=0;i<data.length;i++){
               if (data[i].equals(false)){
                   System.out.println(data[i]);
                   this.data.getColumns().remove(i-d);
                   d++;
                   index.add(i);
               }
           }
           System.out.println(index);


       } else if (getTable().toString().equalsIgnoreCase("gehalt")) {// Wenn in Gehalt
           data= Filter_gehalt();
           setJCBTableGehalt();
           ArrayList<Integer> index = new ArrayList<>();
           int d=0;
           for (int i=0;i<data.length;i++){
               if (data[i].equals(false)){
                   System.out.println(data[i]);
                   this.data.getColumns().remove(i-d);
                   d++;
                   index.add(i);
               }
           }
       } else if (getTable().toString().equalsIgnoreCase("Titel")) {
           data= Filter_Titel();
           setJCBTableTitel();
           ArrayList<Integer> index = new ArrayList<>();
           int d=0;
           for (int i=0;i<data.length;i++){
               if (data[i].equals(false)){
                   System.out.println(data[i]);
                   this.data.getColumns().remove(i-d);
                   d++;
                   index.add(i);
               }
           }
       } else if (getTable().equals("gesamt")) {
           data= Filter_gesamt();
           setJCBTableGesamt();
           ArrayList<Integer> index = new ArrayList<>();
           int d=0;
           for (int i=0;i<data.length;i++){
               if (data[i].equals(false)){
                   System.out.println(data[i]);
                   this.data.getColumns().remove(i-d);
                   d++;
                   index.add(i);
               }
           }

       }


    }

    private Object[] Filter_Titel() {
        Dialog<Object[]> dialogF = new Dialog<>();
        dialogF.setTitle("Daten auswählen");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialogF.initOwner(null);
        dialogF.initModality(Modality.APPLICATION_MODAL);


        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        CheckBox id = new CheckBox("ID");
        CheckBox Titel = new CheckBox("Titel");
        CheckBox From = new CheckBox("from");
        CheckBox TO = new CheckBox("to");

        id.setSelected(true);
        Titel.setSelected(true);
        From.setSelected(true);
        TO.setSelected(true);


        dialogPane.add(id, 0, 0);
        dialogPane.add(Titel, 0, 1);
        dialogPane.add(From, 0, 2);
        dialogPane.add(TO, 0, 3);



        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialogF.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialogF.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{id.isSelected(), Titel.isSelected(), From.isSelected(), TO.isSelected()};
            }
            return null;
        });

        dialogF.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialogF.showAndWait();

        return result.orElse(null);

    }


    private Object[] Filter_gehalt() {
        Dialog<Object[]> dialogF = new Dialog<>();
        dialogF.setTitle("Daten auswählen");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialogF.initOwner(null);
        dialogF.initModality(Modality.APPLICATION_MODAL);


        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        CheckBox id = new CheckBox("ID");
        CheckBox Gehalt = new CheckBox("Gehalt");
        CheckBox From = new CheckBox("from");
        CheckBox TO = new CheckBox("to");

        id.setSelected(true);
        Gehalt.setSelected(true);
        From.setSelected(true);
        TO.setSelected(true);


        dialogPane.add(id,0,0);
        dialogPane.add(Gehalt,0,1);
        dialogPane.add(From,0,2);
        dialogPane.add(TO,0,3);



        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialogF.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialogF.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{id.isSelected(),Gehalt.isSelected(),From.isSelected(),TO.isSelected()};
            }
            return null;
        });

        dialogF.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialogF.showAndWait();

        return result.orElse(null);
    }

    public Object[] Filter_angstellte(){
        Dialog<Object[]> dialogF = new Dialog<>();
        dialogF.setTitle("Daten auswählen");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialogF.initOwner(null);
        dialogF.initModality(Modality.APPLICATION_MODAL);


        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        CheckBox id = new CheckBox("ID");
        CheckBox vorname = new CheckBox("Vorname");
        CheckBox name = new CheckBox("nachname");
        CheckBox birth = new CheckBox("Birth");
        CheckBox geschlecht = new CheckBox("Geschlecht");
        CheckBox hired = new CheckBox("hired");
        id.setSelected(true);
        vorname.setSelected(true);
        name.setSelected(true);
        birth.setSelected(true);
        geschlecht.setSelected(true);
        hired.setSelected(true);

        dialogPane.add(id,0,0);
        dialogPane.add(vorname,0,1);
        dialogPane.add(name,0,2);
        dialogPane.add(birth,0,3);
        dialogPane.add(geschlecht,0,4);
        dialogPane.add(hired,0,5);


        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialogF.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialogF.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{id.isSelected(),vorname.isSelected(),name.isSelected(),birth.isSelected(),geschlecht.isSelected(),hired.isSelected()};
            }
            return null;
        });

        dialogF.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialogF.showAndWait();

        return result.orElse(null);
    }
//getTable
    public Object[] Filter_gesamt() {
        Dialog<Object[]> dialogF = new Dialog<>();
        dialogF.setTitle("Daten auswählen");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialogF.initOwner(null);
        dialogF.initModality(Modality.APPLICATION_MODAL);

        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        CheckBox angestelltenId = new CheckBox("AngestelltenID");
        CheckBox vorname = new CheckBox("Vorname");
        CheckBox nachname = new CheckBox("Nachname");
        CheckBox geburtsdatum = new CheckBox("Geburtsdatum");
        CheckBox geschlecht = new CheckBox("Geschlecht");
        CheckBox einstellungsdatum = new CheckBox("Einstellungsdatum");
        CheckBox titel = new CheckBox("Titel");
        CheckBox titelVon = new CheckBox("Titel_Von");
        CheckBox titelBis = new CheckBox("Titel_Bis");
        CheckBox gehalt = new CheckBox("Gehalt");
        CheckBox gehaltVon = new CheckBox("Gehalt_Von");
        CheckBox gehaltBis = new CheckBox("Gehalt_Bis");

        angestelltenId.setSelected(true);
        vorname.setSelected(true);
        nachname.setSelected(true);
        geburtsdatum.setSelected(true);
        geschlecht.setSelected(true);
        einstellungsdatum.setSelected(true);
        titel.setSelected(true);
        titelVon.setSelected(true);
        titelBis.setSelected(true);
        gehalt.setSelected(true);
        gehaltVon.setSelected(true);
        gehaltBis.setSelected(true);

        dialogPane.add(angestelltenId, 0, 0);
        dialogPane.add(vorname, 0, 1);
        dialogPane.add(nachname, 0, 2);
        dialogPane.add(geburtsdatum, 0, 3);
        dialogPane.add(geschlecht, 0, 4);
        dialogPane.add(einstellungsdatum, 0, 5);
        dialogPane.add(titel, 0, 6);
        dialogPane.add(titelVon, 0, 7);
        dialogPane.add(titelBis, 0, 8);
        dialogPane.add(gehalt, 0, 9);
        dialogPane.add(gehaltVon, 0, 10);
        dialogPane.add(gehaltBis, 0, 11);

        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialogF.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialogF.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{angestelltenId.isSelected(), vorname.isSelected(), nachname.isSelected(),
                        geburtsdatum.isSelected(), geschlecht.isSelected(), einstellungsdatum.isSelected(),
                        titel.isSelected(), titelVon.isSelected(), titelBis.isSelected(),
                        gehalt.isSelected(), gehaltVon.isSelected(), gehaltBis.isSelected()};
            }
            return null;
        });

        dialogF.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialogF.showAndWait();

        return result.orElse(null);
    }

    public void edit(){
        String table = getTable().toString();
        if (table.equalsIgnoreCase("angestellte")){
            Object [] data = EditCell();
            String birthDate = String.valueOf(Date.valueOf(data[2].toString()));
            String hireDate = String.valueOf(Date.valueOf(data[4].toString()));
            db.updateMitarbeiter(editMitarbeiter(data[0].toString(),data[1].toString(),birthDate,data[3].toString(),hireDate),data[5]);
            setJCBTableAngestellte();

        } else if (table.equalsIgnoreCase("gehalt")) {
//            db.updateGehalt(editgehalt());
//Nicht veränderbar

        }else if (table.equalsIgnoreCase("titel")){
//Nicht veränderbar
        }
    }


    public  Object[] editgehalt (){
        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Gehalt eingeben");

        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialog.initOwner(null);
        dialog.initModality(Modality.APPLICATION_MODAL);

        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);
        TextField angNr = new TextField();
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
        Label label5 = new Label("Angestellten NR: ");
        generate.setText("Generate");

        Object data[] = EditCell();
        Date from = Date.valueOf(data[2].toString());
        Date to = Date.valueOf(data[3].toString());

        angNr.setText(data[0].toString());
        textField1.setText(data[1].toString());
        dateCell.setValue(from.toLocalDate());
        todatum.setValue(to.toLocalDate());

        dialogPane.add(label1, 0, 1);
        dialogPane.add(textField1, 1, 1);
        dialogPane.add(label2, 0, 2);
        dialogPane.add(dateCell,1,2);
        dialogPane.add(label3,0,3);
        dialogPane.add(todatum,1,3);
        dialogPane.add(checkBox1,2,1);
        dialogPane.add(checkBox2,2,2);
        dialogPane.add(checkBox3,2,3);
        dialogPane.add(sck,1,4);
        dialogPane.add(generate,2,4);
        dialogPane.add(label4,0,4);
        dialogPane.add(label5,0,0);
        dialogPane.add(angNr,1,0);


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
                if (sck.getText().equalsIgnoreCase("")){
                    try {
                        SecretKey key = generateKey();
                        sck.setText(getKeyAsString(key));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return new Object[]{angNr.getText(), textField1.getText(),dateCell.getValue(),todatum.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }

    public Object[] editMitarbeiter(String vorn, String name, String birth, String gesch, String hired){

        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("Daten ändern");

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
        Label label2 = new Label("Nachname: ");
        Label label3 = new Label("Geburtsdatum");
        Label label4 = new Label("Your Secret Key:");
        Label label5 = new Label("Geschlecht M/F: ");
        Label label6 = new Label("Anstelldatum: ");
        generate.setText("Generate");

        Object data[] = EditCell();
        Date birthade = Date.valueOf(data[2].toString());
        Date hireda = Date.valueOf(data[4].toString());
        textField1.setText(data[0].toString());
        textField2.setText(data[1].toString());
        dateCell.setValue(birthade.toLocalDate());
        geschlecht.setText(data[3].toString());
        anstelldatum.setValue(hireda.toLocalDate());

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
                if (sck.getText().equalsIgnoreCase("")){
                    try {
                        SecretKey key = generateKey();
                        sck.setText(getKeyAsString(key));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return new Object[]{textField1.getText(), textField2.getText(),dateCell.getValue(),geschlecht.getText(),anstelldatum.getValue(),getKeyFromString(sck.getText()),checkBox1.isSelected(),checkBox2.isSelected(),checkBox3.isSelected(),checkBox4.isSelected(),checkBox5.isSelected()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
    }



    public void execsql(){
        Object [] res = sql();
        db.execute(res[0].toString());

    }

    private Object[] sql(){
        Dialog<Object[]> dialog = new Dialog<>();
        dialog.setTitle("sql Befehl eingeben");
        // Setze den Dialog als Modal, um die Interaktion mit der Hauptanwendung zu blockieren
        dialog.initOwner(null);
        dialog.initModality(Modality.APPLICATION_MODAL);
        GridPane dialogPane = new GridPane();
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);
        TextField tfsql = new TextField();
        Label label1 = new Label("sql: ");
        tfsql.setMinSize(600,0);
        tfsql.setAlignment(Pos.TOP_LEFT);
        tfsql.setPrefColumnCount(8);

        dialogPane.add(tfsql,1,0);
        dialogPane.add(label1, 0, 0);
        ButtonType submitButtonType = new ButtonType("Bestätigen", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == submitButtonType) {
                return new Object[]{tfsql.getText()};
            }
            return null;
        });

        dialog.getDialogPane().setContent(dialogPane);
        Optional<Object[]> result = dialog.showAndWait();

        return result.orElse(null);
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

    public SecretKey getKeyFromObject(Object keyObj) {
        if (keyObj instanceof String) {
            return getKeyFromString((String) keyObj);
        } else {
            throw new IllegalArgumentException("Expected a Base64 encoded string for the key");
        }
    }


    // Method to encrypt a message
    public String encrypt(String message, SecretKey key) throws Exception {
        System.out.println(message);
        byte[] iv = null;
        byte[] encryptedMessage = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            encryptedMessage = cipher.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(encryptedMessage);
    }

    // Method to decrypt a message
    public String decrypt(String encryptedMessage, SecretKey key) throws Exception {
        byte[] originalMessage = null;
        try {
            String[] parts = encryptedMessage.split(":");
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            originalMessage = cipher.doFinal(encryptedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(originalMessage);
    }

}

