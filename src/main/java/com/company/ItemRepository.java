package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ItemRepository {

    private static final String[] dataURLs = {
        "./src/main/java/com/company/data/bebidas-tabuladas.txt",
        "./src/main/java/com/company/data/vinhos-tabulados.txt",
        "./src/main/java/com/company/data/pratos.csv"
    };
    private static final String[] separators = { "\t", "\t", ";" };
    private static final int[] colPrice = new int[3];
    private static final int[] colName = new int[3];

    public static void Create(int category, Item item) throws IOException {
        List<Item> items = ReadItemsFromFile(category);
        items.add(item);
        SaveItemsToFile(category, items);
    }

    public static void Delete(int category, int index) throws IOException {
        List<Item> items = ReadItemsFromFile(category);
        items.remove(index);
        SaveItemsToFile(category, items);
    }

    public static void Update(int category, int index, Item item) throws IOException {
        List<Item> items = ReadItemsFromFile(category);
        items.set(index, item);
        SaveItemsToFile(category, items);
    }

    public static List<Item> ReadItemsFromFile(int category) throws FileNotFoundException {
        File file = new File(dataURLs[category]);
        Scanner scanner = new Scanner(new FileInputStream(file));
        String[] header = scanner.nextLine().split(separators[category]);
        colPrice[category] = Arrays.asList(header).indexOf("PRECO");
        colName[category] = (colPrice[category] == 0) ? 1 : 0;
        String categoryName = header[colName[category]];

        List<Item> items = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String[] conteudo = scanner.nextLine().split(separators[category]);
            if (conteudo.length > 1) {
                items.add(new Item(
                        categoryName,
                        conteudo[colName[category]],
                        Float.parseFloat(conteudo[colPrice[category]].replace(",", "."))
                ));
            }
        }
        scanner.close();

        return items;
    }

    private static void SaveItemsToFile(int category, List<Item> items) throws IOException {
        FileWriter fileWriter = new FileWriter(dataURLs[category]);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        if(colPrice[category] == 0) {
            printWriter.print("PRECO");
            printWriter.print(separators[category]);
            printWriter.println(Main.categories[category]);
        } else {
            printWriter.print(Main.categories[category]);
            printWriter.print(separators[category]);
            printWriter.println("PRECO");
        }

        items.forEach(item -> {
            if (colPrice[category] == 0) {
                printWriter.print(String.format("%.2f", item.price));
                printWriter.print(separators[category]);
                printWriter.println(item.name);
            } else {
                printWriter.print(item.name);
                printWriter.print(separators[category]);
                printWriter.println(String.format("%.2f", item.price));
            }
        });

        printWriter.close();
    }

}
