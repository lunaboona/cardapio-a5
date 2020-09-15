package com.company;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner inputScanner = new Scanner(System.in);

        List<ItemMenu> listPratos = FileReader.GetListFromFile("pratos.csv", ";");
        List<ItemMenu> listBebidas = FileReader.GetListFromFile("bebidas-tabuladas.txt", "\t");
        List<ItemMenu> listVinhos = FileReader.GetListFromFile("vinhos-tabulados.txt", "\t");

        List<ItemMenu> itensEscolhidos = new ArrayList<>();

        // inicio
        System.out.println("Bem-vindo ao restaurante!");

        ItemMenu pratoEscolhido = Menu.ListItems(listPratos, "Prato");
        ItemMenu bebidaEscolhida = Menu.ListItems(listBebidas, "Bebida");
        ItemMenu vinhoEscolhido = Menu.ListItems(listVinhos, "Vinho");

        // observação
        System.out.println("Deseja incluir uma observação?:");
        inputScanner.nextLine();
        String observacao = inputScanner.nextLine();

        // out
        String filename = "/pedidos/pedido_" + DateUtil.NowFlat() + ".txt";

        String urlOut = "./src/" + Main.class.getPackage().getName().replace(".", "/");
        FileWriter arquivoOut = new FileWriter(urlOut + filename);
        PrintWriter gravador = new PrintWriter(arquivoOut);

        gravador.println("Resumo do pedido em " + DateUtil.NowBrazil());
        gravador.println("");
        gravador.println("Prato: " + pratoEscolhido.nome + " ( R$" + pratoEscolhido.preco + " )");
        gravador.println("Bebida: " + bebidaEscolhida.nome + " ( R$" + bebidaEscolhida.preco + " )");
        gravador.println("Vinho: " + vinhoEscolhido.nome + " ( R$" + vinhoEscolhido.preco + " )");
        gravador.println("");
        gravador.println("Total: R$" + (pratoEscolhido.preco + bebidaEscolhida.preco + vinhoEscolhido.preco));
        if (observacao.length() > 0) {
            gravador.println("");
            gravador.println("Observação: " + observacao);
        }

        gravador.close();

        System.out.println("Pedido finalizado! Confira em \"." + filename +"\" :)");

    }
}
