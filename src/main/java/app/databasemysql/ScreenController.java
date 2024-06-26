package app.databasemysql;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    protected void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }
    protected Pane getScene(String name){
        return screenMap.get(name);
    }

    protected Scene activate(String name){
        main.setRoot( screenMap.get(name) );
        return setScene(name);
    }
    protected Scene setScene(String name){
        main = screenMap.get(name).getScene();
        return main;
    }
}
