public class TermoSimples extends Termo{

    private String pronuncia;

    public TermoSimples(String palavra, String traducao, String pronuncia){
        super(palavra, traducao);
        this.pronuncia = pronuncia;
    }

    @Override
    public String descricao() {
        return "Palavra: " + getPalavra() + ", Tradução: " + getTraducao() + ", Pronúncia: " + getPronuncia();
    }

    public String getPronuncia() {
        return pronuncia;
    }
}