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
        
        // inicio
        System.out.println("Bem-vindo ao restaurante!");

        // pratos
        System.out.println("Pratos disponíveis:");

        for (int i = 0; i < listPratos.size(); i++) {
            System.out.println("[ " + i + " ] - " + listPratos.get(i).nome + " ( R$ " + listPratos.get(i).preco + " )");
        }

        System.out.println("Favor escolher um prato (digite o código):");

        ItemMenu pratoEscolhido = listPratos.get(inputScanner.nextInt());

        System.out.println("Você escolheu:");
        System.out.println(pratoEscolhido.nome);

        // bebidas
        System.out.println("Bebidas disponíveis:");

        for (int i = 0; i < listBebidas.size(); i++) {
            System.out.println("[ " + i + " ] - " + listBebidas.get(i).nome + " ( R$ " + listBebidas.get(i).preco + " )");
        }

        System.out.println("Favor escolher uma bebida (digite o código):");

        ItemMenu bebidaEscolhida = listBebidas.get(inputScanner.nextInt());

        System.out.println("Você escolheu:");
        System.out.println(bebidaEscolhida.nome);

        // vinhos
        System.out.println("Vinhos disponíveis:");

        for (int i = 0; i < listVinhos.size(); i++) {
            System.out.println("[ " + i + " ] - " + listVinhos.get(i).nome + " ( R$ " + listVinhos.get(i).preco + " )");
        }

        System.out.println("Favor escolher um vinho (digite o código):");

        ItemMenu vinhoEscolhido = listVinhos.get(inputScanner.nextInt());

        System.out.println("Você escolheu:");
        System.out.println(vinhoEscolhido.nome);

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
