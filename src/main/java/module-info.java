module sistema_eventos {
    requires javafx.controls;
    requires javafx.fxml;

    opens sistema_eventos to javafx.fxml;
    exports sistema_eventos;
}
