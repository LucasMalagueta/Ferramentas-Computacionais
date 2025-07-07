import java.time.LocalDate;

public class IngressoNormal extends Ingresso{

    public IngressoNormal(String show, LocalDate data, double valor){
        super(show, data, valor);
    }

    @Override
    public double getValor() {

        LocalDate hoje = LocalDate.now();
        int diasRestantes = getData().getDayOfYear() - hoje.getDayOfYear();

        if (diasRestantes <= 0)
            diasRestantes = 0;

        if(diasRestantes > 5)
            return valor;

        return valor * Math.pow(1.1, 5 - diasRestantes);
    }
}
