package pl.psklad.hello;

public class App {
    public static void main(String[] args) {
        var name = "Jakub";

        System.out.println(String.format("Hello %s", name));

        int a = 2;
        int b = 3;

        var result = a + b;

        if (result != 5) {
            throw new IllegalStateException("assertion error");
        }
    }
}
