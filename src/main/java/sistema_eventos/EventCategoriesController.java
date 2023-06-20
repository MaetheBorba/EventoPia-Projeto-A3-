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

public class EventCategoriesController implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    @FXML
    private Label accountName;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnSair;

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

    
    // funções do menu de opções
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void logoutUsuario() throws IOException {
        accountName.setText("Visitante");
        Sessao.atualizarSessao("");
        switchToHome();
    }

    @FXML
    private void switchToAccountLogin() throws IOException {
        App.setRoot("account-login");
    }

    @FXML
    private void switchToAccountRegister() throws IOException {
        App.setRoot("account-register");
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
    private void switchToEventParticipating() throws IOException {
        Sessao sessao = new Sessao();
        if (sessao.getUsuarioAtual().matches("")) {
            App.setRoot("account-login");
        }
        else {
            App.setRoot("event-participating");
        }
    }

    @FXML
    private void switchToEventCreated() throws IOException {
        Sessao sessao = new Sessao();
        if (sessao.getUsuarioAtual().matches("")) {
            App.setRoot("account-login");
        }
        else {
            App.setRoot("event-created");
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
    //

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

        // mostrar nome de usuário no menu de opções
        Sessao sessao = new Sessao();
        String usuarioAtual = sessao.getUsuarioAtual();
        if (!(usuarioAtual.matches(""))) {
            accountName.setText(usuarioAtual);
            accountName.setVisible(true);
            btnSair.setVisible(true);
        }

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

