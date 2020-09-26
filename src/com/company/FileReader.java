package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class FileReader {

    public static List<Item> GetListFromFile(String filename, String separator) throws FileNotFoundException {
        URL url = Main.class.getResource(filename);
        File file = new File(url.getPath());
        Scanner scanner = new Scanner(new FileInputStream(file));

        String[] header = scanner.nextLine().split(separator);
        int priceColumn = Arrays.asList(header).indexOf("PRECO");
        int nameColumn = (priceColumn == 0) ? 1 : 0;

        List<Item> list = new ArrayList<Item>();
        while (scanner.hasNextLine()) {
            String[] content = scanner.nextLine().split(separator);
            if (content.length > 1) {
                list.add(new Item(
                        content[nameColumn],
                        Float.parseFloat(content[priceColumn].replace(",", "."))
                ));
            }
        }
        scanner.close();

        return list;
    }
}
