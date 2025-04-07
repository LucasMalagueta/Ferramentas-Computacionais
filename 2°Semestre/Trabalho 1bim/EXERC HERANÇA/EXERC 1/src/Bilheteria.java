import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bilheteria {
    private List<Ingresso> ingressos;

    public Bilheteria(int qtdIngressos, String nomeShow, double valorIngressoNormal, double valorAdicionalVIP, LocalDate dataShow) {

        ingressos = new ArrayList<>();

        int qtdVIPs = (int)(qtdIngressos * 0.2);
        for (int i = 0; i < qtdVIPs; i++) {
            ingressos.add(new IngressoVIP(nomeShow, dataShow, valorIngressoNormal, valorAdicionalVIP));
        }

        int qtdNormais = qtdIngressos - qtdVIPs;
        for (int i = 0; i < qtdNormais; i++) {
            ingressos.add(new IngressoNormal(nomeShow, dataShow, valorIngressoNormal));
        }
    }

    public Ingresso vender(char tipo){
        for (int i = 0; i < ingressos.size(); i++){
            Ingresso ingresso = ingressos.get(i);
            if (tipo == 'n' && ingresso instanceof IngressoNormal){
                ingressos.remove(i);
                return ingresso;
            }

            else if (tipo == 'v' && ingresso instanceof IngressoVIP){
                ingressos.remove(i);
                return ingresso;
            }
        }
        return null;
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Ingresso ingresso : ingressos)
            total += ingresso.getValor();

        return total;
    }

    public int quantidadeIngressosRestantes(){
        return ingressos.size();
    }
}