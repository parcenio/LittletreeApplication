package br.com.pauloarcenio.entidades;

import br.com.pauloarcenio.enums.TipoLittle;

public abstract class Produto {

    private int id;
    private TipoLittle tipo;
    private String nome;
    private int valor;
    private int quantidade;

    public Produto(String nome, int valor, int quantidade) {

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public Produto(String nome, TipoLittle tipo, int valor, int quantidade) {

        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.quantidade = quantidade;
    }
    
    public Produto(int id, String nome,  int quantidade, int valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public Produto(int id, String nome, TipoLittle tipo, int valor, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public TipoLittle getTipo() {
        return tipo;
    }

    public int getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setTipo(TipoLittle tipo) {
        this.tipo = tipo;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
