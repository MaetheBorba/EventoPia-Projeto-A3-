package sistema_eventos;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class EventController implements Initializable{

    private static List<Evento> eventos = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventos = Evento.carregarEventos();
        for (Evento evento : eventos) {
        }
    }
}
