import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Sedan extends Carro {
    private double preco;
    private double espacoPortaMala;
    private static List<Sedan> sedans = new ArrayList<>();

    public Sedan() {}

    public Sedan(String placa, String modelo, String marca, int ano, int quilometragem,double preco, double espacoPortaMala) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.preco = preco;
        this.espacoPortaMala = espacoPortaMala;
    }

    // Getters e Setters
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getEspacoPortaMala() {
        return espacoPortaMala;
    }

    public void setEspacoPortaMala(double espacoPortaMala) {
        this.espacoPortaMala = espacoPortaMala;
    }

    // Métodos CRUD
    @Override
    public void cadastrarVeiculo() {
        if (Carro.placaExistente(this.placa)) {
            throw new IllegalArgumentException("Placa já cadastrada");
        }
        carros.add(this);
        sedans.add(this);
    }

    public static boolean atualizarSedan(String placa, double novoPreco, double novoEspacoPortaMala) {
        for (Sedan sedan : sedans) {
            if (sedan.getPlaca().equals(placa)) {
                sedan.setPreco(novoPreco);
                sedan.setEspacoPortaMala(novoEspacoPortaMala);
                return true;
            }
        }
        return false;
    }

    @Override
    public double valorAtual() {
        int anos = Year.now().getValue() - this.ano;
        // Desvalorização por ano (Sedan desvaloriza médio. Primeiro ano:7%, quinto ano: 20%, teto: 30%)
        double percentualAno = Math.min(0.30, 0.07 + (anos * 0.026));
        // Desvalorização menor por KM (0.25% a cada 1.000 km)
        double percentualKm = Math.min(0.20, (this.quilometragem / 1000) * 0.0025);

        return this.preco * (1 - Math.min(0.50, percentualAno + percentualKm));
    }


    public static List<Sedan> getTodosSedans() {
        return new ArrayList<>(sedans);
    }
}