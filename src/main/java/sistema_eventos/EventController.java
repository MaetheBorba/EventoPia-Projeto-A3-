package sistema_eventos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventController implements Initializable{

    private static List<Evento> eventos = new ArrayList<>();

    @FXML
    private VBox event;

    @FXML
    private Label dataCategoria;
    @FXML
    private Label dataDescricao;
    @FXML
    private Label dataEndereco;
    @FXML
    private Label dataHorario;
    @FXML
    private Label dataNome;

    @FXML
    private Label statusParticipacao;

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

    public void addParticipante() {
        for (Evento evento : eventos) {
            if (evento.getNome().matches(dataNome.getText())) {
                Sessao sessao = new Sessao();
                String usuarioAtual = sessao.getUsuarioAtual();
                evento.addParticipante(usuarioAtual, evento.getNome());
                statusParticipacao.setText("Sua participação neste evento está confirmada");

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
                statusParticipacao.setText("Sua participação neste evento não está confirmada");
                break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String eventoSelecionado="";
        eventos = Evento.carregarEventos();
        Sessao sessao = new Sessao();
        String usuarioAtual = sessao.getUsuarioAtual();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/temp.data"))) {
            eventoSelecionado = reader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }

        for (Evento evento : eventos) {
            if (eventoSelecionado.matches(evento.getNome())) {
                dataNome.setText(evento.getNome());
                dataEndereco.setText(evento.getEndereco());
                dataCategoria.setText(evento.getCategoria());
                dataHorario.setText(evento.getHorario().toString().replace("T", " ").replace("-", "/"));
                dataDescricao.setText(evento.getDescricao());

                if (evento.isParticipante(usuarioAtual, eventoSelecionado)) {
                    statusParticipacao.setText("Sua participação neste evento está confirmada");
                }
            }
        }
    }
}
