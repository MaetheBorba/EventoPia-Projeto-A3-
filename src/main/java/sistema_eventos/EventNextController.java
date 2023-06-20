package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRulesProvider;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventNextController implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    

    @FXML
    private Label accountName;
    @FXML
    private Button btnSair;

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
        
        // adicionar eventos na lista
        for (Evento evento : eventos) {
            HBox eventItem = new EventListItem(evento);
            eventList.getChildren().add(eventItem);
        }
    }
}

