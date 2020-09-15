package com.company;

import java.util.List;

public class Menu {

    public static ItemMenu ListItems(List<ItemMenu> items, String title) {

        System.out.println(title + "s disponíveis:");

        for (int i = 0; i < items.size(); i++) {
            System.out.println("[ " + i + " ] - " + items.get(i).name + " ( R$ " + Main.formatter.format(items.get(i).price) + " )");
        }

        System.out.println("Digite o código do item1 desejado:");

        ItemMenu itemPicked = items.get(Main.input.nextInt());

        System.out.println("Você escolheu:");
        System.out.println(itemPicked.name);

        return itemPicked;
    }
}
