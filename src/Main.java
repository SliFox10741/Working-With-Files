import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        String input;
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] price = {100, 200, 300};
        File textFile = new File("basket.txt");
        Basket basket = new Basket(products, price);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Список достуных товаров: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + price[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Введите номер и количество товара или введите 'end' ");
            input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" ");
            basket.addToCart(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }

        System.out.println("""
                Хотите сохранить и выйти или закончить покупки? Введите цифру
                1.Сохранить и выйти
                2.Закончить покупки""");
        input = scanner.nextLine();
        if (input.equals("1")) {
            basket.saveTxt(textFile);
        } else if (input.equals("2")) {
            basket.printCart();
            textFile.deleteOnExit();
        }
    }
}
