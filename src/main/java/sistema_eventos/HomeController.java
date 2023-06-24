package sistema_eventos;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;

public class HomeController extends Controller implements Initializable {

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPage();
    }

    @Override
    void loadPage() {
        // mostrar nome de usuário no menu de opções
        displayCurrentUser();
    }
}



