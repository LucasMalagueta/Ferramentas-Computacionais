import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Dicionario dicionario = new Dicionario();

        TermoSimples termoSimples = new TermoSimples("gato", "cat", "ket");
        TermoCompleto termoCompleto = new TermoCompleto("cachorro", "dog", Arrays.asList("cão", "canino", "perro"));

        dicionario.inserir(termoSimples);
        dicionario.inserir(termoCompleto);

        System.out.println(dicionario.traduzir("gato"));
        System.out.println(dicionario.traduzir("cachorro"));

        System.out.println(dicionario.PrintarSinonimos("cachorro"));

        dicionario.remover("gato");
        System.out.println(dicionario.traduzir("gato"));
    }
}