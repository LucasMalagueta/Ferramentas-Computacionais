public interface IDic {
    boolean inserir(Termo termo);
    String traduzir(String palavra);
    boolean remover(String palavra);
    String PrintarSinonimos(String palavra);
}