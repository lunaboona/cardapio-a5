package com.company;

import java.io.IOException;

public class Menu {

    public static void ShowMainMenu() throws IOException, InterruptedException {
        int choice;
        do {
            System.out.println("\nBem-vindo ao restaurante");
            System.out.println("[1] - Pedidos");
            System.out.println("[2] - Itens");
            System.out.println("[3] - Sair");

            choice = Main.input.nextInt();

            if (choice < 1 || choice > 3) {
                System.out.println("Escolha um número válido!");
            } else {
                switch (choice) {
                    case 1 -> OrderMenu.ShowOrderMenu();
                    case 2 -> ItemMenu.ShowItemCategoriesMenu();
                    case 3 -> System.exit(0);
                }
            }
        } while (choice > 3 || choice < 1);
    }

}
