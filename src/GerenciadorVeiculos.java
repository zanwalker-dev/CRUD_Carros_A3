import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorVeiculos {
    private static List<Carro> carros = new ArrayList<>();

    // Métodos CRUD básicos
    public static void adicionar(Carro carro) {
        if (placaExistente(carro.getPlaca())) {
            throw new IllegalArgumentException("Placa já cadastrada: " + carro.getPlaca());
        }
        carros.add(carro);
    }

    public static boolean remover(String placa) {
        return carros.removeIf(carro -> carro.getPlaca().equalsIgnoreCase(placa));
    }

    // Métodos de busca
    public static Carro buscarPorPlaca(String placa) {
        return carros.stream()
                .filter(carro -> carro.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElse(null);
    }

    public static List<Carro> buscarPorMarca(String marca) {
        return carros.stream()
                .filter(carro -> carro.getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
    }

    public static List<Carro> buscarPorModelo(String modelo) {
        return carros.stream()
                .filter(carro -> carro.getModelo().equalsIgnoreCase(modelo))
                .collect(Collectors.toList());
    }

    // Métodos específicos por tipo
    public static List<Hatch> listarHatchs() {
        return carros.stream()
                .filter(carro -> carro instanceof Hatch)
                .map(carro -> (Hatch) carro)
                .collect(Collectors.toList());
    }

    public static List<Sedan> listarSedans() {
        return carros.stream()
                .filter(carro -> carro instanceof Sedan)
                .map(carro -> (Sedan) carro)
                .collect(Collectors.toList());
    }

    public static List<SUV> listarSUVs() {
        return carros.stream()
                .filter(carro -> carro instanceof SUV)
                .map(carro -> (SUV) carro)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares
    public static boolean placaExistente(String placa) {
        return carros.stream().anyMatch(carro -> carro.getPlaca().equalsIgnoreCase(placa));
    }

    public static List<Carro> listarTodos() {
        return new ArrayList<>(carros);
    }
}