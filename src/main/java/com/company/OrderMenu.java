package com.company;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class OrderMenu {

    public static void ShowOrderMenu() throws IOException, InterruptedException {
        int choice;
        do {
            System.out.println("\nPedidos");
            System.out.println("[1] - Criar");
            System.out.println("[2] - Listar");
            System.out.println("[3] - Atualizar");
            System.out.println("[4] - Apagar");
            System.out.println("[5] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Escolha um número válido!");
            } else {
                switch (choice) {
                    case 1 -> ShowOrderCreateMenu();
                    case 2 -> ShowOrderReadMenu();
                    case 3 -> ShowOrderUpdateMenu();
                    case 4 -> ShowOrderDeleteMenu();
                    case 5 -> System.exit(0);
                }
            }
        } while (choice > 5 || choice < 1);
    }

    public static void ShowOrderInteractionMenu(Order order, boolean isCreating) throws IOException, InterruptedException {
        System.out.println("\nCriar pedido");
        int choice;
        do {
            System.out.println("\nEscolha uma opção");
            System.out.println("[1] - Adicionar item");
            System.out.println("[2] - Adicionar observação");
            System.out.println("[3] - Salvar");
            System.out.println("[4] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("Escolha um número válido!");
            } else {
                switch (choice) {
                    case 1 -> ShowOrderItemsMenu(order, isCreating);
                    case 2 -> ShowOrderNoteMenu(order, isCreating);
                    case 3 -> SaveOrder(order, isCreating);
                    case 4 -> System.exit(0);
                }
            }
        } while (choice > 4 || choice < 1);
    }

    public static void ShowOrderItemsMenu(Order order, boolean isCreating) throws IOException, InterruptedException {
        int choice;
        do {
            System.out.println("\nEscolha uma opção");
            System.out.println("[1] - Bebidas");
            System.out.println("[2] - Vinhos");
            System.out.println("[3] - Pratos");
            System.out.println("[4] - Voltar");
            System.out.println("[5] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("Escolha um número válido!");
            } else {
                switch (choice) {
                    case 4 -> ShowOrderInteractionMenu(order, isCreating);
                    case 5 -> System.exit(0);
                    default -> ListOrderItems(order, choice - 1, isCreating);
                }
            }
        } while (choice > 4 || choice < 1);
    }

    public static void ShowOrderCreateMenu() throws IOException, InterruptedException {
        ShowOrderInteractionMenu(new Order(OrderRepository.GetAvailableID()), true);
    }

    private static void ShowOrderUpdateMenu() throws IOException, InterruptedException {
        System.out.println("\nAtualizar pedido");
        System.out.print("Informe o ID: ");
        int id = Main.input.nextInt();
        Order order = OrderRepository.GetById(id);
        if (order != null) {
            ShowOrderInteractionMenu(order, false);
        } else {
            System.out.println("Erro ao atualizar");
        }
    }

    public static void ListOrderItems(Order order, int category, boolean isCreating) throws IOException, InterruptedException {
        Main.listItems.set(category, ItemRepository.ReadItemsFromFile(category));
        List<Item> items = Main.listItems.get(category);
        ItemMenu.ListItems(items, false);

        System.out.println("Digite o código do item desejado: ");
        Item item = items.get(Main.input.nextInt() - 1);
        order.AddItem(item);

        ShowOrderItemsMenu(order, isCreating);
    }

    public static void ShowOrderNoteMenu(Order order, boolean isCreating) throws IOException, InterruptedException {
        System.out.println("Nova observação: ");
        Main.input.nextLine();
        order.SetNote(Main.input.nextLine());
        ShowOrderInteractionMenu(order, isCreating);
    }

    public static void ShowOrderReadMenu() throws IOException {
        List<Order> orders = OrderRepository.ReadOrdersFromJSON();

        int listSize = orders.size();
        if (listSize > 0) {
            IntStream.range(0, listSize).forEach(index -> {
                Order order = orders.get(index);
                PrintOrder(order, "Pedido " + order.GetId(), false, false);
            });
        } else {
            System.out.println("Não há nenhum pedido\n");
        }
    }

    public static void ShowOrderDeleteMenu() throws IOException {
        System.out.println("\nApagar pedido");
        System.out.print("Informe o ID: ");
        int id = Main.input.nextInt();
        if (OrderRepository.Delete(id)) {
            System.out.println("Sucesso");
        } else {
            System.out.println("Erro ao apagar");
        }
    }

    public static void PrintOrder(Order order, String title, boolean showNote, boolean showIndex) {
        System.out.println(title + " | Preço total: R$ " + Main.formatter.format(order.GetTotal()));
        IntStream.range(0, order.GetItems().size()).forEach(index -> {
            String indice = showIndex ? "["+(index + 1)+"] - " : "";
            Item item = order.GetItems().get(index);
            System.out.println("\t"+ indice + item.category + " " + item.name + " ( R$ "+ Main.formatter.format(item.price) +" )");
        });
        if (showNote && order.GetNote() != null) {
            System.out.println("\tObservação: " + (order.GetNote()) );
        }
        System.out.println();
    }

    public static void SaveOrder(Order order, boolean isCreating) throws IOException {
        OrderMenu.PrintOrder(order, "Pedido atual", true, false);

        if (!order.IsValid()) {
            System.out.println("Erro: O pedido atual está vazio");
            System.exit(0);
        }

        if (isCreating ? OrderRepository.Create(order) : OrderRepository.Update(order)) {
            System.out.println("Sucesso");
        } else {
            System.out.println("Ocorreu um erro ao salvar o pedido");
        }
    }
}
