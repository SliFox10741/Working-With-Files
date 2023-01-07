import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class Basket implements Serializable {

    private String[] products;
    private int[] prices;
    private int[] productCount;



    public Basket(String[] products, int[] prices) throws IOException {
        this.products = products;
        this.prices = prices;
        this.productCount = new int[prices.length];

    }

    public void setProductCount(int[] productCount) {
        this.productCount = productCount;
    }

    //метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        productCount[productNum - 1] += amount;
    }

    //метод вывода на экран покупательской корзины.
    public void printCart() {
        System.out.println("Ваша корзина: ");
        int total = 0;
        for (int i = 0; i < productCount.length; i++) {
            int count = productCount[i];
            int sumProducts = prices[i] * count;
            if (count != 0) {
                total += sumProducts;
                System.out.println(products[i] + " " + count + ": " + sumProducts + " руб");
            }
        }
        System.out.println("К оплате " + total + " руб");
    }

    protected void saveText(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (int i = 0; i < products.length; i++) {
                out.println(products[i] + " " + prices[i] + " " + productCount[i]);
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

        for (int i = 0; i <= basketList.size() - 1; i++) {
            String[] data = basketList.get(i).split(" ");
            productName[i] = data[0];
            prices[i] = Integer.parseInt(data[1]);
            productsCount[i] = Integer.parseInt(data[2]);
        }

        Basket basket = new Basket(productName, prices);
        basket.setProductCount(productsCount);
        System.out.println("Корзина восстановлена");
        basket.printCart();
        return basket;

    }

    //метод сохранения корзины в текстовый файл; использовать встроенные сериализаторы нельзя;
    public void saveBin(File file) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public static Basket loadFromBinFile(File binFile) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(binFile))) {
            Basket basket = (Basket) objectInputStream.readObject();
            System.out.println("Корзина восстановлена");
            basket.printCart();
            return basket;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
