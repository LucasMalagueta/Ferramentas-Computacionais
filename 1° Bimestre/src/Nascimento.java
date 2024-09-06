import java.time.LocalDate;
import java.util.Scanner;

public class Nascimento {
    public static void main(String[] args) {
        int AnoAtual = LocalDate.now().getYear();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o seu ano de nascimento: ");
        int anoDeNascimento = scanner.nextInt();

        int Idade = AnoAtual - anoDeNascimento;
        System.out.println("Sua idade é: " + Idade);
    }
}
