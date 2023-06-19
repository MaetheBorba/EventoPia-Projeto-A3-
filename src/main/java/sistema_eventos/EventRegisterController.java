package sistema_eventos;

import java.io.*;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventRegisterController {

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
    private Label avisoHorario;

    
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

    

    public void cadastrarEvento() throws IOException {
        Boolean dadosAceitos = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            LocalDateTime.parse(campoHorario.getText(), formatter);
        } catch (Exception e) {
            avisoHorario.setText("Horário inválido.\nSiga o formato dd/MM/yyyy HH:mm");
            dadosAceitos = false;
        }

        if (dadosAceitos) {
            eventos = Evento.carregarEventos();
            String nome = campoNome.getText();
            String endereco = campoEndereco.getText();
            String categoria = campoCategoria.getText();
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
}

