package sistema_eventos;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

abstract class Controller {

    // elementos da interface definidos no arquivo FXML
    @FXML
    protected Label accountName;
    @FXML
    protected Button btnLogin;
    @FXML
    protected Button btnRegister;
    @FXML
    protected Button btnSair;
    
    // funções do menu de opções

    // muda para a tela inicial
    @FXML
    protected void switchToHome() throws IOException {
        App.setRoot("home");
    }

    // realiza o logout do usuário, retorna para a tela inicial (home.fxml) e limpa a sessão atual
    @FXML
    protected void logoutUsuario() throws IOException {
        accountName.setText("Visitante");
        Sessao.atualizarSessao("");
        switchToHome();
    }

    // muda para a tela de login (account-login.fxml)
    @FXML
    protected void switchToAccountLogin() throws IOException {
        App.setRoot("account-login");
    }

    // muda para a tela de registro de eventos (event-register.fxml)
    @FXML
    protected void switchToEventRegister() throws IOException {
        Sessao sessao = new Sessao();
        if (sessao.getUsuarioAtual().matches("")) {
            App.setRoot("account-login");
        }
        else {
            App.setRoot("event-register");
        }
    }

    // muda para a tela de eventos que o usuário está participando ('event-participating.fxml)
    @FXML
    protected void switchToEventParticipating() throws IOException {
        Sessao sessao = new Sessao();
        if (sessao.getUsuarioAtual().matches("")) {
            App.setRoot("account-login");
        }
        else {
            App.setRoot("event-participating");
        }
    }

    // muda para a tela de eventos criados pelo usuário (account-login.fxml)
    @FXML
    protected void switchToEventCreated() throws IOException {
        Sessao sessao = new Sessao();
        if (sessao.getUsuarioAtual().matches("")) {
            App.setRoot("account-login");
        }
        else {
            App.setRoot("event-created");
        }
    }

    // muda para a tela de eventos recentes (event-list.fxml)
    @FXML
    protected void switchToEventList() throws IOException {
        App.setRoot("event-list");
    }

    // muda para a tela de registro de eventos (account-login.fxml)
    @FXML
    protected void switchToAccountRegister() throws IOException {
        App.setRoot("account-register");
    }

    // muda para a tela de eventos por categoria (event-categories.fxml)
    @FXML
    protected void switchToEventCategories() throws IOException {
        App.setRoot("event-categories");
    }
    
    // muda para a tela de eventos próximos (event-next.fxml)
    @FXML
    protected void switchToEventNext() throws IOException {
        App.setRoot("event-next");
    }

    // muda para a tela de eventos passados (event-previous.fxml)
    @FXML
    protected void switchToEventPrevious() throws IOException {
        App.setRoot("event-previous");
    }

    protected void displayCurrentUser() {
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
    }

    abstract void loadPage();
}
