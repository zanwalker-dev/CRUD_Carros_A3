import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class SUV extends Carro {
    private double preco;
    private String tracao;
    private static List<SUV> suvs = new ArrayList<>();

    public SUV() {}

    public SUV(String placa, String modelo, String marca, int ano, int quilometragem, double preco, String tracao) {
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
        if (tracao.equals("4x4") || tracao.equals("2x4")) {
            this.tracao = tracao;
        } else {
            throw new IllegalArgumentException("Tração deve ser '4x4' ou '2x4'");
        }
    }

    // Métodos CRUD
    public void cadastrarVeiculo() {
        carros.add(this);
        suvs.add(this);
    }

    public static boolean atualizarSUV(String placa, double novoPreco, String novaTracao) {
        for (SUV suv : suvs) {
            if (suv.getPlaca().equals(placa)) {
                suv.setPreco(novoPreco);
                try {
                    suv.setTracao(novaTracao);
                    return true;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public double valorAtual() {
        int anos = Year.now().getValue() - this.ano;
        // Desvalorização por ano (SUV desvaloriza pouco. Primeiro ano:5%, quinto ano: 15%, teto: 25%)
        double percentualAno = Math.min(0.25, 0.05 + (anos * 0.02));
        // Menor desvalorização por KM (0.18% a cada 1.000 km)
        double percentualKm = Math.min(0.15, (this.quilometragem / 1000) * 0.0018);

        return this.preco * (1 - Math.min(0.40, percentualAno + percentualKm));
    }

    public static List<SUV> getTodosSUVs() {
        return new ArrayList<>(suvs);
    }
}