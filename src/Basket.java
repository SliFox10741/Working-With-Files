import java.io.File;

public class Basket {

    String[] products;
    int[] price;
    boolean[] isFilled;
    int[] arr;

    public Basket(String[] products, int[] price) {
        this.products = products;
        this.price = price;
        isFilled = new boolean[products.length];
        arr = new int[products.length];
    }

    //метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount){
        arr[(productNum - 1)] = arr[(productNum - 1)] + amount;
        isFilled[(productNum - 1)] = true;
    }

    //метод вывода на экран покупательской корзины.
    public int printCart(){
        int sumProducts = 0;
        int positionInReceipt = 0;

        for (boolean f : isFilled) {
            if (f) {
                System.out.println(products[positionInReceipt] + " " + arr[positionInReceipt] + " шт " + price[positionInReceipt] +
                        " руб/шт " + (price[positionInReceipt] * arr[positionInReceipt]) + " руб. в сумме");
                sumProducts = sumProducts + (price[positionInReceipt] * arr[positionInReceipt]);
            }
            positionInReceipt = positionInReceipt + 1;
        }
        return sumProducts;
    }

    //метод сохранения корзины в текстовый файл; использовать встроенные сериализаторы нельзя;
    public void pusaveTxt(File textFile){

    }

    //статический(!) метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
    public static Basket loadFromTxtFile(File textFile){

        return null;
    }
}
