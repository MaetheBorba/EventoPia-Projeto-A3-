package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;

public class EventController implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoEndereco;
    @FXML
    private TextField campoCategoria;
    @FXML
    private TextField campoHorario;
    @FXML
    private TextField campoDescricao;

    @FXML
    private ListView<String> eventList;
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

    public void addParticipante() {
        for (Evento evento : eventos) {
            if (evento.getNome().matches(dataNome.getText())) {
                String usuarioAtual = Sessao.carregarSessao();
                evento.addParticipante(usuarioAtual, evento.getNome());
                participacaoEvento.setText("Sua participação está confirmada");

                break;
            }
        }
    }

    public void removeParticipante() {
        for (Evento evento : eventos) {
            if (evento.getNome().matches(dataNome.getText())) {
                String usuarioAtual = Sessao.carregarSessao();
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
            eventList.getItems().add(evento.getNome());
        }
// roda quando um evento da lista é selecionado
        eventList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String eventoSelecionado = eventList.getSelectionModel().getSelectedItem();
                String usuarioAtual = Sessao.carregarSessao();
                
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
        });
    }

}

