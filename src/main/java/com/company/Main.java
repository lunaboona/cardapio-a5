package com.company;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static final Scanner input = new Scanner(System.in);
    public static final NumberFormat formatter = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public static final String[] categories = { "BEBIDA", "VINHO", "PRATO"};

    private static final List<List<Item>> listItems = new ArrayList<>();
    private static Order currentOrder;

    public static void main(String[] args) throws IOException, InterruptedException {
        listItems.add(ItemRepository.ReadItemsFromFile(0));
        listItems.add(ItemRepository.ReadItemsFromFile(1));
        listItems.add(ItemRepository.ReadItemsFromFile(2));

        int choice;
        do {
            currentOrder = new Order(OrderRepository.GetAvailableID());
            choice = Menu.ShowMainMenu();
        } while (choice == 2);
    }

    public static int ProcessInputChoice(int choice) throws IOException, InterruptedException {
        int r = 0;
        switch (choice) {
            case 1 -> r = Menu.ShowOrderMenu();
            case 5 -> {
                System.exit(0);
            }
            default -> r = Menu.ShowCategoryMenu(choice);
        }

        return r;
    }

    public static int ProcessOrderChoice(int choice) throws IOException, InterruptedException {
        int r = 0;
        switch (choice) {
            case 1 -> {
                System.out.println("\nCriar pedido");
                r = Menu.ShowItemsMenu(true);
            }
            case 2 -> {
                List<Order> orders = OrderRepository.ReadOrdersFromJSON();

                System.out.println("\nBuscar pedido");
                int listSize = orders.size();
                if (listSize > 0) {
                    IntStream.range(0, listSize).forEach(index -> {
                        Order order = orders.get(index);
                        PrintOrder(order, "Pedido " + order.GetId(), false, false);
                    });
                } else {
                    System.out.println("Não há nenhum pedido\n");
                }

                r = 1;

            }
            case 3 -> {
                System.out.println("\nAtualizar pedido");
                System.out.print("Informe o ID: ");
                int id = input.nextInt();
                Order order = OrderRepository.GetById(id);
                if (order != null) {
                    currentOrder = order;
                    r = Menu.ShowEditMenu();
                } else {
                    System.out.println("Erro ao atualizar");
                    r = 1;
                }
            }
            case 4 -> {
                System.out.println("\nApagar pedido");
                System.out.print("Informe o ID: ");
                int id = input.nextInt();
                if (OrderRepository.Delete(id)) {
                    System.out.println("Sucesso");
                } else {
                    System.out.println("Erro ao apagar");
                }
                r = 1;
            }
            case 5 -> {
                r = 2;
            }
            case 6 -> {
                System.exit(0);
            }
        }

        return r;
    }

    public static int ProcessEditChoice(int choice) throws IOException, InterruptedException {
        int r = 0;
        switch (choice) {
            case 1 -> r = Menu.ShowItemsMenu(false);
            case 2 -> {
                System.out.println("Observação: "+ currentOrder.GetNote());
                System.out.println("Alterar observação? [S/N]");
                input.nextLine();

                String choiceEditNote = input.nextLine();

                if (choiceEditNote.toLowerCase().equals("s")) {
                    System.out.println("Nova observação: ");
                    currentOrder.SetNote(input.nextLine());
                    System.out.println("Sucesso");
                }
                r = 1;
            }
            case 3 -> {
                if (!currentOrder.IsValid()) {
                    System.out.println("O pedido atual está vazio");
                    r = 1;
                } else {
                    r = SaveOrder(false);
                }
            }
            case 4 -> {
                r = 2;
            }
        }
        return r;
    }

    public static int ProcessItemsChoice(int choice, boolean isCreating) throws InterruptedException, IOException {
        int r = 0;
        switch (choice) {
            case 4 -> {
                do {
                    System.out.println("\nCarrinho");
                    PrintOrder(currentOrder, "Pedido atual", true, true);
                    if (currentOrder.GetItems().size() > 0) {
                        System.out.println("[1] - Continuar");
                        System.out.println("[2] - Remover itens");
                        int choiceCart;
                        do {
                            choiceCart = Main.input.nextInt();
                            if (choiceCart < 1 || choiceCart > 2) {
                                System.out.println("Escolha um número válido!");
                            }
                        } while (choiceCart < 1 || choiceCart > 2);

                        if (choiceCart == 1) {
                            r = 1;
                        } else {
                            int choiceRemove;
                            do {
                                System.out.print("Insira o número do produto para remover: ");
                                choiceRemove = input.nextInt();

                                if (choiceRemove < 1 || choiceRemove > currentOrder.GetItems().size()) {
                                    System.out.println("Escolha um número válido!");
                                }
                            } while (choiceRemove < 1 || choiceRemove > currentOrder.GetItems().size());

                            String item = currentOrder.GetItems().get(choiceRemove - 1).name;
                            currentOrder.RemoveItem(choiceRemove - 1);
                            System.out.println("Removido");
                            r = -1;
                        }
                    } else {
                        r = 1;
                    }
                } while (r == -1);
            }
            case 5 -> {
                if (!currentOrder.IsValid()) {
                    System.out.println("Pedido atual está vazio");
                    r = 1;
                } else {
                    r = SaveOrder(isCreating);
                }
            }
            case 6 -> {
                r = 2;
                System.out.println("Pedido cancelado");
            }
            case 7 -> {
                System.exit(0);
            }
            default -> {
                Object currentItem;
                do {
                    currentItem = ChooseItem(choice - 1);
                    if (currentItem != null) {
                        currentOrder.AddItem((Item) currentItem);
                    }
                } while (currentItem != null);
                r = 1;
            }
        }

        return r;
    }

    public static int ProcessCategoryChoice(int choice, int category) throws IOException, InterruptedException {
        int r = 0;
        String categoryName = categories[category];
        listItems.set(category, ItemRepository.ReadItemsFromFile(category));
        List<Item> items = listItems.get(category);
        switch(choice) {
            case 1 -> {
                System.out.println("\nCriar");

                System.out.print("Insira o nome: ");
                input.nextLine();
                String name = input.nextLine();

                System.out.print("Insira o preço: ");
                Float price = input.nextFloat();

                Item newItem = new Item(categoryName, name, price);
                ItemRepository.Create(category, newItem);

                System.out.println("Sucesso");

                r = 1;
            }
            case 2 -> {
                Menu.ListItems(items, false);
                r = 1;
            }
            case 3 -> {
                System.out.println("\nEditar");
                Menu.ListItems(items, false);

                int i;
                do {
                    System.out.println("\nInsira o índice do item que deseja editar: ");
                    i = input.nextInt();
                    if (i < 1 || i > items.size()) {
                        System.out.println("Escolha um número válido!");
                    }
                } while (i < 1 || i > items.size());

                System.out.println("Insira o novo nome do item: ");
                input.nextLine();
                String name = input.nextLine();

                System.out.println("Insira o novo preço do item: ");
                Float price = input.nextFloat();

                Item updatedItem = new Item(categoryName, name, price);
                ItemRepository.Update(category, i - 1, updatedItem);
                System.out.println("Sucesso");

                r = 1;
            }
            case 4 -> {
                System.out.println("\nApagar");
                Menu.ListItems(items, false);

                int i;
                do {
                    System.out.println("\nInsira o índice do item que deseja deletar: ");
                    i = input.nextInt();
                    if (i < 1 || i > items.size()) {
                        System.out.println("Escolha um número válido");
                    }
                } while (i < 1 || i > items.size());
                ItemRepository.Delete(category, i - 1);
                System.out.println("Sucesso");

                r = 1;
            }
            case 5 -> {
                r = 2;
            }
            case 6 -> {
                System.exit(0);
            }
        }

        return r;
    }

    private static int SaveOrder(boolean isCreating) throws IOException {
        PrintOrder(currentOrder, "Pedido atual", true, false);

        System.out.println("[1] - Continuar comprando");
        System.out.println("[2] - Observação");
        System.out.println("[3] - Salvar");

        int choice;
        int r = 0;

        do {
            choice = input.nextInt();
            if (choice < 1 || choice > 3) {
                System.out.println("Escolha um número válido!");
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1 -> r = 1;
            case 2 -> {
                System.out.println("Observação: ");
                input.nextLine();

                currentOrder.SetNote(input.nextLine());
                r = SaveOrder(isCreating);
            }
            case 3 -> {
                if (isCreating ? OrderRepository.Create(currentOrder) : OrderRepository.Update(currentOrder)) {
                    System.out.println("Sucesso");
                } else {
                    System.out.println("Ocorreu um erro ao salvar o pedido");
                }


                System.out.println("Deseja voltar ao início? [S/N]");
                input.nextLine();

                String choiceStart = input.nextLine();

                if (choiceStart.toLowerCase().equals("s")) {
                    r = 2;
                } else {
                    r = 0;
                }
            }
        }

        return r;
    }

    private static Object ChooseItem (int category) throws FileNotFoundException {
        int choice;
        listItems.set(category, ItemRepository.ReadItemsFromFile(category));
        List<Item> currentList = listItems.get(category);
        do {
            Menu.ListItems(currentList, true);
            choice = Main.input.nextInt();

            if (choice < 1 || choice > currentList.size() + 1) {
                System.out.println("Escolha um número válido!");
            }
        } while (choice < 1 || choice > currentList.size() + 1);

        if (choice == (currentList.size() + 1)) {
            return null;
        } else {
            System.out.println("Sucesso");
            return currentList.get(choice - 1);
        }
    }

    private static void PrintOrder(Order order, String title, boolean showNote, boolean showIndex) {
        System.out.println(title + " | Preço total: R$ " + formatter.format(order.GetTotal()));
        IntStream.range(0, order.GetItems().size()).forEach(index -> {
            String indice = showIndex ? "["+(index + 1)+"] - " : "";
            Item item = order.GetItems().get(index);
            System.out.println("\t"+ indice + item.category + " " + item.name + "\t ( R$ "+ formatter.format(item.price) +" )");
        });
        if (showNote) {
            System.out.println("\tObservação: " + (order.GetNote() != null ? order.GetNote() : "---") );
        }
        System.out.println();
    }
}
