package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderRepository {
    public static final String jsonPath = "./src/main/java/com/company/data/orders.json";

    public static List<Order> ReadOrdersFromJSON() throws IOException {
        Gson gson = new Gson();
        File file = new File(jsonPath);
        List<Order> orders;

        if (file.exists()) {
            Reader reader = new FileReader(file);
            OrderJSONHelper helper = gson.fromJson(reader, OrderJSONHelper.class);
            orders = helper.orders;
            reader.close();
        } else {
            orders = new ArrayList<>();
        }

        return orders;
    }

    public static void SaveOrdersToJSON(List<Order> orders) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OrderJSONHelper helper = new OrderJSONHelper(orders);
        String json = gson.toJson(helper);
        FileWriter fileWriter = new FileWriter(jsonPath);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(json);
        printWriter.close();
    }

    public static boolean Create(Order order) throws IOException {
        List<Order> orders = ReadOrdersFromJSON();

        if (OrderExists(orders, order.GetId())) {
            // Order with same ID already exists
            return false;
        }

        orders.add(order);
        SaveOrdersToJSON(orders);
        return true;
    }

    private static boolean Update(Order order) throws IOException {
        List<Order> orders = ReadOrdersFromJSON();

        if (OrderExists(orders, order.GetId())) {
            orders.set(
                    IntStream.range(0, orders.size()).filter(i -> order.GetId() == orders.get(i).GetId()).findFirst().orElse(0),
                    order
            );
            SaveOrdersToJSON(orders);
            return true;
        }

        // Order doesn't exist
        return false;
    }

    public static boolean Delete(int id) throws IOException {
        List<Order> orders = ReadOrdersFromJSON();
        if (OrderExists(orders, id)) {
            int index = IntStream.range(0, orders.size()).filter(i -> id == orders.get(i).GetId()).findFirst().orElse(-1);
            if (index >= 0) {
                orders.remove(index);
                SaveOrdersToJSON(orders);
                return true;
            }
        }
        return false;
    }

    public static Order GetById(int id) throws IOException {
        List<Order> orders = ReadOrdersFromJSON();
        if(OrderExists(orders, id)) {
            int index = IntStream.range(0, orders.size()).filter(i -> id == orders.get(i).GetId()).findFirst().orElse(-1);
            if (index != -1) {
                return orders.get(index);
            }
        }
        return null;
    }

    private static boolean OrderExists(List<Order> orderList, int id) {
        return orderList.stream().map(Order::GetId).collect(Collectors.toList()).contains(id);
    }
}
