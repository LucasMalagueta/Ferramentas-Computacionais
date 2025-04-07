import java.util.Scanner;

public class Juros {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Amortização no sistema Francês de Amortização (Tabela Price) ");
        System.out.println("--------------------------------------------------------------");

        System.out.print(" Montante Financiado: ");
        double valorFinanciado = scanner.nextDouble();

        System.out.print("Juros Financiamento: ");
        double taxaJuros = scanner.nextDouble() / 100;

        System.out.print("Nº de Parcelas: ");
        int numeroParcelas = scanner.nextInt();

        double valorParcela = valorFinanciado * (taxaJuros * Math.pow(1 + taxaJuros, numeroParcelas)) / (Math.pow(1 + taxaJuros, numeroParcelas) - 1);

        double saldoDevedor = valorFinanciado;
        double totalJurosPago = 0.0;

        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Tabela Price - Detalhamento das Parcelas");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("Valor Financiado : R$ %.2f%n", valorFinanciado);
        System.out.printf("Taxa de Juros : %.2f %% %n", taxaJuros * 100);
        System.out.printf("Número de Parcelas : %d%n", numeroParcelas);
        System.out.println("--------------------------------------------------------------");
        System.out.println("Parcela  Valor da Parcela  Amortização  Juros  Saldo Devedor");

        for (int parcelaAtual = 1; parcelaAtual <= numeroParcelas; parcelaAtual++) {
            double juros = saldoDevedor * taxaJuros;
            double amortizacao = valorParcela - juros;
            saldoDevedor -= amortizacao;
            totalJurosPago += juros;

            String myStr = "Nº%-10d  %,-14.2f  %,-12.2f  %,-10.2f  %,.2f";
            String result = String.format(myStr, parcelaAtual, valorParcela, amortizacao, juros, saldoDevedor);
            System.out.println(result);
        }

        System.out.printf("\nTotal de juros pago: R$ %.2f\n", totalJurosPago);
        System.out.println("--------------------------------------------------------------");
    }
}