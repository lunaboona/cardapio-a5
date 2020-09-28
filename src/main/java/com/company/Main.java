package com.company;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

public class Main {

    public static final Scanner input = new Scanner(System.in);
    public static final NumberFormat formatter = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public static final String[] categories = { "BEBIDA", "VINHO", "PRATO"};

    public static final List<List<Item>> listItems = new ArrayList<>();
    public static Order currentOrder;

    public static void main(String[] args) throws IOException, InterruptedException {
        listItems.add(ItemRepository.ReadItemsFromFile(0));
        listItems.add(ItemRepository.ReadItemsFromFile(1));
        listItems.add(ItemRepository.ReadItemsFromFile(2));
        currentOrder = new Order(OrderRepository.GetAvailableID());
        Menu.ShowMainMenu();
    }

}
