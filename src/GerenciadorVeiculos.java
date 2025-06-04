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
        return carros.removeIf(c -> c.getPlaca().equalsIgnoreCase(placa));
    }

    // Métodos de busca
    public static Carro buscarPorPlaca(String placa) {
        return carros.stream()
                .filter(c -> c.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElse(null);
    }

    public static List<Carro> buscarPorMarca(String marca) {
        return carros.stream()
                .filter(c -> c.getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
    }

    public static List<Carro> buscarPorModelo(String modelo) {
        return carros.stream()
                .filter(c -> c.getModelo().equalsIgnoreCase(modelo))
                .collect(Collectors.toList());
    }

    public static List<Carro> filtrarPorTipo(Class<?> tipo) {
        return carros.stream()
                .filter(tipo::isInstance)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares
    public static boolean placaExistente(String placa) {
        return carros.stream().anyMatch(c -> c.getPlaca().equalsIgnoreCase(placa));
    }

    public static List<Carro> listarTodos() {
        return new ArrayList<>(carros);
    }
}