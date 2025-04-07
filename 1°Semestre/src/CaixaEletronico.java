import java.util.Scanner;

public class CaixaEletronico {
    public static void main(String[] args) {
        Scanner Scanner = new Scanner(System.in);
        System.out.print("Informe o valor para saque: ");
        double valorTotal = Scanner.nextDouble();

        //Extraindo a parte inteira e a parte dos centavos
        int valorInteiro = (int) valorTotal;
        int valorCentavos = (int) Math.round((valorTotal - valorInteiro) * 100);

        int[] notasDisponiveis = {100, 50, 20, 10, 5, 2};
        int[] moedasDisponiveis = {100, 50, 25, 10, 5, 1};

        System.out.println("A composição do saque será:");

        //Contagem das notas necessárias
        for (int nota : notasDisponiveis) {
            int qtdNotas = valorInteiro / nota;
            valorInteiro %= nota;
            if (qtdNotas > 0) {
                System.out.println(qtdNotas + " nota(s) de " + nota + " reais");
            }
        }

        //Contagem das moedas necessárias
        for (int i = 0; i < moedasDisponiveis.length; i++) {
            int qtdMoedas = valorCentavos / moedasDisponiveis[i];
            valorCentavos %= moedasDisponiveis[i];
            if (qtdMoedas > 0) {
                if (i == 0) {
                    System.out.println(qtdMoedas + " moeda(s) de 1 real");
                } else {
                    System.out.println(qtdMoedas + " moeda(s) de " + moedasDisponiveis[i] + " centavos");
                }
            }
        }
    }
}