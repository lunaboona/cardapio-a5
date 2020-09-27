package main.java.com.company;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

public class Main {

    public static final Scanner input = new Scanner(System.in);
    public static final NumberFormat formatter = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public static void main(String[] args) throws IOException {

        int currentChoice;

        // TODO read categories dynamically from JSON config file
        // init lists
        List<Item> listPratos = FileReader.GetListFromFile("pratos.csv", ";");
        List<Item> listBebidas = FileReader.GetListFromFile("bebidas-tabuladas.txt", "\t");
        List<Item> listVinhos = FileReader.GetListFromFile("vinhos-tabulados.txt", "\t");

        List<Item> itensEscolhidos = new ArrayList<>();

        // display menu
        System.out.println("Bem-vindo ao restaurante!");
        System.out.println("");

        do {
            System.out.println("Escolha uma opção para incluir ao seu pedido:");
            System.out.println("[ 1 ] Pratos");
            System.out.println("[ 2 ] Bebidas");
            System.out.println("[ 3 ] Vinhos");
            System.out.println("[ 0 ] Finalizar");
            currentChoice = input.nextInt();

            switch (currentChoice) {
                case (1): { itensEscolhidos.add(Menu.ListItems(listPratos, "Prato")); break; }
                case (2): { itensEscolhidos.add(Menu.ListItems(listBebidas, "Bebida")); break; }
                case (3): { itensEscolhidos.add(Menu.ListItems(listVinhos, "Vinho")); break; }
                default: { }
            }
        } while (currentChoice > 0);

        if (itensEscolhidos.size() == 0) {
            input.close();
            System.out.println("Nenhum item escolhido, finalizando pedido.");
            return;
        }

        // add note
        System.out.println("Deseja incluir uma observação?:");
        input.nextLine();
        String observacao = input.nextLine();
        input.close();

        // out
        String filename = "/pedidos/pedido_" + DateUtil.NowFlat() + ".txt";
        String urlOut = "./src/" + Main.class.getPackage().getName().replace(".", "/");
        FileWriter fileOut = new FileWriter(urlOut + filename);
        PrintWriter writer = new PrintWriter(fileOut);


        writer.println("Resumo do pedido em " + DateUtil.NowBrazil());
        writer.println("");
        writer.println("--------------------");
        writer.println("Item (preço)");
        writer.println("");
        itensEscolhidos.stream().forEach(item -> {
            writer.println(item.name + " ( R$" + formatter.format(item.price) + " )");
        });
        writer.println("");
        writer.println("Total: R$" + formatter.format(itensEscolhidos.stream().mapToDouble(item -> item.price).sum()));
        writer.println("--------------------");
        if (observacao.length() > 0) {
            writer.println("");
            writer.println("Observação: " + observacao);
        }

        writer.close();

        System.out.println("Pedido finalizado! Confira em \"." + filename +"\" :)");

    }
}
