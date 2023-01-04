package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        String input;
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        ClientLog clientLog = new ClientLog();

        File fileCSV = new File("log.csv");
        File file = new File("basket.txt");
        Basket basket = new Basket(products, prices);

        if (file.exists()) {
            //  Basket.loadFromTxtFile(file);
            Basket.fromJsonFile("basket.json");
        }

        System.out.println("Список достуных товаров: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Введите номер и количество товара или введите 'end' ");
            input = scanner.nextLine();
            if (input.equals("end")) {
                clientLog.log(input);
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
        if (input.equals("1")) {
            basket.saveText(file);
            basket.toJsonFile("basket.json");
            clientLog.log("Сохранить корзину");
        } else if (input.equals("2")) {
            basket.printCart();
            clientLog.log("Закончить покупки");
        }
        clientLog.exportAsCSV(fileCSV);
    }
}