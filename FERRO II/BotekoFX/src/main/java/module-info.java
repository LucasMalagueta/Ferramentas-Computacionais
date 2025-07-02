module com.example.botekofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.botekofx to javafx.fxml;
    opens com.example.botekofx.db.entidades to javafx.fxml;
    exports com.example.botekofx;
    exports com.example.botekofx.db.entidades;
}