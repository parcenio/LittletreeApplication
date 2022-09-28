
package br.com.pauloarcenio.entidades;


public class Cliente {
    private int id;
    private String nome,contato;
    private int vendas;

    public Cliente( String nome, String contato) {
        this.nome = nome;
        this.contato = contato;
    }
    public Cliente( String nome, String contato,int vendas) {
        this.nome = nome;
        this.contato = contato;
        this.vendas = vendas;
    }
    
    public Cliente( int id, String nome, String contato,int vendas) {
        this.id = id;
        this.nome = nome;
        this.contato = contato;
        this.vendas = vendas;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getContato() {
        return contato;
    }

    public int getVendas() {
        return vendas;
    }

    public void setVendas(int vendas) {
        this.vendas = vendas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return "o cliente " + nome;
    }
    
    
}
