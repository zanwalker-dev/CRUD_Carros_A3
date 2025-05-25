import java.util.ArrayList;
import java.util.List;

public abstract class Carro {
    protected String placa;
    protected String modelo;
    protected String marca;
    protected int ano;
    protected static List<Carro> carros = new ArrayList<>();

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    // Métodos CRUD
    public abstract void cadastrarVeiculo(String placa, String modelo, String marca, int ano);

    public static void removerVeiculo(String placa) {
        carros.removeIf(carro -> carro.getPlaca().equals(placa));
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