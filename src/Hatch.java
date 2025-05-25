import java.util.ArrayList;
import java.util.List;

public class Hatch extends Carro {
    private double preco;
    private boolean isCompact;
    private static List<Hatch> hatchs = new ArrayList<>();

    public Hatch() {}

    public Hatch(String placa, String modelo, String marca, int ano, double preco, boolean isCompact) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.isCompact = isCompact;
    }

    // Getters e Setters específicos
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isCompact() {
        return isCompact;
    }

    public void setCompact(boolean compact) {
        isCompact = compact;
    }

    @Override
    public void cadastrarVeiculo(String placa, String modelo, String marca, int ano) {
        Hatch hatch = new Hatch();
        hatch.setPlaca(placa);
        hatch.setModelo(modelo);
        hatch.setMarca(marca);
        hatch.setAno(ano);
        carros.add(hatch);
        hatchs.add(hatch);
    }

    public double valorAno() {
        int anos = java.time.Year.now().getValue() - this.ano;
        //5% no primeiro ano, 15% no quinto ano, estabilizando em 25% após 10 anos
        double percentualDesvalorizacao = Math.min(0.25, 0.05 + (anos * 0.02)); // Máximo de 25% de desvalorização
        return this.preco * (1 - percentualDesvalorizacao);
    }

    public static List<Hatch> getTodosHatchs() {
        return new ArrayList<>(hatchs);
    }
}