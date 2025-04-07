public abstract class Contribuinte implements ITributacao {

    private String nome;
    private String doc;
    private double rendaBruta;

    public Contribuinte(){}

    public Contribuinte(String nome, String doc, double rendaBruta){
        this.nome = nome;
        this.doc = doc;
        this.rendaBruta = rendaBruta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public double getRendaBruta() {
        return rendaBruta;
    }

    public void setRendaBruta(double rendaBruta) {
        this.rendaBruta = rendaBruta;
    }

    public abstract double calcImposto();
}