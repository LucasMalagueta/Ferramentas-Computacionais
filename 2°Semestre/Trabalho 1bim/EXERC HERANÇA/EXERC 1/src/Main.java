import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        LocalDate dataShow = LocalDate.of(2025, 4, 15);
        Bilheteria bilheteria = new Bilheteria(100, "Show do Veigh", 100.0, 50.0, dataShow);

        Ingresso ingressoNormal = null;
        Ingresso ingressoVIP = null;
        int contNormal = 0, contVIP = 0;

        System.out.println("Valor total dos ingressos não vendidos: R$ " + bilheteria.calcularValorTotal());

        for (int i = 0; i < 3; i++) {
            ingressoNormal = bilheteria.vender('n');
            contNormal++;
        }

        System.out.println("Ingresso Normal vendido: " + ingressoNormal.getShow() + " - R$ " + ingressoNormal.getValor() + " - Qtd vendida: " + contNormal);

        for (int i = 0; i < 5; i++) {
            ingressoVIP = bilheteria.vender('v');
            contVIP++;
        }

        System.out.println("Ingresso VIP vendido: " + ingressoVIP.getShow() + " - R$ " + ingressoVIP.getValor() + " - Qtd vendida: " + contVIP);
        System.out.println("Valor total dos ingressos não vendidos após vendas: R$ " + bilheteria.calcularValorTotal());
        System.out.println("Quantidade de ingressos restantes após vendas: " + bilheteria.quantidadeIngressosRestantes());
    }
}