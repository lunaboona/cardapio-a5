package com.company;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner inputScanner = new Scanner(System.in);

        // get pratos
        URL pratosUrl = Main.class.getResource("pratos.csv");
        File pratosArquivo = new File(pratosUrl.getPath());
        Scanner leitorPratos = new Scanner(new FileInputStream(pratosArquivo));
        leitorPratos.nextLine();
        List<ItemMenu> listPratos = new ArrayList<ItemMenu>();
        while (leitorPratos.hasNextLine()) {
            String[] conteudoPratos = leitorPratos.nextLine().split(";");
            if (conteudoPratos.length > 1) {
                listPratos.add(new ItemMenu(
                        conteudoPratos[0],
                        Float.parseFloat(conteudoPratos[1])
                ));
            }
        }
        leitorPratos.close();

        // get bebidas
        URL bebidasUrl = Main.class.getResource("bebidas-tabuladas.txt");
        File bebidasArquivo = new File(bebidasUrl.getPath());
        Scanner leitorBebidas = new Scanner(new FileInputStream(bebidasArquivo));
        leitorBebidas.nextLine();
        List<ItemMenu> listBebidas = new ArrayList<ItemMenu>();
        while (leitorBebidas.hasNextLine()) {
            String[] conteudoBebidas = leitorBebidas.nextLine().split("\t");
            if (conteudoBebidas.length > 1) {
                listBebidas.add(new ItemMenu(
                        conteudoBebidas[1],
                        Float.parseFloat(conteudoBebidas[0].replace(",", "."))
                ));
            }
        }
        leitorBebidas.close();

        // get vinhos
        URL vinhosUrl = Main.class.getResource("vinhos-tabulados.txt");
        File vinhosArquivo = new File(vinhosUrl.getPath());
        Scanner leitorVinhos = new Scanner(new FileInputStream(vinhosArquivo));
        leitorVinhos.nextLine();
        List<ItemMenu> listVinhos = new ArrayList<ItemMenu>();
        while (leitorVinhos.hasNextLine()) {
            String[] conteudoVinhos = leitorVinhos.nextLine().split("\t");
            if (conteudoVinhos.length > 1) {
                listVinhos.add(new ItemMenu(
                        conteudoVinhos[1],
                        Float.parseFloat(conteudoVinhos[0])
                ));
            }
        }
        leitorBebidas.close();

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
        String urlOut = "./src/" + Main.class.getPackage().getName().replace(".", "/");
        FileWriter arquivoOut = new FileWriter(urlOut + "/pedido.txt");
        PrintWriter gravador = new PrintWriter(arquivoOut);

        gravador.println("Resumo do pedido");
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

        System.out.println("Pedido finalizado! Confira em \"pedido.txt\" :)");

    }
}
