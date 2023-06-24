/* Classe para a criação dos cards que aparecem na lista de eventos */

package sistema_eventos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EventListItem extends HBox{

    // muda para a tela com as informações completas do evento (event.fxml)
    @FXML
    private void switchToEvent() throws IOException {
        App.setRoot("event");
    }

    public EventListItem(Evento evento) {
        ImageView imagem = new ImageView();
        String categoriaStr = evento.getCategoria();

        // define a imagem com base na categoria
        if (categoriaStr.matches("Acad\u00EAmico/Educativo")) {
            imagem = new ImageView(new File("src/main/resources/images/categoria-academico.png").toURI().toString());
        }
        else if (categoriaStr.matches("Cultural/Entretenimento")) {
            imagem = new ImageView(new File("src/main/resources/images/categoria-cultural.png").toURI().toString());
        }
        else if (categoriaStr.matches("Esportivo")) {
            imagem = new ImageView(new File("src/main/resources/images/categoria-esportivo.png").toURI().toString());
        }
        else if (categoriaStr.matches("Corporativo")) {
            imagem = new ImageView(new File("src/main/resources/images/categoria-corporativo.png").toURI().toString());
        }
        else if (categoriaStr.matches("Religioso")) {
            imagem = new ImageView(new File("src/main/resources/images/categoria-religioso.png").toURI().toString());
        }
        else if (categoriaStr.matches("Social")) {
            imagem = new ImageView(new File("src/main/resources/images/categoria-social.png").toURI().toString());
        }
        else {
            imagem = new ImageView(new File("src/main/resources/images/icon-eventopia.png").toURI().toString());
        }
        imagem.setId("event-item-image");
        
        VBox info = new VBox();
        info.setId("event-item-info-box");

        // elementos que contém os dados do evento
        Label nome = new Label(evento.getNome());
        nome.setId("event-item-info");
        Label endereco = new Label("Endereço: " + evento.getEndereco());
        endereco.setId("event-item-info");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Label horario = new Label("Horário: " + evento.getHorario().format(formatter).toString());
        horario.setId("event-item-info");
        
        // botão de "Ver mais"
        Button eventItemBtn = new Button("Ver mais");
        eventItemBtn.setId("btn-round");

        // define a função chamada quando o botão "Ver mais" é clicado
        eventItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/temp.data"))) { // carrega qual evento foi selecionado pela lista ao ler o arquivo temp.data
                        writer.write(evento.getNome());
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    switchToEvent();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });

        // adiciona os dados do evento à parte de informações do card
        info.getChildren().addAll(nome,endereco,horario,eventItemBtn);
        info.setSpacing(5);

        this.setSpacing(20);
        this.setId("event-item");
        this.getChildren().addAll(imagem, info); // adiciona a imagem e as informações ao card
        EventListItem.setHgrow(imagem, Priority.ALWAYS);
        EventListItem.setHgrow(info, Priority.ALWAYS);
    }
}
