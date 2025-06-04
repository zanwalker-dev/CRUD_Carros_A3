import java.util.ArrayList;
import java.util.List;

public abstract class Carro {
    protected String placa;
    protected String modelo;
    protected String marca;
    protected int ano;
    protected int quilometragem;
    protected static List<Carro> carros = new ArrayList<>();

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public abstract double valorAtual();
}