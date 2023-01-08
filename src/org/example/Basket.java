package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Basket {
    private final String[] products;
    private final int[] prices;
    private int[] productsCount;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        productsCount = new int[products.length];
    }

    protected void addToCart(int productNum, int amount) {
        productsCount[(productNum - 1)] += amount;
    }

    protected void printCart() {
        System.out.println("Ваша корзина:");
        int total = 0;
        for (int i = 0; i < productsCount.length; i++) {
            int count = productsCount[i];
            int sumProducts = prices[i] * count;
            if (count != 0) {
                total += sumProducts;
                System.out.println(products[i] + " " + count + " шт: " + sumProducts + " руб.");
            }
        }
        System.out.println("К оплате: " + total + " руб.");
    }

    protected void saveText(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (int i = 0; i < products.length; i++) {
                out.println(products[i] +" " + prices[i] + " " + productsCount[i]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!!!");

        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        List<String> basketList = Files.readAllLines(textFile.toPath());

        String[] productName = new String[basketList.size()];
        int[] prices = new int[basketList.size()];
        int[] productsCount = new int[basketList.size()];

        for (int i = 0; i < basketList.size(); i++) {
            String[] data = basketList.get(i).split(" ");
            productName[i] = data[0];
            prices[i] = Integer.parseInt(data[1]);
            productsCount[i] = Integer.parseInt(data[2]);
        }

        Basket basket = new Basket(productName, prices);
        basket.setProductCount(productsCount);
        System.out.println("Корзина восстановлена");
        return basket;
    }

    private void setProductCount(int[] productsCount) {
        this.productsCount = productsCount;

    }

    public void toJsonFile(File fileJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileJson)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Корзина: {" +
                "Название " + (Arrays.deepToString(products)) +
                "\n Количество: " + (Arrays.toString(productsCount)) +
                "}\n";
    }


    public static Basket fromJsonFile(File fileJson) {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(fileJson)) {
            Basket basket = gson.fromJson(reader, Basket.class);
            System.out.println(basket);
            return basket;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}


