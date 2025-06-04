import java.time.Year;

public class Hatch extends Carro {
    private double preco;
    private boolean isCompact;

    public Hatch() {}

    public Hatch(String placa, String modelo, String marca, int ano, int quilometragem, double preco, boolean isCompact) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.preco = preco;
        this.isCompact = isCompact;
    }

    // Getters e Setters
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
    public void cadastrarVeiculo() {
        GerenciadorVeiculos.adicionar(this);
    }

    @Override
    public double valorAtual() {
        int anos = Year.now().getValue() - this.ano;
        double percentualAno = Math.min(0.40, 0.10 + (anos * 0.03));
        double percentualKm = Math.min(0.25, (this.quilometragem / 1000) * 0.0035);
        return this.preco * (1 - Math.min(0.60, percentualAno + percentualKm));
    }
}