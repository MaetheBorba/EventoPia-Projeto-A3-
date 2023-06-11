package sistema_eventos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Sessao {
    String usuarioAtual;

    public Sessao(String setUsuarioAtual) {
        this.usuarioAtual = setUsuarioAtual;
    }

    public String getUsuarioAtual() {
        return usuarioAtual;
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

    public static String carregarSessao() {
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
        return sessaoAtual;
    }
}
