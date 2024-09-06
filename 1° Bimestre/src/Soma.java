import java.util.Scanner;

public class Soma {
    public static void main(String[] args) {

        System.out.println("Digite um número inteiro:");
        Scanner scanner = new Scanner(System.in);
        long num = scanner.nextLong();
        long soma = 0;

        while (num != 0) {
            soma += num % 10;
            num /= 10;
        }

        System.out.println("\nO resultado da soma dos dígitos é: " + soma);

        scanner.close();
    }
}