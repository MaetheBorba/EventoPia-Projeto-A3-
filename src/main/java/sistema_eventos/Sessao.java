package sistema_eventos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Sessao {
    String usuarioAtual; Boolean salvarSessao;

    public Sessao() {
        carregarSessao();
    }

    public String getUsuarioAtual() {
        return usuarioAtual;
    }
    public void setUsuarioAtual(String usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
    }

    public Boolean getSalvarSessao() {
        return salvarSessao;
    }
    public void setSalvarSessao(Boolean salvarSessao) {
        this.salvarSessao = salvarSessao;
    }

    public static void atualizarSessao(String usuarioAtual) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/sessao.data"))) {
            
            String line = usuarioAtual;
            writer.write(line);
            
            System.out.println("Sessão atualizada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar sessão: " + e.getMessage());
        }

    }

    public void carregarSessao() {
        String sessaoAtual="";
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/sessao.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 1) {
                    sessaoAtual = data[0];
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar sessão: " + e.getMessage());
        }
        setUsuarioAtual(sessaoAtual);
    }
}
