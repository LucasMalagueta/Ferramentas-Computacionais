public class PessoaJuridica extends Contribuinte {

    private int anoDeFundacao;

    public PessoaJuridica(String nome, String doc, double rendaBruta, int anoDeFundacao){
        super(nome, doc, rendaBruta);
        this.anoDeFundacao = anoDeFundacao;
    }

    public int getAnoDeFundacao() {
        return anoDeFundacao;
    }

    public void setAnoDeFundacao(int anoDeFundacao) {
        this.anoDeFundacao = anoDeFundacao;
    }

    @Override
    public double calcImposto() {
        return getRendaBruta() * 0.10;
    }
}