package sistema_eventos;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventPreviousController extends Controller implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    
    // elementos da interface definidos no arquivo FXML
    @FXML
    private VBox eventList;

   


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
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(0);

        // remover eventos próximos
        eventos.removeIf(evento -> evento.getHorario().isAfter(LocalDateTime.now()));

        // organizar eventos por data e horário
        Collections.sort(eventos, new Comparator<Evento>() {
            public int compare(Evento o1, Evento o2) {
                return o1.getHorario().toInstant(offset).compareTo(o2.getHorario().toInstant(offset));
            }
        });
        
        // adicionar eventos na lista
        for (Evento evento : eventos) {
            HBox eventItem = new EventListItem(evento); // cria um card utilizando a classe EventListItem
            eventList.getChildren().add(eventItem); // adiciona o card à lista
        }
    }
}

