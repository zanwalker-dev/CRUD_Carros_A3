import java.time.Year;

public class SUV extends Carro {
    private double preco;
    private String tracao;

    public SUV() {}

    public SUV(String placa, String modelo, String marca, int ano,
               int quilometragem, double preco, String tracao) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.preco = preco;
        setTracao(tracao);
    }

    // Getters e Setters
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTracao() {
        return tracao;
    }

    public void setTracao(String tracao) {
        if (!tracao.equals("4x4") && !tracao.equals("2x4")) {
            throw new IllegalArgumentException("Tração deve ser '4x4' ou '2x4'");
        }
        this.tracao = tracao;
    }

    @Override
    public void cadastrarVeiculo() {
        GerenciadorVeiculos gerenciador = new GerenciadorVeiculos();
        gerenciador.adicionar(this);
    }

    @Override
    public double valorAtual() {
        int anos = Year.now().getValue() - this.ano;
        double percentualAno = Math.min(0.25, 0.05 + (anos * 0.02));
        double percentualKm = Math.min(0.15, (this.quilometragem / 1000) * 0.0018);
        return this.preco * (1 - Math.min(0.40, percentualAno + percentualKm));
    }
}