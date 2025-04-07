import java.util.ArrayList;
import java.util.List;

public class Dicionario implements IDic {

    private List<Termo> termos;

    public Dicionario(){
        termos = new ArrayList<>();
    }

    @Override
    public boolean inserir(Termo termo) {

        for (Termo t : termos)
            if (t.getPalavra().equals(termo.getPalavra()))
                return false;

        termos.add(termo);
        return true;
    }

    @Override
    public String traduzir(String palavra) {
        for (Termo t : termos)
            if (t.getPalavra().equals(palavra))
                return t.getTraducao();

        return "Termo não encontrado.";
    }

    @Override
    public boolean remover(String palavra) {
        for (Termo t : termos)
            if (t.getPalavra().equals(palavra)){
                termos.remove(t);
                return true;
            }

        return false;
    }

    public String PrintarSinonimos(String palavra) {
        for (Termo t : termos) {
            if (t.getPalavra().equals(palavra) && t instanceof TermoCompleto) {
                TermoCompleto termoCompleto = (TermoCompleto) t;

                return String.join(" ", termoCompleto.getSinonimos());
            }
        }
        return "Termo não encontrado ou não é um 'TermoCompleto'.";
    }
}