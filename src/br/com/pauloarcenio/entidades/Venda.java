package br.com.pauloarcenio.entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {

    private int id;
    private Cliente cliente;
    private String descricao;
    private int valorTotal;
    private final LocalDate data;

    public String getDataFormatada() {
        return data.format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Venda(Cliente cliente, String descricao,int valorTotal, LocalDate data) {
        this.cliente = cliente;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.data = data;
    }

    public Venda(int id, Cliente cliente, String descricao,int valorTotal, LocalDate data) {
        this.id = id;
        this.cliente = cliente;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Venda:" + id + ", para " + cliente + " ,produtos ->" + descricao +" ,valorTotal R$" + valorTotal + ", no dia= " + data + '}';
    }

}
