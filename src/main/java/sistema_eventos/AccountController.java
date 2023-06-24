package sistema_eventos;

import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class AccountController extends Controller implements Initializable{

    private static List<Usuario> usuarios = new ArrayList<>();
    private static Usuario usuario;

    // elementos da interface definidos no arquivo FXML

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoIdade;
    @FXML
    private TextField campoEmail;
    @FXML
    private PasswordField campoSenha;
    
    @FXML
    private Label aviso;
    @FXML
    private Label avisoSenha;
    @FXML
    private Label avisoUsuario;
    @FXML
    private Label avisoIdade;
    @FXML
    private Label avisoEmail;

    
    
    // carrega os dados das contas ao ler o arquivo contas.data
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

    // registra um novo usuário
    public void cadastrarUsuario() throws IOException {
        Boolean dadosAceitos = true;
        avisoUsuario.setVisible(false); avisoIdade.setVisible(false); avisoEmail.setVisible(false); avisoSenha.setVisible(false);

        // validação dos inputs

        // testa se um nome foi inserido
        if (campoNome.getText().matches("")) {
            avisoUsuario.setVisible(true);
            dadosAceitos = false;
        }

        // testa se a idade inserida é um número inteiro
        try {
            Integer.parseInt(campoIdade.getText());
        } catch (Exception e) {
            avisoIdade.setVisible(true);
            dadosAceitos = false;
        }

        // testa se o email inserido é compatível com o formato (caracteres + @ + caracteres + . + 2 à 3 caracteres)
        if (!(campoEmail.getText().matches("\\w{1,}@[a-zA-Z]{1,}.\\w{2,3}"))) {
            avisoEmail.setVisible(true);
            dadosAceitos = false;
        }

        // testa se uma senha foi inserida
        if (campoSenha.getText().matches("")) {
            avisoSenha.setVisible(true);
            dadosAceitos = false;
        }

        // escreve os dados do usuário no arquivo contas.data se os dados foram válidados
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
        else {
            aviso.setVisible(true);
        }
    }

    // função de login do usuário
    public void loginUsuario() throws IOException {
        carregarContas(); // lê o arquivo contas.data e passa os dados para um ArrayList
        String usuarioAtual="";
        avisoUsuario.setVisible(false); avisoSenha.setVisible(false);
        String nome = campoNome.getText();
        String senha = campoSenha.getText();

        // se o nome de usuário estiver registrado, testa se a senha está correta
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                if (usuario.getSenha().equals(senha)) {
                    usuarioAtual = nome;
                    Sessao.atualizarSessao(usuarioAtual);
                    switchToHome();
                }
                else {
                    avisoSenha.setVisible(true);
                }
                break;
            }
            else {
                avisoUsuario.setVisible(true);
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
        displayCurrentUser();
    }
}



