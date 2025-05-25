import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Cadastrando veículos
        Hatch hatch1 = new Hatch();
        hatch1.cadastrarVeiculo("ABC1234", "Gol", "Volkswagen", 2020);
        hatch1.setPreco(45000);
        hatch1.setCompact(true);

        Sedan sedan1 = new Sedan();
        sedan1.cadastrarVeiculo("DEF5678", "Civic", "Honda", 2019);
        sedan1.setPreco(90000);
        sedan1.setEspacoPortaMala(450);

        SUV suv1 = new SUV();
        suv1.cadastrarVeiculo("GHI9012", "Tiguan", "Volkswagen", 2021);
        suv1.setPreco(150000);
        suv1.setTracao("4x4");

        // Pesquisas
        System.out.println("Todos os carros:");
        List<Carro> todosCarros = Carro.getTodosCarros();
        todosCarros.forEach(carro -> System.out.println(carro.getModelo()));

        System.out.println("\nCarros da Volkswagen:");
        List<Carro> carrosVW = Carro.pesquisarPorMarca("Volkswagen");
        carrosVW.forEach(carro -> System.out.println(carro.getModelo()));

        // Valor atualizado
        System.out.println("\nValor atualizado do Hatch:");
        System.out.println("R$ " + hatch1.valorAno());

        // Removendo veículo
        Carro.removerVeiculo("DEF5678");
        System.out.println("\nCarros após remoção:");
        Carro.getTodosCarros().forEach(carro -> System.out.println(carro.getModelo()));
    }
}