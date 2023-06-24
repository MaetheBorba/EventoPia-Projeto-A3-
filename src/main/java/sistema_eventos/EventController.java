package sistema_eventos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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

public class EventController extends Controller implements Initializable{

    private static List<Evento> eventos = new ArrayList<>();

    // elementos da interface definidos no arquivo FXML
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

    

    // adiciona o usuário atual como participante do evento
    public void addParticipante() {
        Sessao sessao = new Sessao();
        String usuarioAtual = sessao.getUsuarioAtual();

        // muda para a página de login se um usuário não estiver logado
        if (usuarioAtual.matches("")) {
            try {
                App.setRoot("account-login");
            } catch (IOException e) {}
        }

        else {
            for (Evento evento : eventos) {
                if (evento.getNome().matches(dataNome.getText())) {
                    evento.addParticipante(usuarioAtual, evento.getNome());
                    statusParticipacao.setText("Sua participação neste evento está confirmada");

                    // muda o botão de participação para cancelar
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
    }

    // remove o usuário atual como participante do evento
    public void removeParticipante() {
        for (Evento evento : eventos) {
            if (evento.getNome().matches(dataNome.getText())) {
                Sessao sessao = new Sessao();
                String usuarioAtual = sessao.getUsuarioAtual();
                evento.removeParticipante(usuarioAtual, evento.getNome());
                statusParticipacao.setText("Sua participação neste evento não está confirmada");

                // muda o botão de participação para confirmar
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

    // roda quando a tela é inicializada
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPage();
    }

    @Override
    void loadPage() {
        String eventoSelecionado="";
        eventos = Evento.carregarEventos();
        Sessao sessao = new Sessao();
        String usuarioAtual = sessao.getUsuarioAtual();

        // mostrar nome de usuário no menu de opções
        displayCurrentUser();

        // salva temporariamente qual evento foi selecionado da lista
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/temp.data"))) {
            eventoSelecionado = reader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }

        // carrega as informações do evento
        for (Evento evento : eventos) {
            if (eventoSelecionado.matches(evento.getNome())) {
                String categoria = evento.getCategoria();

                // define a imagem com base na categoria
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

                // passa os dados do evento para os elementos da tela
                dataNome.setText(evento.getNome());
                dataEndereco.setText(evento.getEndereco());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                dataHorario.setText(evento.getHorario().format(formatter).toString());
                dataDescricao.setText(evento.getDescricao());

                if (evento.getHorario().isAfter(LocalDateTime.now())) {
                    // muda o botão de participação com base no status de participação do usuário
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
                }
                else {
                    btnParticipacao.setVisible(false);
                    statusParticipacao.setText("Este evento já passou :(");
                }
                break;
            }
        }
    }
}
