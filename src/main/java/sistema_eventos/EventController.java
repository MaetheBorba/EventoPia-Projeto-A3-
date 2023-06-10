package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventController implements Initializable {

    private static List<Evento> eventos = new ArrayList<>();
    private static Evento evento;

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
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    public static void carregarEventos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/events.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String nome = data[0];
                    String endereco = data[1];
                    String categoria = data[2];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime horario = LocalDateTime.parse(data[3], formatter);
                    String descricao = data[4];

                    Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
        }
    }

    public void cadastrarEvento() throws IOException {
        String nome = campoNome.getText();
        String endereco = campoEndereco.getText();
        String categoria = campoCategoria.getText();
        String horarioStr = campoHorario.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime horario = LocalDateTime.parse(horarioStr, formatter);
        String descricao = campoDescricao.getText();

        evento = new Evento(nome, endereco, categoria, horario, descricao);
        eventos.add(evento);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Evento evento : eventos) {
            eventList.getItems().add(evento.getNome());
        }

        eventList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                for (Evento evento : eventos) {
                    if (evento.getNome() == eventList.getSelectionModel().getSelectedItem()) {
                        dataNome.setText(evento.getNome());
                        dataEndereco.setText(evento.getEndereco());
                        dataCategoria.setText(evento.getCategoria());
                        dataHorario.setText(evento.getHorario().toString());
                        dataDescricao.setText(evento.getDescricao());
                    }
                }
            }
        });
    }

}

