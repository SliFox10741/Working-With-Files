import java.io.*;

public class Basket {

    String[] products;
    int[] price;
    boolean[] isFilled;
    int[] numberOfPieces;
    File file = new File("basket.txt");
    int positionInReceipt = 0;


    public Basket(String[] products, int[] price) throws IOException {
        this.products = products;
        this.price = price;
        isFilled = new boolean[products.length];
        numberOfPieces = new int[products.length];
        if (file.exists()) {
            loadFromTxtFile(file);
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
    public void saveTxt(File textFile) throws IOException {
        positionInReceipt = 0;
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (boolean f : isFilled) {
                if (f) {
                    out.print(positionInReceipt + " " +
                            numberOfPieces[positionInReceipt] + " ");
                }
                positionInReceipt += 1;
            }
        }
    }

    //статический(!) метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public void loadFromTxtFile(File textFile) throws IOException {
        try (FileInputStream f = new FileInputStream(textFile)) {
            byte[] bytes = new byte[(char) textFile.length()];
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
}


