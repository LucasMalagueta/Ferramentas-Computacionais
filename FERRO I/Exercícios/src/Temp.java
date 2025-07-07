import java.util.Scanner;

public class Temp {
    public static void main(String[] args) {

        System.out.println("Digite a tempera em graus celsius:");
        Scanner scanner = new Scanner(System.in);
        int celsius = scanner.nextInt();

        double fahrenheit = (celsius * 9/5) + 32;
        double kelvin = celsius + 273;
        double rankine =  (celsius * 9/5) + 491.67;

        System.out.printf("\nFahrenheit: %.2f\n", fahrenheit);
        System.out.printf("Kelvin: %.2f\n", kelvin);
        System.out.printf("Rankine: %.2f\n", rankine);

    }
}