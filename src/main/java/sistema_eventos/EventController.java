package sistema_eventos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EventController implements Initializable{

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
    private VBox event;

    @FXML
    private ImageView eventImage;
    @FXML
    private Button btnParticipacao;
    @FXML
    private TextArea dataDescricao;
    @FXML
    private Label dataEndereco;
    @FXML
    private Label dataHorario;
    @FXML
    private Label dataNome;

    @FXML
    private Label statusParticipacao;

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

    public void addParticipante() {
        for (Evento evento : eventos) {
            if (evento.getNome().matches(dataNome.getText())) {
                Sessao sessao = new Sessao();
                String usuarioAtual = sessao.getUsuarioAtual();
                evento.addParticipante(usuarioAtual, evento.getNome());
                statusParticipacao.setText("Sua participação neste evento está confirmada");

                btnParticipacao.setText("Cancelar participação");
                btnParticipacao.setStyle("-fx-background-color: rgba(200,0,0,.6);");
                btnParticipacao.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        removeParticipante();
                    }
                });

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

                btnParticipacao.setText("Confirmar participação");
                btnParticipacao.setStyle("-fx-background-color: rgba(0,200,0,.6);");
                btnParticipacao.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        addParticipante();
                    }
                });
                
                break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String eventoSelecionado="";
        eventos = Evento.carregarEventos();

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

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/temp.data"))) {
            eventoSelecionado = reader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }

        for (Evento evento : eventos) {
            if (eventoSelecionado.matches(evento.getNome())) {
                String categoria = evento.getCategoria();
                if (categoria.matches("Acad\u00EAmico/Educativo")) {
                    eventImage.setImage(new Image(new File("src/main/resources/images/categoria-academico.png").toURI().toString()));
                }
                else if (categoria.matches("Cultural/Entretenimento")) {
                    eventImage.setImage(new Image(new File("src/main/resources/images/categoria-cultural.png").toURI().toString()));
                }
                else if (categoria.matches("Esportivo")) {
                    eventImage.setImage(new Image(new File("src/main/resources/images/icon-eventopia.png").toURI().toString()));
                }
                else if (categoria.matches("Corporativo")) {
                    eventImage.setImage(new Image(new File("src/main/resources/images/categoria-corporativo.png").toURI().toString()));
                }
                else if (categoria.matches("Religioso")) {
                    eventImage.setImage(new Image(new File("src/main/resources/images/categoria-religioso.png").toURI().toString()));
                }
                else if (categoria.matches("Social")) {
                    eventImage.setImage(new Image(new File("src/main/resources/images/categoria-social.png").toURI().toString()));
                }
                else {
                    eventImage.setImage(new Image(new File("src/main/resources/images/icon-eventopia.png").toURI().toString()));
                }

                dataNome.setText(evento.getNome());
                dataEndereco.setText(evento.getEndereco());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                dataHorario.setText(evento.getHorario().format(formatter).toString());
                dataDescricao.setText(evento.getDescricao());

                if (evento.isParticipante(usuarioAtual, eventoSelecionado)) {
                    statusParticipacao.setText("Sua participação neste evento está confirmada");
                    btnParticipacao.setText("Cancelar participação");
                    btnParticipacao.setStyle("-fx-background-color: rgba(200,0,0,.6);");
                    btnParticipacao.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            removeParticipante();
                        }
                    });
                }
                else {
                    btnParticipacao.setStyle("-fx-background-color: rgba(0,200,0,.6);");
                }
                break;
            }
        }
    }
}
