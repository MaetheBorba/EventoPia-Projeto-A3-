package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class AccountController implements Initializable{

    private static List<Usuario> usuarios = new ArrayList<>();
    private static Usuario usuario;

    @FXML
    private Label accountName;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnSair;

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoIdade;
    @FXML
    private TextField campoEmail;
    @FXML
    private PasswordField campoSenha;
    
    @FXML
    private Label avisoSenha;
    @FXML
    private Label avisoUsuario;
    @FXML
    private Label avisoIdade;
    @FXML
    private Label avisoEmail;

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
    private void switchToAccountRegister() throws IOException {
        App.setRoot("account-register");
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

    public static void carregarContas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/contas.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String nome = data[0];
                    String idade = data[1];
                    String email = data[2];
                    String senha = data[3];

                    Usuario usuario = new Usuario(nome, idade, email, senha);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contas: " + e.getMessage());
        }
    }

    public void cadastrarUsuario() throws IOException {
        Boolean dadosAceitos = true;
        avisoIdade.setText(""); avisoEmail.setText("");

        // validação dos inputs
        try {
            Integer.parseInt(campoIdade.getText());
        } catch (Exception e) {
            avisoIdade.setText("Idade inválida");
            dadosAceitos = false;
        }

        if (!(campoEmail.getText().matches("\\w{1,}@[a-zA-Z]{1,}.\\w{2,3}"))) {
            avisoEmail.setText("Email inválido");
            dadosAceitos = false;
        }

        if (dadosAceitos) {
            carregarContas();
            String nome = campoNome.getText();
            String idade = campoIdade.getText();
            String email = campoEmail.getText();
            String senha = campoSenha.getText();

            usuario = new Usuario(nome, idade, email, senha);
            usuarios.add(usuario);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/contas.data"))) {
                for (Usuario usuario : usuarios) {
                    String line = usuario.getNome() + "," +
                            usuario.getIdade() + "," +
                            usuario.getEmail() + "," +
                            usuario.getSenha();
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Conta registrada com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao registrar conta: " + e.getMessage());
            }

            switchToHome();
        }
    }

    public void loginUsuario() throws IOException {
        carregarContas();
        String usuarioAtual="";
        avisoSenha.setText("");
        String nome = campoNome.getText();
        String senha = campoSenha.getText();

        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                if (usuario.getSenha().equals(senha)) {
                    usuarioAtual = nome;
                    Sessao.atualizarSessao(usuarioAtual);
                    switchToHome();
                }
                else {
                    avisoSenha.setText("Senha incorreta");
                }
                break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
}



