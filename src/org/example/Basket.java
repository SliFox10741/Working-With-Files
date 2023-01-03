package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Basket {

    protected String[] products;
    protected int[] price;
    protected boolean[] isFilled;
    protected int[] numberOfPieces;
    protected File fileJSON = new File("basket.json");
    protected int positionInReceipt = 0;


    public Basket(String[] products, int[] price) throws IOException {
        this.products = products;
        this.price = price;
        isFilled = new boolean[products.length];
        numberOfPieces = new int[products.length];
        if (fileJSON.exists() && fileJSON.length() != 0) {
            loadFromTxtFile();
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
    public void saveTxt() throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("name", "Maxim");
        JSONArray list = new JSONArray();
        for (boolean f : isFilled) {
            if (f) {
                list.add(positionInReceipt + " " +
                        numberOfPieces[positionInReceipt] + " ");
            }
            positionInReceipt += 1;
        }
        obj.put("basket", list);

        try (FileWriter file = new FileWriter(fileJSON)) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        positionInReceipt = 0;
    }

    //метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public void loadFromTxtFile() throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileJSON));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray products = (JSONArray) jsonObject.get("basket");
            for (Object product : products) {
                String[] index = product.toString().split(" ");
                positionInReceipt = Integer.parseInt(index[0]);
                isFilled[positionInReceipt] = true;
                numberOfPieces[positionInReceipt] = Integer.parseInt(index[1]);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        positionInReceipt = 0;
    }

    public void clearBasket() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileJSON);
        writer.print("");
        writer.close();
    }
}


