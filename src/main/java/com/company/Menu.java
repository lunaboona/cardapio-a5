package com.company;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class Menu {

    public static int ShowMainMenu() throws IOException, InterruptedException {
        int choice;
        int r = 0;
        do {
            System.out.println("\nBem-vindo ao restaurante");
            System.out.println("[1] - Pedidos");
            System.out.println("[2] - Bebidas");
            System.out.println("[3] - Vinhos");
            System.out.println("[4] - Pratos");
            System.out.println("[5] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Escolha um número válido!");
            } else {
                r = Main.ProcessInputChoice(choice);
            }
        } while (choice > 5 || choice < 1 || r == 1);

        return r;
    }

    public static int ShowOrderMenu() throws IOException, InterruptedException {
        int choice;
        int r = 0;
        do {
            System.out.println("\nGerenciar Pedidos\n");
            System.out.println("[1] - Criar pedido");
            System.out.println("[2] - Buscar pedido");
            System.out.println("[3] - Atualizar pedido");
            System.out.println("[4] - Apagar pedido");
            System.out.println("[5] - Voltar");
            System.out.println("[6] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 6) {
                System.out.println("Escolha um número válido!");
            } else {
                r = Main.ProcessOrderChoice(choice);
            }
        } while (choice > 6 || choice < 1 || r == 1);

        return r;
    }

    public static int ShowCategoryMenu(int category) throws IOException, InterruptedException {
        int choice;
        int r = 0;
        do {
            System.out.println("[1] - Criar");
            System.out.println("[2] - Buscar");
            System.out.println("[3] - Atualizar");
            System.out.println("[4] - Apagar");
            System.out.println("[5] - Voltar");
            System.out.println("[6] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 6) {
                System.out.println("Escolha um número válido!");
            } else {
                r = Main.ProcessCategoryChoice(choice, category);
            }
        } while (choice > 6 || choice < 1 || r == 1);

        return r;
    }

    public static int ShowItemsMenu(boolean isCreating) throws InterruptedException, IOException {
        int choice;
        int r = 0;
        do {
            System.out.println("\nEscolha uma opção");
            System.out.println("[1] - Bebidas");
            System.out.println("[2] - Vinhos");
            System.out.println("[3] - Pratos");
            System.out.println("[4] - Carrinho");
            System.out.println("[5] - Salvar");
            System.out.println("[6] - Cancelar");
            System.out.println("[7] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 7) {
                System.out.println("Escolha um número válido!");
            } else {
                r = Main.ProcessItemsChoice(choice, isCreating);
            }
        } while (choice > 7 || choice < 1 || r == 1);

        return r;

    }

    public static int ShowEditMenu () throws IOException, InterruptedException {
        int choice;
        int r = 0;
        do {
            System.out.println("[1] - Alterar itens");
            System.out.println("[2] - Observação");
            System.out.println("[3] - Salvar");
            System.out.println("[4] - Cancelar");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("Escolha um número válido!");
            } else {
                r = Main.ProcessEditChoice(choice);
            }
        } while (choice > 4 || choice < 1 || r == 1);

        return r;
    }

    public static void ListItems(List<Item> items, boolean isMainMenu) {
        if (isMainMenu) {
            System.out.println("Itens disponíveis:");
        }

        System.out.println();

        int s = items.size();
        IntStream.range(0, s).forEach(i -> {
            Item item = items.get(i);
            System.out.println("["+ (i + 1) +"] - " + item.name + "\t\t( R$ " + Main.formatter.format(item.price) + " )");
        });

        if (isMainMenu) {
            System.out.println("[" + (s + 1) + "] - Voltar");
        }
    }

}
