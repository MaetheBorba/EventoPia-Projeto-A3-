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

    public void addParticipante() {
        for (Evento evento : eventos) {
            if (evento.getNome().matches(dataNome.getText())) {
                Sessao sessao = new Sessao();
                String usuarioAtual = sessao.getUsuarioAtual();
                evento.addParticipante(usuarioAtual, evento.getNome());
                participacaoEvento.setText("Sua participação está confirmada");

                break;
            }
        }
    }

    public void removeParticipante() {
        for (Evento evento : eventos) {
            if (evento.getNome().matches(dataNome.getText())) {
                Sessao sessao = new Sessao();
                String usuarioAtual = sessao.getUsuarioAtual();
                evento.removeParticipante(usuarioAtual, evento.getNome());
                participacaoEvento.setText("Sua participação não está confirmada");
                break;
            }
        }
    }

// roda quando a página é inicializada
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventos = Evento.carregarEventos();
        for (Evento evento : eventos) {
            HBox eventItem = new EventListItem(evento);
            eventList.getChildren().add(eventItem);
        }
// roda quando um evento da lista é selecionado
        /*eventList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String eventoSelecionado = eventList.getSelectionModel().getSelectedItem();
                Sessao sessao = new Sessao();
                String usuarioAtual = sessao.getUsuarioAtual();
                
                for (Evento evento : eventos) {
                    if (evento.getNome() == eventoSelecionado) {
                        dataNome.setText(evento.getNome());
                        dataEndereco.setText(evento.getEndereco());
                        dataCategoria.setText(evento.getCategoria());
                        dataHorario.setText(evento.getHorario().toString().replace("T", " "));
                        dataDescricao.setText(evento.getDescricao());
                        if (evento.isParticipante(usuarioAtual, evento.getNome())) {
                            participacaoEvento.setText("Sua participação está confirmada");
                        }
                        else {
                            participacaoEvento.setText("Sua participação não está confirmada");
                        }
                        break;
                    }
                }
            }
        });*/
    }

}

