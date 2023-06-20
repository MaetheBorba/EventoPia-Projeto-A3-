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

public class EventCreatedController implements Initializable {

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
            btnLogin.setVisible(false);
            btnRegister.setVisible(false);
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

        // adicionar eventos na lista
        for (Evento evento : eventos) {
            if (evento.getCriador().matches(usuarioAtual)) {
                HBox eventItem = new EventListItem(evento);
                eventList.getChildren().add(eventItem);
            } 
        }
    }
}
