import java.util.ArrayList;
import java.util.List;

public class SUV extends Carro {
    private double preco;
    private String tracao; // "4x4" ou "2x4"
    private static List<SUV> suvs = new ArrayList<>();

    public SUV() {}

    public SUV(String placa, String modelo, String marca, int ano, double preco, String tracao) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.tracao = tracao;
    }

    // Getters e Setters específicos
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

    @Override
    public void cadastrarVeiculo(String placa, String modelo, String marca, int ano) {
        SUV suv = new SUV();
        suv.setPlaca(placa);
        suv.setModelo(modelo);
        suv.setMarca(marca);
        suv.setAno(ano);
        carros.add(suv);
        suvs.add(suv);
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

    public double valorAno() {
        int anos = java.time.Year.now().getValue() - this.ano;
        // SUV desvaloriza pouco: 5% no primeiro ano, 15% no quinto ano, até 25% máximo
        double percentualDesvalorizacao = Math.min(0.25, 0.05 + (anos * 0.02));
        return this.preco * (1 - percentualDesvalorizacao);
    }

    public static List<SUV> getTodosSUVs() {
        return new ArrayList<>(suvs);
    }
}