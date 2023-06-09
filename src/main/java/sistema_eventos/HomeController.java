package sistema_eventos;

import java.io.*;
import javafx.fxml.*;
import javafx.event.ActionEvent;

public class HomeController {

    
    @FXML
    private void switchToAccountLogin() throws IOException {
        App.setRoot("account-login");
    }

    @FXML
    private void switchToEventRegister() throws IOException {
        App.setRoot("event-register");
    }

}



