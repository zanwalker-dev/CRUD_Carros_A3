import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n### MENU PRINCIPAL ###");
            System.out.println("1. Cadastrar carro para venda");
            System.out.println("2. Pesquisar carro");
            System.out.println("3. Listar todos os carros");
            System.out.println("4. Alterar dados de um carro");
            System.out.println("5. Remover um carro");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCarro();
                    break;
                case 2:
                    pesquisarCarro();
                    break;
                case 3:
                    listarTodosCarros();
                    break;
                case 4:
                    alterarCarro();
                    break;
                case 5:
                    removerCarro();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarCarro() {
        System.out.println("\n### CADASTRAR CARRO ###");
        System.out.println("Selecione o tipo:");
        System.out.println("1. Hatch");
        System.out.println("2. Sedan");
        System.out.println("3. SUV");
        System.out.print("Opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        String placa;
        do {
            System.out.print("Placa: ");
            placa = scanner.nextLine().toUpperCase();

            if (GerenciadorVeiculos.placaExistente(placa)) {
                System.out.println("Erro: Placa já cadastrada no sistema!");
                System.out.println("Deseja tentar novamente? (S/N)");
                String resposta = scanner.nextLine();
                if (!resposta.equalsIgnoreCase("S")) {
                    return;
                }
            }
        } while (GerenciadorVeiculos.placaExistente(placa));

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Quilometragem atual: ");
        int quilometragem = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (tipo) {
                case 1:
                    System.out.print("É compacto? (true/false): ");
                    boolean isCompact = scanner.nextBoolean();
                    Hatch hatch = new Hatch(placa, modelo, marca, ano, quilometragem, preco, isCompact);
                    hatch.cadastrarVeiculo();
                    break;
                case 2:
                    System.out.print("Espaço do porta-mala (litros): ");
                    double espacoPortaMala = scanner.nextDouble();
                    Sedan sedan = new Sedan(placa, modelo, marca, ano, quilometragem, preco, espacoPortaMala);
                    sedan.cadastrarVeiculo();
                    break;
                case 3:
                    System.out.print("Tração (4x4 ou 2x4): ");
                    String tracao = scanner.nextLine();
                    SUV suv = new SUV(placa, modelo, marca, ano, quilometragem, preco, tracao);
                    suv.cadastrarVeiculo();
                    break;
                default:
                    System.out.println("Tipo inválido!");
                    return;
            }
            System.out.println("Carro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void pesquisarCarro() {
        System.out.println("\n### PESQUISAR CARRO ###");
        System.out.println("1. Pesquisar por placa");
        System.out.println("2. Listar por categoria");
        System.out.println("3. Listar por modelo");
        System.out.println("4. Listar por marca");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.print("Digite a placa: ");
                String placa = scanner.nextLine();
                Carro carro = GerenciadorVeiculos.buscarPorPlaca(placa);
                if (carro != null) {
                    exibirDetalhesCarro(carro);
                } else {
                    System.out.println("Carro não encontrado!");
                }
                break;
            case 2:
                System.out.println("1. Hatch");
                System.out.println("2. Sedan");
                System.out.println("3. SUV");
                System.out.print("Escolha a categoria: ");
                int categoria = scanner.nextInt();
                scanner.nextLine();

                List<? extends Carro> lista;
                switch (categoria) {
                    case 1:
                        lista = GerenciadorVeiculos.listarHatchs();
                        break;
                    case 2:
                        lista = GerenciadorVeiculos.listarSedans();
                        break;
                    case 3:
                        lista = GerenciadorVeiculos.listarSUVs();
                        break;
                    default:
                        System.out.println("Categoria inválida!");
                        return;
                }
                listarCarros(lista);
                break;
            case 3:
                System.out.print("Digite o modelo: ");
                String modelo = scanner.nextLine();
                listarCarros(GerenciadorVeiculos.buscarPorModelo(modelo));
                break;
            case 4:
                System.out.print("Digite a marca: ");
                String marca = scanner.nextLine();
                listarCarros(GerenciadorVeiculos.buscarPorMarca(marca));
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void listarTodosCarros() {
        System.out.println("\n### TODOS OS CARROS ###");
        listarCarros(GerenciadorVeiculos.listarTodos());
    }

    private static void alterarCarro() {
        System.out.println("\n### ALTERAR CARRO ###");
        System.out.print("Digite a placa do carro: ");
        String placa = scanner.nextLine();

        Carro carro = GerenciadorVeiculos.buscarPorPlaca(placa);
        if (carro == null) {
            System.out.println("Carro não encontrado!");
            return;
        }

        System.out.println("\nSelecione o que deseja alterar:");
        System.out.println("1. Modelo");
        System.out.println("2. Marca");
        System.out.println("3. Ano");
        System.out.println("4. Preço");

        if (carro instanceof Hatch) {
            System.out.println("5. Tipo (compacto)");
        } else if (carro instanceof Sedan) {
            System.out.println("5. Espaço do porta-mala");
        } else if (carro instanceof SUV) {
            System.out.println("5. Tração");
        }

        System.out.println("6. Atualizar quilometragem");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (opcao) {
                case 1:
                    System.out.print("Novo modelo: ");
                    carro.setModelo(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Nova marca: ");
                    carro.setMarca(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Novo ano: ");
                    carro.setAno(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.print("Novo preço: ");
                    if (carro instanceof Hatch) {
                        ((Hatch) carro).setPreco(scanner.nextDouble());
                    } else if (carro instanceof Sedan) {
                        ((Sedan) carro).setPreco(scanner.nextDouble());
                    } else if (carro instanceof SUV) {
                        ((SUV) carro).setPreco(scanner.nextDouble());
                    }
                    scanner.nextLine();
                    break;
                case 5:
                    if (carro instanceof Hatch) {
                        System.out.print("É compacto? (true/false): ");
                        ((Hatch) carro).setCompact(scanner.nextBoolean());
                    } else if (carro instanceof Sedan) {
                        System.out.print("Novo espaço do porta-mala: ");
                        ((Sedan) carro).setEspacoPortaMala(scanner.nextDouble());
                    } else if (carro instanceof SUV) {
                        System.out.print("Nova tração (4x4 ou 2x4): ");
                        ((SUV) carro).setTracao(scanner.nextLine());
                    }
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.print("Nova quilometragem: ");
                    carro.setQuilometragem(scanner.nextInt());
                    scanner.nextLine();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    return;
            }
            System.out.println("Carro atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    private static void removerCarro() {
        System.out.println("\n### REMOVER CARRO ###");
        System.out.print("Digite a placa do carro: ");
        String placa = scanner.nextLine();

        if (GerenciadorVeiculos.remover(placa)) {
            System.out.println("Carro removido com sucesso!");
        } else {
            System.out.println("Carro não encontrado!");
        }
    }

    private static void listarCarros(List<? extends Carro> carros) {
        if (carros.isEmpty()) {
            System.out.println("Nenhum carro encontrado!");
            return;
        }

        for (Carro carro : carros) {
            exibirDetalhesCarro(carro);
            System.out.println("-------------------");
        }
    }

    private static void exibirDetalhesCarro(Carro carro) {
        System.out.println("\nPlaca: " + carro.getPlaca());
        System.out.println("Modelo: " + carro.getModelo());
        System.out.println("Marca: " + carro.getMarca());
        System.out.println("Ano: " + carro.getAno());
        System.out.println("Quilometragem: " + carro.getQuilometragem() + " km");

        if (carro instanceof Hatch) {
            Hatch hatch = (Hatch) carro;
            System.out.println("Tipo: Hatch");
            System.out.printf("Preço: R$%.2f\n", hatch.getPreco());
            System.out.println("Compacto: " + hatch.isCompact());
        } else if (carro instanceof Sedan) {
            Sedan sedan = (Sedan) carro;
            System.out.println("Tipo: Sedan");
            System.out.printf("Preço: R$%.2f\n", sedan.getPreco());
            System.out.println("Porta-mala: " + sedan.getEspacoPortaMala() + " litros");
        } else if (carro instanceof SUV) {
            SUV suv = (SUV) carro;
            System.out.println("Tipo: SUV");
            System.out.printf("Preço: R$%.2f\n", suv.getPreco());
            System.out.println("Tração: " + suv.getTracao());
        }

        System.out.printf("Valor atual: R$%.2f\n", carro.valorAtual());
    }
}