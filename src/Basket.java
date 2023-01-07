import java.io.*;

public class Basket {

    String[] products;
    int[] price;
    boolean[] isFilled;
    int[] numberOfPieces;
    int positionInReceipt = 0;



    public Basket(String[] products, int[] price) throws IOException {
        this.products = products;
        this.price = price;
        isFilled = new boolean[products.length];
        numberOfPieces = new int[products.length];

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
    public void saveBin(File fileBin) throws IOException {
        positionInReceipt = 0;
        try (FileOutputStream fos = new FileOutputStream(fileBin);
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            for (boolean f : isFilled) {
                if (f) {
                    out.writeBytes(positionInReceipt + " " +
                            numberOfPieces[positionInReceipt] + " ");
                }
                positionInReceipt += 1;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public static Basket loadFromBinFile(File binFile) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(binFile))) {
            Basket basket = (Basket) objectInputStream.readObject();
            System.out.print("Basket return");
            basket.printCart();
            return basket;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
