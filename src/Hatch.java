import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Hatch extends Carro {
    private double preco;
    private boolean isCompact;
    private static List<Hatch> hatchs = new ArrayList<>();

    public Hatch() {}

    public Hatch(String placa, String modelo, String marca, int ano, int quilometragem, double preco, boolean isCompact) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.isCompact = isCompact;
        this.quilometragem = quilometragem;
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

    // Métodos CRUD
    @Override
    public void cadastrarVeiculo() {
        carros.add(this);
        hatchs.add(this);
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

    @Override
    public double valorAtual() {
        // Desvalorização por ano (Hatch desvaloriza mais. Primeiro ano:10%, quinto ano: 25%, teto: 40%)
        int anos = Year.now().getValue() - this.ano;
        double percentualAno = Math.min(0.40, 0.10 + (anos * 0.03));
        // Desvalorização por KM (0.35% a cada 1.000 km)
        double percentualKm = Math.min(0.25, (this.quilometragem / 1000) * 0.0035);

        return this.preco * (1 - Math.min(0.60, percentualAno + percentualKm));
    }

    public static List<Hatch> getTodosHatchs() {
        return new ArrayList<>(hatchs);
    }
}