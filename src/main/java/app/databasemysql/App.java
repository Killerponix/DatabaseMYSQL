package app.databasemysql;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class App extends Application {
    MySQL DB = new MySQL();
    public Stage Primestage;
    public ScreenController screenController;

    @Override
    public void start(Stage stage) throws IOException {
        Primestage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("loginpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        screenController = new ScreenController(scene);
        screenController.addScreen("login", (Pane) fxmlLoader.getRoot());
        screenController.addScreen("MainPanel", FXMLLoader.load(getClass().getResource("MainPanel.fxml")));
        Primestage.setTitle("MYSQL Database Manager");
        loginpage loginpage = fxmlLoader.getController();
        loginpage.setScreenController(screenController);
        Primestage.setScene(scene);
        Primestage.show();




       /* Primestage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("loginpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ScreenController sc = new ScreenController(scene);
        this.screenController = sc;
//        this.screenController = new ScreenController(scene);
        screenController.addScreen("login", FXMLLoader.load(getClass().getResource("loginpage.fxml")));
        screenController.addScreen("MainPanel", FXMLLoader.load(getClass().getResource("MainPanel.fxml")));
        Primestage.setTitle("MYSQL Database Manager");
        Primestage.setScene(screenController.activate("login"));
        Primestage.show();
        while (DB.isConnected()){

        }
        //        switchScene("MainPanel",screenController);
        stage.setScene(screenController.activate("MainPanel"));*/
//       stage.show();
//       fxmlLoader = new FXMLLoader(App.class.getResource("MainPanel.fxml"));
//       Scene scene1 = new Scene(fxmlLoader.load());
//       stage.setScene(scene1);
//       stage.show();


//        this.Primestage = stage;
//        FXMLLoader loader = new FXMLLoader(getClass()
//                .getResource("calculator.fxml"));
//        Parent root = (Parent)loader.load();

    }

    public void switchScene2(String fxmlFile) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource(fxmlFile));
        Parent root;
        try {
            root = (Parent) loader.load();
            if (fxmlFile.equals("loginpage.fxml")) {
                System.out.println("Login");
            } else if (fxmlFile.equals("MainPanel.fxml")) {
                System.out.println("Main");
            }
            this.Primestage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public void switchScene(String name, ScreenController sc){
//            this.Primestage.setScene(sc.activate(name));
            try {
                this.Primestage.setScene( FXMLLoader.load(getClass().getResource(name)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static void main (String[]args){
            launch();
        }
    }

