package sistema_eventos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class Evento {
    // variáveis para armazenar os dados do evento
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;
    private String criador;

    // construtor
    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao, String criador) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.criador = criador;
    }

    // métodos para mudar ou receber os dados do evento
    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCriador() {
        return criador;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    // função para carregar os eventos salvos no arquivo events.data
    public static List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/events.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",,,"); // divide a linha em partes separadas pelo separador ",,," e armazena em um array
                if (data.length == 6) { // verifica se há exatamente 6 partes

                    // armazena os elementos nas variáveis do evento
                    String nome = data[0];
                    String endereco = data[1];
                    String categoria = data[2];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // cria um formatador de data e hora
                    LocalDateTime horario = LocalDateTime.parse(data[3], formatter); // converte o quarto elemento para um LocalDateTime usando o formatador
                    String descricao = data[4].replace("/n", "\n"); // armazena o quinto elemento como descrição do evento, substituindo "/n" por quebras de linha
                    String criador = data[5];

                    // cria um novo objeto Evento com os valores obtidos e o adiciona à lista de eventos
                    Evento evento = new Evento(nome, endereco, categoria, horario, descricao, criador);
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
        }
        return eventos; // retorna a lista de eventos carregados
    }

    public boolean isParticipante(String usuario, String nomeEvento) {
        List<String> participantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/eventos/" + nomeEvento + ".data"))) {
            String line;
            while ((line = reader.readLine()) != null) { // lê cada linha do arquivo
                participantes.add(line); // adiciona a linha à lista de participantes
            }
        } catch (IOException e) {
                    System.out.println("Erro ao carregar evento: " + e.getMessage());
            }

        // verifica se a lista de participantes contém o usuário fornecido
        if (participantes.contains(usuario)) {
            return true;
        }
        else {
            return false;
        } 
    }

    public void removeParticipante(String usuario, String nomeEvento) {
        List<String> participantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/eventos/" + nomeEvento + ".data"))) { // lê o arquivo correspondente ao evento fornecido
            String line;
            while ((line = reader.readLine()) != null) { // lê cada linha do arquivo
                participantes.add(line); // adiciona a linha à lista de participantes
            }
        } catch (IOException e) {
                    System.out.println("Erro ao carregar evento: " + e.getMessage());
            }

        // remove o usuário fornecido à lista de participantes se o usuário estiver na lista
        if (participantes.contains(usuario)) {
            participantes.remove(usuario);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/eventos/" + nomeEvento + ".data"))) { // escreve no arquivo correspondente ao evento fornecido
            for (String participante : participantes) { // percorre a lista de participantes atualizada
                String line = participante; // obtém cada participante
                writer.write(line); // escreve o participante no arquivo
                writer.newLine(); // escreve uma nova linha após cada participante
            }
        } catch (IOException e) {
                System.out.println("Erro ao salvar evento: " + e.getMessage());
            }
    }

    public void addParticipante(String usuario, String nomeEvento) {
        List<String> participantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/eventos/" + nomeEvento + ".data"))) { // lê o arquivo correspondente ao evento fornecido
            String line;
            while ((line = reader.readLine()) != null) { // lê cada linha do arquivo
                participantes.add(line); // adiciona a linha à lista de participantes
            }
        } catch (IOException e) {
                    System.out.println("Erro ao carregar evento: " + e.getMessage());
            }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/eventos/" + nomeEvento + ".data"))) { // escreve no arquivo correspondente ao evento fornecido
            for (String participante : participantes) { // percorre a lista de participantes atualizada
                String line = participante; // obtém cada participante
                writer.write(line); // escreve o participante no arquivo
                writer.newLine(); // escreve uma nova linha após cada participante
            }

            // adiciona o usuário à lista de participantes se o usuário não estiver na lista
            if (!(participantes.contains(usuario))) {
                writer.write(usuario);
                writer.newLine();
            }
        } catch (IOException e) {
                System.out.println("Erro ao salvar evento: " + e.getMessage());
            }
    }
}
