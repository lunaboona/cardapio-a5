package com.company;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int currentChoice;

        List<ItemMenu> listPratos = FileReader.GetListFromFile("pratos.csv", ";");
        List<ItemMenu> listBebidas = FileReader.GetListFromFile("bebidas-tabuladas.txt", "\t");
        List<ItemMenu> listVinhos = FileReader.GetListFromFile("vinhos-tabulados.txt", "\t");

        List<ItemMenu> itensEscolhidos = new ArrayList<>();

        // inicio
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

        // observação
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
        itensEscolhidos.stream().forEach(itemMenu -> {
            writer.println(itemMenu.nome + " ( R$" + itemMenu.preco + " )");
        });
        writer.println("");
        writer.println("Total: R$" + itensEscolhidos.stream().mapToDouble(itemMenu -> itemMenu.preco).sum());
        writer.println("--------------------");
        if (observacao.length() > 0) {
            writer.println("");
            writer.println("Observação: " + observacao);
        }

        writer.close();

        System.out.println("Pedido finalizado! Confira em \"." + filename +"\" :)");

    }
}
