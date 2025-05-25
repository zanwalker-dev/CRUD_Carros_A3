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

    public static boolean atualizarHatch(String placa, double novoPreco, boolean novoIsCompact) {
        for (Hatch hatch : hatchs) {
            if (hatch.getPlaca().equals(placa)) {
                hatch.setPreco(novoPreco);
                hatch.setCompact(novoIsCompact);
                return true;
            }
        }
        return false;
    }

    public double valorAno() {
        int anos = java.time.Year.now().getValue() - this.ano;
        // Hatch desvaloriza mais: 10% no primeiro ano, 25% no quinto ano, até 40% máximo
        double percentualDesvalorizacao = Math.min(0.40, 0.10 + (anos * 0.03));
        return this.preco * (1 - percentualDesvalorizacao);
    }

    public static List<Hatch> getTodosHatchs() {
        return new ArrayList<>(hatchs);
    }
}