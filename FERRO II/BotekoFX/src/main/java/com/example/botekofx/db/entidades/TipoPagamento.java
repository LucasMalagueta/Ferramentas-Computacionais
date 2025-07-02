package com.example.botekofx.db.entidades;

public class TipoPagamento {
    private int id;
    private String descricao;

    public TipoPagamento(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public TipoPagamento(String descricao) {
        this(0,descricao);
    }

    public TipoPagamento() {
        this(0,"");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Adicionado para exibir a descrição corretamente em ComboBox, ChoiceDialog, etc.
     * @return a descrição do tipo de pagamento.
     */
    @Override
    public String toString() {
        return descricao; // Retorna a descrição em vez do código do objeto
    }
}