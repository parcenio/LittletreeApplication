package br.com.pauloarcenio.entidades;

public class Pedido {

    private int id;
    private String nomeProduto;
    private int quantidade = 0;
    private int valor;

    public Pedido() {
        //EMPTY
    }

    public Pedido(String nomeProduto, int quantidade, int valor) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Pedido(int id, String nomeProduto, int quantidade, int valor) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getValor() {
        return valor;
    }

    public void setQuantidade() {
        this.quantidade++;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setValor(int valor) {
        this.valor = this.quantidade * valor;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    @Override
    public String toString() {
        return nomeProduto + ", " + quantidade;
    }

}
