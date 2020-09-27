package main.java.com.company;

import java.util.List;

public class Menu {

    public static Item ListItems(List<Item> items, String title) {

        System.out.println(title + "s disponíveis:");

        for (int i = 0; i < items.size(); i++) {
            System.out.println("[ " + i + " ] - " + items.get(i).name + " ( R$ " + Main.formatter.format(items.get(i).price) + " )");
        }

        System.out.println("Digite o código do item1 desejado:");

        Item itemPicked = items.get(Main.input.nextInt());

        System.out.println("Você escolheu:");
        System.out.println(itemPicked.name);

        return itemPicked;
    }
}
