package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventRegisterController implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();
    private static Evento evento;
    private String[] categorias = {"Social","Corporativo","Religioso","Acadêmico/Educativo","Cultural/Entretenimento","Esportivo"};

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoEndereco;
    @FXML
    private ChoiceBox<String> campoCategoria;
    @FXML
    private TextField campoHorario;
    @FXML
    private TextField campoDescricao;

    @FXML
    private Label avisoHorario;
    @FXML
    private Label avisoCategoria;
    @FXML
    private Label avisoDescricao;
    @FXML
    private Label avisoEndereco;
    @FXML
    private Label avisoNome;

    
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

    

    public void cadastrarEvento() throws IOException {
        Boolean dadosAceitos = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        avisoNome.setText(""); avisoEndereco.setText(""); avisoCategoria.setText(""); avisoHorario.setText(""); avisoDescricao.setText("");

        if (campoNome.getText().matches("")) {
            avisoNome.setText("Adicione um nome");
            dadosAceitos = false;
        }

        if (campoEndereco.getText().matches("")) {
            avisoEndereco.setText("Adicione um endereço");
            dadosAceitos = false;
        }

        if (campoCategoria.getValue().matches("Categoria")) {
            avisoCategoria.setText("Escolha uma categoria");
            dadosAceitos = false;
        }

        try {
            LocalDateTime.parse(campoHorario.getText(), formatter);
        } catch (Exception e) {
            avisoHorario.setText("Horário inválido.\nSiga o formato dd/MM/yyyy HH:mm");
            dadosAceitos = false;
        }

        if (campoDescricao.getText().matches("")) {
            avisoDescricao.setText("Adicione uma descrição");
            dadosAceitos = false;
        }

        if (dadosAceitos) {
            eventos = Evento.carregarEventos();
            String nome = campoNome.getText();
            String endereco = campoEndereco.getText();
            String categoria = campoCategoria.getValue();
            LocalDateTime horario = LocalDateTime.parse(campoHorario.getText(), formatter);
            String descricao = campoDescricao.getText();
            evento = new Evento(nome, endereco, categoria, horario, descricao);
            eventos.add(evento);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/eventos/" + nome + ".data"))) {
                System.out.println("Evento salvo com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao salvar evento: " + e.getMessage());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/events.data"))) {
                for (Evento evento : eventos) {
                    String line = evento.getNome() + "," +
                            evento.getEndereco() + "," +
                            evento.getCategoria() + "," +
                            evento.getHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "," +
                            evento.getDescricao();
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Eventos salvos com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao salvar eventos: " + e.getMessage());
            }

            switchToHome();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoCategoria.setValue("Categoria");
        campoCategoria.getItems().addAll(categorias);
    }
}

