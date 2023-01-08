package org.example;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, XPathExpressionException {
        Scanner scanner = new Scanner(System.in);
        String input;
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        Basket basket = new Basket(products, prices);
        ClientLog clientLog = new ClientLog();
        XMLReader xmlReader = new XMLReader();

        File loadFile = xmlReader.loadXML();
        File saveFile = xmlReader.saveXML();
        File logFile = xmlReader.logXML();

        if(loadFile != null && loadFile.length() != 0) {
            String path = loadFile.getPath();
            if (path.contains(".json")) {
                basket = Basket.fromJsonFile(loadFile);
            } else if (path.contains(".txt")) {
                basket = Basket.loadFromTxtFile(loadFile);
            }
        }


        System.out.println("Список достуных товаров: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Введите номер и количество товара или введите 'end' ");
            input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" ");

            int productNum;
            try {
                productNum = Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                System.out.println("Number format!!!");
                continue;
            }

            int productAmount;
            try {
                productAmount = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Number format!!!");
                continue;
            }

            if (productNum > products.length || productNum < 0) {
                System.out.println("This product is not exist!!!");
                continue;
            }

            basket.addToCart(productNum, productAmount);
            clientLog.log(productNum, productAmount);
        }

        System.out.println("""
                Хотите сохранить и выйти или закончить покупки? Введите цифру
                1.Сохранить и выйти
                2.Закончить покупки""");
        input = scanner.nextLine();
        if (saveFile != null) {
            if (input.equals("1") && basket != null) {
                if (saveFile.getPath().contains(".json")) {
                    basket.toJsonFile(saveFile);
                } else if (saveFile.getPath().contains(".txt")) {
                    basket.saveText(saveFile);
                }
            } else if (input.equals("2") && basket != null) {
                basket.printCart();
                saveFile.deleteOnExit();
            }
        }

        if (logFile != null) {
            clientLog.exportAsCSV(logFile);
        }
    }
}