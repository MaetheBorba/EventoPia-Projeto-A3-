package sistema_eventos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Classe principal da aplicação JavaFX.
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Carrega o arquivo FXML "home.fxml" e cria uma cena com base nele
        scene = new Scene(loadFXML("home"));

        // Configura a cena no palco (stage)
        stage.setScene(scene);

        // Define o título da janela
        stage.setTitle("EventoPia");

        // Define o ícone da janela
        stage.getIcons().add(new Image(new File("src/main/resources/images/icon-eventopia.png").toURI().toString()));

        // Mostra a janela
        stage.show();
    }

    /**
     * Define o arquivo FXML especificado como raiz da cena.
     *
     * @param fxml o nome do arquivo FXML a ser carregado
     * @throws IOException se ocorrer um erro durante o carregamento do arquivo FXML
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Obtém a raiz atual da cena.
     *
     * @return o nó raiz da cena
     */
    static Parent getRoot() {
        Parent root = scene.getRoot();
        return root;
    }

    /**
     * Carrega o arquivo FXML especificado e retorna o nó raiz correspondente.
     *
     * @param fxml o nome do arquivo FXML a ser carregado
     * @return o nó raiz do arquivo FXML
     * @throws IOException se ocorrer um erro durante o carregamento do arquivo FXML
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Método de teste simples que imprime uma mensagem de teste.
     */
    public static void test() {
        System.out.println("test");
    }

    /**
     * O método principal que inicia a aplicação.
     *
     * @param args os argumentos de linha de comando (não são usados neste exemplo)
     */
    public static void main(String[] args) {
        launch();
    }

}
