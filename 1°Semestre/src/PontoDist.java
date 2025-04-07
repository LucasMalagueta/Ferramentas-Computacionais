import java.util.Scanner;

public class PontoDist {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

        System.out.println("Digite o valor do ponto XA: ");
        double XA = scanner.nextDouble();

        System.out.println("Agora o valor do ponto  XB: ");
        double XB = scanner.nextDouble();

        System.out.println("Agora o valor do ponto  YA: ");
        double YA = scanner.nextDouble();

        System.out.println("Agora o valor do ponto  YB: ");
        double YB = scanner.nextDouble();

        double dist = Math.sqrt(Math.pow(XA - XB, 2)+Math.pow(YA - YB, 2));

        System.out.printf("A distancia é de %.2f", dist);

    }
}
