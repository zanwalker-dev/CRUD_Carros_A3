import java.util.ArrayList;
import java.util.List;

public class Sedan extends Carro {
    private double preco;
    private double espacoPortaMala;
    private static List<Sedan> sedans = new ArrayList<>();

    public Sedan() {}

    public Sedan(String placa, String modelo, String marca, int ano, double preco, double espacoPortaMala) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.espacoPortaMala = espacoPortaMala;
    }

    // Getters e Setters específicos
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
    public void cadastrarVeiculo(String placa, String modelo, String marca, int ano) {
        Sedan sedan = new Sedan();
        sedan.setPlaca(placa);
        sedan.setModelo(modelo);
        sedan.setMarca(marca);
        sedan.setAno(ano);
        carros.add(sedan);
        sedans.add(sedan);
    }

    public double valorAno() {
        int anos = java.time.Year.now().getValue() - this.ano;
        return this.preco * Math.pow(0.85, anos); // Desvalorização de 15% ao ano
    }

    public static List<Sedan> getTodosSedans() {
        return new ArrayList<>(sedans);
    }
}