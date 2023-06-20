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
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;
    private String criador;

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao, String criador) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.criador = criador;
    }

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

    public static List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/events.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String nome = data[0];
                    String endereco = data[1];
                    String categoria = data[2];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime horario = LocalDateTime.parse(data[3], formatter);
                    String descricao = data[4];
                    String criador = data[5];

                    Evento evento = new Evento(nome, endereco, categoria, horario, descricao, criador);
                    eventos.add(evento);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
        }
        return eventos;
    }

    public boolean isParticipante(String usuario, String nomeEvento) {
        List<String> participantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/eventos/" + nomeEvento + ".data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                participantes.add(line);
            }
        } catch (IOException e) {
                    System.out.println("Erro ao carregar evento: " + e.getMessage());
            }
        if (participantes.contains(usuario)) {
            return true;
        }
        else {
            return false;
        } 
    }

    public void removeParticipante(String usuario, String nomeEvento) {
        List<String> participantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/eventos/" + nomeEvento + ".data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                participantes.add(line);
            }
        } catch (IOException e) {
                    System.out.println("Erro ao carregar evento: " + e.getMessage());
            }

        if (participantes.contains(usuario)) {
            participantes.remove(usuario);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/eventos/" + nomeEvento + ".data"))) {
            for (String participante : participantes) {
                String line = participante;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
                System.out.println("Erro ao salvar evento: " + e.getMessage());
            }
    }

    public void addParticipante(String usuario, String nomeEvento) {
        List<String> participantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/data/eventos/" + nomeEvento + ".data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                participantes.add(line);
            }
        } catch (IOException e) {
                    System.out.println("Erro ao carregar evento: " + e.getMessage());
            }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/data/eventos/" + nomeEvento + ".data"))) {
            for (String participante : participantes) {
                String line = participante;
                writer.write(line);
                writer.newLine();
            }
            if (!(participantes.contains(usuario))) {
                writer.write(usuario);
                writer.newLine();
            }
        } catch (IOException e) {
                System.out.println("Erro ao salvar evento: " + e.getMessage());
            }
    }
}
