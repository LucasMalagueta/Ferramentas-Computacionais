package com.example.botekofx.db.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    //inner class
    public static class Item{
        public Produto produto;
        public int quantidade;
        public double valor;

        public Item(Produto produto, int quantidade) {
            this.produto = produto;
            this.quantidade = quantidade;
            this.valor=quantidade* getProduto().getPreco();
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }
    private int id;
    private int numero;
    private LocalDate data;
    private String descricao;
    private double valor;
    private char status;
    private TipoPagamento tipoPagamento;
    private List<Item> itemList;

    public Comanda(int id, int numero, String descricao, double valor, char status, TipoPagamento tipoPagamento) {
        this.id = id;
        this.numero = numero;
        this.data = LocalDate.now();
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
        this.tipoPagamento = tipoPagamento;
        itemList=new ArrayList();
    }

    public Comanda(int numero, String descricao, double valor, char status, TipoPagamento tipoPagamento) {
        this(0,numero,descricao,valor,status,tipoPagamento);
    }

    public Comanda() {
        this(0,0,"",0,'A',null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
    public boolean addItem(Produto produto, int quantidade)
    {
        return itemList.add(new Item(produto,quantidade));
    }
    public boolean delItem(Item item){
        return itemList.remove(item);
    }
    public double somarItens(){
        double valor=0;
        for (Item item : itemList)
            valor+=item.produto.getPreco()*item.quantidade;
        return valor;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    @Override
    public String toString() {
        // Corrigido para incluir o especificador de formato "%.2f" para o valor
        return String.format("Nº %03d R$ %.2f", numero, somarItens());
    }
}
