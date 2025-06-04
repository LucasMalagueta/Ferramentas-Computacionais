import java.time.LocalDate;

public class IngressoVIP extends Ingresso {

    private double adicional;

    public IngressoVIP(String show, LocalDate data, double valor, double adicional){
        super(show, data, valor);
        this.adicional = adicional;
    }

    @Override
    public double getValor() {
        return valor + adicional;
    }
}