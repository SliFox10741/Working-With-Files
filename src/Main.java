import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String input;
        String[] products = {"Хлеб", "Яблоки", "Молоко"};

        boolean[] isFilled = new boolean[(products.length)];
        int[] price = {100, 200, 300};
        int counter = 1;

        Basket basket = new Basket(products, price);

        System.out.println("Список достуных товаров: ");

        for (int i = 0; i < products.length; i++) {
            System.out.println(counter++ + ". " + products[i] + " " + price[i] + " руб/шт");
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите номер и количество товара или введите 'end' ");
            input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" ");
            basket.addToCart(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }

        System.out.println("Ваша корзина: ");
        int sumProducts = basket.printCart();

        System.out.println("К оплате " + sumProducts + " руб");


    }
}
