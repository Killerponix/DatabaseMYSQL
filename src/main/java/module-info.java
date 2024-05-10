module app.databasemysql {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens app.databasemysql to javafx.fxml;
    exports app.databasemysql;
}