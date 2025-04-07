import java.time.LocalDate;

public abstract class Ingresso {
    private String show;
    private LocalDate data;
    protected double valor;

    public Ingresso(String show, LocalDate data, double valor) {
        this.show = show;
        this.valor = valor;
        this.data = data;
    }

    public String getShow() {
        return show;
    }

    public LocalDate getData() {
        return data;
    }

    public abstract double getValor();
}