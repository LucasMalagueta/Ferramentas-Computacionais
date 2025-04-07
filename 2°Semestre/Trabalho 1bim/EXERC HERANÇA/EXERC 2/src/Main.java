import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Contribuinte> contribuintes = new ArrayList<>();

        contribuintes.add(new PessoaFisica("João", "12345678900", 100.00, "Masculino"));
        contribuintes.add(new PessoaFisica("Maria", "98765432100", 100.00, "Feminino"));
        contribuintes.add(new PessoaFisica("Ana", "55555555555", 100.00, "Feminino"));
        contribuintes.add(new PessoaJuridica("Empresa X", "12345678000195", 100.00, 2010));
        contribuintes.add(new PessoaFisica("Carlos", "12312312312", 100.00, "Masculino"));
        contribuintes.add(new PessoaFisica("Fernanda", "65465465465", 100.00, "Feminino"));

        PagadoresImposto pagadoresImposto = new PagadoresImposto(contribuintes);

        double porcentagemMulheres = pagadoresImposto.calcPorcentFem();
        System.out.println("Porcentagem de mulheres: " + porcentagemMulheres + "%");

        double totalImpostos = pagadoresImposto.calcImpostoTotal();
        System.out.println("Total de impostos: R$ " + totalImpostos);
    }
}