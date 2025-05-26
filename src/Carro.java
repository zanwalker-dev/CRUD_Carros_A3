import java.util.ArrayList;
import java.util.List;

public abstract class Carro {
    protected String placa;
    protected String modelo;
    protected String marca;
    protected int ano;
    protected int quilometragem;
    protected static List<Carro> carros = new ArrayList<>();

    public static boolean atualizarVeiculo(String placa, String novoModelo, String novaMarca, int novoAno) {
        for (Carro carro : carros) {
            if (carro.getPlaca().equals(placa)) {
                carro.setModelo(novoModelo);
                carro.setMarca(novaMarca);
                carro.setAno(novoAno);
                return true;
            }
        }
        return false;
    }

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

    // Métodos CRUD
    public abstract void cadastrarVeiculo();

    public static void removerVeiculo(String placa) {
        carros.removeIf(carro -> carro.getPlaca().equals(placa));
    }

    public abstract double valorAtual();

    public static boolean placaExistente(String placa) {
        return carros.stream().anyMatch(c -> c.getPlaca().equalsIgnoreCase(placa));
    }

    public static List<Carro> pesquisarPorModelo(String modelo) {
        List<Carro> resultado = new ArrayList<>();
        for (Carro carro : carros) {
            if (carro.getModelo().equalsIgnoreCase(modelo)) {
                resultado.add(carro);
            }
        }
        return resultado;
    }

    public static List<Carro> pesquisarPorMarca(String marca) {
        List<Carro> resultado = new ArrayList<>();
        for (Carro carro : carros) {
            if (carro.getMarca().equalsIgnoreCase(marca)) {
                resultado.add(carro);
            }
        }
        return resultado;
    }

    public static Carro pesquisarPorPlaca(String placa) {
        for (Carro carro : carros) {
            if (carro.getPlaca().equals(placa)) {
                return carro;
            }
        }
        return null;
    }

    public static List<Carro> getTodosCarros() {
        return new ArrayList<>(carros);
    }
}