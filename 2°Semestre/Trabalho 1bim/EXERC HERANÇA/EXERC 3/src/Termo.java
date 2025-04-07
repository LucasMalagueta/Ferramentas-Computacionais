public abstract class Termo {

    private String palavra;
    private String traducao;

    public Termo(String palavra, String traducao){
        this.palavra = palavra;
        this.traducao = traducao;
    }

    public String getPalavra() {
        return palavra;
    }

    public String getTraducao() {
        return traducao;
    }

    public abstract String descricao();
}