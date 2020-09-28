package com.company;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class ItemMenu {

    public static void ShowItemCategoriesMenu() throws IOException {
        int choice;
        do {
            System.out.println("\nItens");
            System.out.println("[1] - Bebidas");
            System.out.println("[2] - Vinhos");
            System.out.println("[3] - Pratos");
            System.out.println("[4] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("Escolha um número válido!");
            } else {
                switch (choice) {
                    case 4 -> System.exit(0);
                    default -> ShowItemMenu(choice -1);
                }
            }
        } while (choice > 4 || choice < 1);
    }

    public static void ShowItemMenu(int category) throws IOException {
        int choice;
        do {
            System.out.println("\nItens");
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
                    case 1 -> ShowItemCreateMenu(category);
                    case 2 -> ShowItemReadMenu(category);
                    case 3 -> ShowItemUpdateMenu(category);
                    case 4 -> ShowItemDeleteMenu(category);
                    case 5 -> System.exit(0);
                }
            }
        } while (choice > 5 || choice < 1);
    }

    public static void ShowItemCreateMenu(int category) throws IOException {
        System.out.println("\nCriar item");

        System.out.print("Nome: ");
        Main.input.nextLine();
        String name = Main.input.nextLine();

        System.out.print("Preço: ");
        Float price = Main.input.nextFloat();

        Item newItem = new Item(Main.categories[category], name, price);
        ItemRepository.Create(category, newItem);

        System.out.println("Criado com sucesso");
    }

    public static void ShowItemReadMenu(int category) throws IOException {
        Main.listItems.set(category, ItemRepository.ReadItemsFromFile(category));
        List<Item> items = Main.listItems.get(category);
        ListItems(items, false);
    }

    public static void ShowItemUpdateMenu(int category) throws IOException {
        System.out.println("\nEditar item");

        String categoryName = Main.categories[category];
        Main.listItems.set(category, ItemRepository.ReadItemsFromFile(category));
        List<Item> items = Main.listItems.get(category);
        ListItems(items, false);

        int i;
        do {
            System.out.println("\nInsira o índice do item que deseja editar: ");
            i = Main.input.nextInt();
            if (i < 1 || i > items.size()) {
                System.out.println("Escolha um número válido!");
            }
        } while (i < 1 || i > items.size());

        System.out.println("Nome: ");
        Main.input.nextLine();
        String name = Main.input.nextLine();

        System.out.println("Preco: ");
        Float price = Main.input.nextFloat();

        Item updatedItem = new Item(categoryName, name, price);
        ItemRepository.Update(category, i - 1, updatedItem);
        System.out.println("Sucesso");
    }

    public static void ShowItemDeleteMenu(int category) throws IOException {
        System.out.println("\nApagar");

        Main.listItems.set(category, ItemRepository.ReadItemsFromFile(category));
        List<Item> items = Main.listItems.get(category);
        ListItems(items, false);

        int i;
        do {
            System.out.println("\nInsira o índice do item que deseja deletar: ");
            i = Main.input.nextInt();
            if (i < 1 || i > items.size()) {
                System.out.println("Escolha um número válido");
            }
        } while (i < 1 || i > items.size());
        ItemRepository.Delete(category, i - 1);
        System.out.println("Sucesso");
    }

    public static void ListItems(List<Item> items, boolean isMainMenu) {
        if (isMainMenu) {
            System.out.println("Itens disponíveis:");
        }

        System.out.println();

        int s = items.size();
        IntStream.range(0, s).forEach(i -> {
            Item item = items.get(i);
            System.out.println("["+ (i + 1) +"] - " + item.name + " ( R$ " + Main.formatter.format(item.price) + " )");
        });

        if (isMainMenu) {
            System.out.println("[" + (s + 1) + "] - Voltar");
        }
    }
}
