import java.util.ArrayList;
import java.util.List;

public class PagadoresImposto {

    private List<Contribuinte> contribuintes;

    public PagadoresImposto(List<Contribuinte> contribuintes){
        this.contribuintes = contribuintes;
    }

    public void addContribuinte(Contribuinte contribuinte){
        contribuintes.add(contribuinte);
    }

    public double calcImpostoTotal(){

        double totalImpostos = 0;

        for (Contribuinte contribuinte : contribuintes)
            totalImpostos += contribuinte.calcImposto();

        return totalImpostos;
    }

    public double calcPorcentFem() {

        int numMulheres = 0;

        if (contribuintes.isEmpty())
            return 0;

        for (Contribuinte contribuinte : contribuintes) {
            // é uma PessoaFisica
            if (contribuinte instanceof PessoaFisica) {

                // é do sexo feminino
                if ("Feminino".equalsIgnoreCase(((PessoaFisica) contribuinte).getSexo())) {
                    // Incrementa o contador de mulheres
                    numMulheres++;
                }
            }
        }

        return (numMulheres * 100.0) / contribuintes.size();
    }
}