package sistema_eventos;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventListItem extends HBox{

    @FXML
    private void switchToEvent() throws IOException {
        App.setRoot("event");
    }

    public EventListItem(Evento evento) {
        ImageView imagem = new ImageView(new File("src/main/resources/images/icon-eventopia.png").toURI().toString());
        imagem.setId("event-item-image");
        
        VBox info = new VBox();
        info.setId("event-item-info-box");

        Label nome = new Label(evento.getNome());
        nome.setId("event-item-info");
        Label endereco = new Label("Endereço: " + evento.getEndereco());
        endereco.setId("event-item-info");
        Label categoria = new Label("Categoria: " + evento.getCategoria());
        categoria.setId("event-item-info");
        Label horario = new Label("Horário: " + evento.getHorario().toString().replace("T", " ").replace("-", "/"));
        horario.setId("event-item-info");
        Button eventItemBtn = new Button("Ver mais");
        eventItemBtn.setId("event-item-btn");
        eventItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    switchToEvent();
                } catch (IOException e) {}
            }
        });

        info.getChildren().addAll(nome,endereco,categoria,horario,eventItemBtn);

        this.setId("event-item");
        this.getChildren().addAll(imagem, info);
    }
}
