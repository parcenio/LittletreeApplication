package br.com.pauloarcenio.dao;

import br.com.pauloarcenio.base.Base;
import br.com.pauloarcenio.bd.LittletreeBD;
import br.com.pauloarcenio.entidades.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final String INSERT_SQL = "Insert into cliente "
            + "(nome, contato) "
            + "values ('%s', '%s')";
    private static final String UPDATE_SQL = "Update cliente "
            + "set nome = '%s', contato = '%s', vendas = %d "
            + "where id = %d";
    private static final String DELETE_SQL = "Delete from cliente "
            + "where id = %d";
    private static final String SELECT_TODOS = "Select * from cliente";
    private static final String SELECT_POR_ID = "Select * from cliente "
            + "where id = %d;";
    private static final String SELECT_POR_NOME = "Select * from cliente "
            + "where nome = '%s';";

    public static void inserir(Cliente cliente) {
        String sql = String.format(INSERT_SQL,
                cliente.getNome(),
                cliente.getContato());
        LittletreeBD.execute(sql, true);
    }

    public static void alterar(Cliente cliente) {
        String sql = String.format(UPDATE_SQL,
                cliente.getNome(),
                cliente.getContato(),
                cliente.getVendas(),
                cliente.getId());
        LittletreeBD.execute(sql, true);
    }

    public static void apagar(Cliente cliente) {
        String sql = String.format(DELETE_SQL, cliente.getId());
        LittletreeBD.execute(sql, true);
    }

    public static List<Cliente> selecionarTodos() {
        List<Cliente> lista = new ArrayList<>();
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(SELECT_TODOS);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String contato = rs.getString("contato");
                int vendas = rs.getInt("vendas");
                lista.add(new Cliente(id, nome, contato, vendas));
            }
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return lista;
    }

    public static Cliente getClientePorID(int idPesquisa) {
        Cliente retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_ID, idPesquisa);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String contato = rs.getString("contato");
            int vendas = rs.getInt("vendas");
            retorno = new Cliente(id, nome, contato, vendas);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }
    
    public static Cliente getClientePorNome(String nomeCliente) {
        Cliente retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeCliente);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String contato = rs.getString("contato");
            int vendas = rs.getInt("vendas");
            retorno = new Cliente(id, nome, contato, vendas);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }

    public static String getNomeCliente(String nomeCliente) {
        String retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeCliente);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            String nome = rs.getString("nome");
            retorno = nome;
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }

    public static boolean existeCliente(String nomeCliente, String contatoCliente) {
        boolean retorno = false;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeCliente);
            ResultSet rs = con.createStatement().executeQuery(sql);
            if (rs.next() == true) {
                String nome = rs.getString("nome");
                String contato = rs.getString("contato");
                if (nome.equals(nomeCliente) || contato.equals(contatoCliente)) {
                    retorno = true;
                }
            }
            LittletreeBD.desconectar(con);

        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }
}
