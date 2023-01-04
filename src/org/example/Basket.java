package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {

    protected String[] products;
    protected int[] price;
    protected boolean[] isFilled;
    protected int[] numberOfPieces;
    protected File file = new File("basket.json");
    protected int positionInReceipt = 0;


    public Basket(String[] products, int[] price) throws IOException {
        this.products = products;
        this.price = price;
        isFilled = new boolean[products.length];
        numberOfPieces = new int[products.length];
        if (file.exists() && file.length() != 0) {
            loadFromTxtFile();
            fromJsonFile();
        }
    }

    //метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        numberOfPieces[(productNum - 1)] = numberOfPieces[(productNum - 1)] + amount;
        isFilled[(productNum - 1)] = true;
    }

    //метод вывода на экран покупательской корзины.
    public void printCart() {
        System.out.println("Ваша корзина: ");
        int sumProducts = 0;
        positionInReceipt = 0;
        for (boolean f : isFilled) {
            if (f) {
                System.out.println(products[positionInReceipt] + " " +
                        numberOfPieces[positionInReceipt] + " шт " + price[positionInReceipt] +
                        " руб/шт " + (price[positionInReceipt] * numberOfPieces[positionInReceipt]) + " руб. в сумме");
                sumProducts = sumProducts + (price[positionInReceipt] * numberOfPieces[positionInReceipt]);
            }
            positionInReceipt++;
        }
        System.out.println("К оплате " + sumProducts + " руб");
    }

    //метод сохранения корзины в текстовый файл; использовать встроенные сериализаторы нельзя;

    protected void saveText() {
        try (PrintWriter out = new PrintWriter(file);) {
            for (boolean f : isFilled) {
                if (f) {
                    out.println(positionInReceipt + " " +
                            numberOfPieces[positionInReceipt] + " ");
                }
                positionInReceipt += 1;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!!!");
        }
        positionInReceipt = 0;
    }

    //метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public void loadFromTxtFile() throws IOException {
        try (FileInputStream f = new FileInputStream(file)) {
            byte[] bytes = new byte[(char) file.length()];
            f.read(bytes);
            StringBuilder inputFromFile = new StringBuilder();
            for (byte aByte : bytes) {
                char s = (char) aByte;
                inputFromFile.append(s);
            }
            String[] parts = inputFromFile.toString().split(" ");
            for (int i = 0; i < parts.length; i++) {
                positionInReceipt = Integer.parseInt(parts[i]);
                i += 1;
                isFilled[positionInReceipt] = true;
                numberOfPieces[positionInReceipt] = Integer.parseInt(parts[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        positionInReceipt = 0;
    }

    public void toJsonFile(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(this.file.getPath())) {
            gson.toJson(file, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fromJsonFile() {
        Gson gson = new Gson();
        try (
                Reader reader = new FileReader(file.getPath());
        ) {
            Basket basket = gson.fromJson(reader, Basket.class);
            System.out.println(basket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearBasket() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }
}


