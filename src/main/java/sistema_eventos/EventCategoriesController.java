package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class EventCategoriesController implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    

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

    
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void switchToAccountLogin() throws IOException {
        App.setRoot("account-login");
    }

    @FXML
    private void switchToEventRegister() throws IOException {
        Sessao sessao = new Sessao();
        if (sessao.getUsuarioAtual().matches("")) {
            App.setRoot("account-login");
        }
        else {
            App.setRoot("event-register");
        }
    }

    @FXML
    private void switchToEventList() throws IOException {
        App.setRoot("event-list");
    }

    @FXML
    private void switchToEventCategories() throws IOException {
        App.setRoot("event-categories");
    }
    
    @FXML
    private void switchToEventNext() throws IOException {
        App.setRoot("event-next");
    }

    @FXML
    private void switchToEventPrevious() throws IOException {
        App.setRoot("event-previous");
    }

    @FXML
    private void displayCategorySocial() throws IOException {
        eventos = Evento.carregarEventos();
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
        eventos = Evento.carregarEventos();
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
        eventos = Evento.carregarEventos();
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
        eventos = Evento.carregarEventos();
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
        eventos = Evento.carregarEventos();
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
        eventos = Evento.carregarEventos();
        for (Evento evento : eventos) {
            if (evento.getCategoria().matches("Esportivo")) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            }
        }
        btnCategoriaEsportivo.setId("btn-selected");
    }

    


// roda quando a página é inicializada
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

