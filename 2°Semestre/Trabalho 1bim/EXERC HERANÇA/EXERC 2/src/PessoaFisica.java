public class PessoaFisica extends Contribuinte{

    private String sexo;

    public PessoaFisica(String nome, String doc, double rendaBruta, String sexo){
        super(nome, doc, rendaBruta);
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public double calcImposto(){

        double imposto = 0;
        double renda = getRendaBruta();

        if(renda <= 1400)
            imposto = 0;

        else if(renda >= 1400.01 && renda <= 2100)
            imposto = renda * 0.10 - 100;

        else if(renda >= 2100.01 && renda <= 2800)
            imposto = renda * 0.15 - 270;

        else if(renda >= 2800.01 && renda <= 3600)
            imposto = renda * 0.25 - 500;

        else if(renda >= 3600.01)
            imposto = renda * 0.30 - 700;

        return imposto;
    }
}