package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventListController implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    

    @FXML
    private VBox eventList;
    @FXML
    private TextField dataCategoria;
    @FXML
    private TextField dataDescricao;
    @FXML
    private TextField dataEndereco;
    @FXML
    private TextField dataHorario;
    @FXML
    private Label dataNome;
    @FXML
    private Label participacaoEvento;

    
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


// roda quando a página é inicializada
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventos = Evento.carregarEventos();
        for (Evento evento : eventos) {
            HBox eventItem = new EventListItem(evento);
            eventList.getChildren().add(eventItem);
        }
    }
}

