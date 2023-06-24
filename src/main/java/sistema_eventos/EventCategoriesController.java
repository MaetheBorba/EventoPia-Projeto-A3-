package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventCategoriesController extends Controller implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    // elementos da interface definidos no arquivo FXML

    @FXML
    private VBox eventList;
    
    @FXML
    private Button btnCategoriaSocial;
    @FXML
    private Button btnCategoriaCorporativo;
    @FXML
    private Button btnCategoriaReligioso;
    @FXML
    private Button btnCategoriaAcademico;
    @FXML
    private Button btnCategoriaCultural;
    @FXML
    private Button btnCategoriaEsportivo;

    
    

    // funções para mudar a lista com base no evento selecionado
    @FXML
    private void displayCategorySocial() throws IOException {
        reset();
        for (Evento evento : eventos) {
            if (evento.getCategoria().matches("Social")) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            }
        }
        btnCategoriaSocial.setId("btn-selected");
    }

    @FXML
    private void displayCategoryCorporativo() throws IOException {
        reset();
        for (Evento evento : eventos) {
            if (evento.getCategoria().matches("Corporativo")) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            }
        }
        btnCategoriaCorporativo.setId("btn-selected");
    }

    @FXML
    private void displayCategoryReligioso() throws IOException {
        reset();
        for (Evento evento : eventos) {
            if (evento.getCategoria().matches("Religioso")) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            }
        }
        btnCategoriaReligioso.setId("btn-selected");
    }

    @FXML
    private void displayCategoryAcademico() throws IOException {
        reset();
        for (Evento evento : eventos) {
            if (evento.getCategoria().matches("Acad\u00EAmico/Educativo")) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            }
        }
        btnCategoriaAcademico.setId("btn-selected");
    }

    @FXML
    private void displayCategoryCultural() throws IOException {
        reset();
        for (Evento evento : eventos) {
            if (evento.getCategoria().matches("Cultural/Entretenimento")) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            }
        }
        btnCategoriaCultural.setId("btn-selected");
    }

    @FXML
    private void displayCategoryEsportivo() throws IOException {
        reset();
        for (Evento evento : eventos) {
            if (evento.getCategoria().matches("Esportivo")) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            }
        }
        btnCategoriaEsportivo.setId("btn-selected");
    }

    // reseta o fundo das opções de categoria e limpa a lista
    private void reset() {
        eventList.getChildren().clear();
        btnCategoriaAcademico.setId("btn");
        btnCategoriaCorporativo.setId("btn");
        btnCategoriaCultural.setId("btn");
        btnCategoriaEsportivo.setId("btn");
        btnCategoriaReligioso.setId("btn");
        btnCategoriaSocial.setId("btn");
    }

    


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

        // remover eventos passados
        eventos.removeIf(evento -> evento.getHorario().isBefore(LocalDateTime.now()));

        // organizar eventos por data e horário
        Collections.sort(eventos, new Comparator<Evento>() {
            public int compare(Evento o1, Evento o2) {
                return o1.getHorario().toInstant(offset).compareTo(o2.getHorario().toInstant(offset));
            }
        });
    }
}

