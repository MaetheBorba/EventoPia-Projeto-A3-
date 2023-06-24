package sistema_eventos;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventListController extends Controller implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    // elementos da interface definidos no arquivo FXML
    @FXML
    protected VBox eventList;

    
    


// roda quando a página é inicializada
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPage();
    }

    @Override
    void loadPage() {

        // mostrar nome de usuário no menu de opções
        displayCurrentUser();

        eventos = Evento.carregarEventos();
        
        // remover eventos passados
        eventos.removeIf(evento -> evento.getHorario().isBefore(LocalDateTime.now()));

        // adicionar eventos na lista
        for (Evento evento : eventos) {
            HBox eventItem = new EventListItem(evento); // cria um card utilizando a classe EventListItem
            eventList.getChildren().add(eventItem); // adiciona o card à lista
        }
    }
}

