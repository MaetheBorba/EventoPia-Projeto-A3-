package sistema_eventos;

public class Usuario {

    // variáveis para armazenar os dados do usuário
    private String nome;
    private String idade;
    private String email;
    private String senha;

    // construtor
    public Usuario(String nome2, String idade2, String email2, String senha2) {
        this.nome = nome2;
        this.idade = idade2;
        this.email = email2;
        this.senha = senha2;
    }

    
    // métodos para mudar ou receber os dados do usuário
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
