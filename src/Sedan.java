import java.time.Year;

public class Sedan extends Carro {
    private double preco;
    private double espacoPortaMala;

    public Sedan() {}

    public Sedan(String placa, String modelo, String marca, int ano, int quilometragem, double preco, double espacoPortaMala) {
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

    @Override
    public void cadastrarVeiculo() {
        GerenciadorVeiculos.adicionar(this);
    }

    @Override
    public double valorAtual() {
        int anos = Year.now().getValue() - this.ano;
        double percentualAno = Math.min(0.30, 0.07 + (anos * 0.026));
        double percentualKm = Math.min(0.20, (this.quilometragem / 1000) * 0.0025);
        return this.preco * (1 - Math.min(0.50, percentualAno + percentualKm));
    }
}