package br.com.pauloarcenio.dao;

import br.com.pauloarcenio.base.Base;
import br.com.pauloarcenio.bd.LittletreeBD;
import br.com.pauloarcenio.entidades.Littletree;
import br.com.pauloarcenio.entidades.Pedido;
import br.com.pauloarcenio.entidades.Produto;
import br.com.pauloarcenio.enums.TipoLittle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private static final String INSERT_SQL = "Insert into pedido "
            + "(nomeProduto, quantidade, valor)"
            + "values ('%s', %d, %d)";
    private static final String UPDATE_SQL = "Update pedido "
            + "set nomeProduto = '%s', quantidade = %d, valor = %d "
            + "where id = %d";
    private static final String DELETE_SQL = "Delete from pedido "
            + "where id = %d";
    private static final String SELECT_TODOS = "Select * from pedido";
    private static final String SELECT_POR_ID = "Select * from pedido "
            + "where id = %d;";
    private static final String SELECT_POR_NOME = "Select * from pedido "
            + "where nomeProduto = '%s';";
    private static final String SOMA_VALORES = "Select SUM(valor) from pedido";
    private static final String LIMPAR_TABELA = "truncate table pedido";

    public static void inserir(Pedido pedido) {
        String sql = String.format(INSERT_SQL,
                pedido.getNomeProduto(),
                pedido.getQuantidade(),
                pedido.getValor());
        LittletreeBD.execute(sql, true);
    }

    public static void alterar(Pedido pedido) {
        String sql = String.format(UPDATE_SQL,
                pedido.getNomeProduto(),
                pedido.getQuantidade(),
                pedido.getValor(),
                pedido.getId());
        LittletreeBD.execute(sql, true);
    }

    public static void apagar(Pedido pedido) {
        String sql = String.format(DELETE_SQL, pedido.getId());
        LittletreeBD.execute(sql, true);
    }

    public static List<Pedido> selecionarTodos() {
        List<Pedido> lista = new ArrayList<>();
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(SELECT_TODOS);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomeProduto = rs.getString("nomeProduto");
                int quantidade = rs.getInt("quantidade");
                int valor = rs.getInt("valor");
                lista.add(new Pedido(id, nomeProduto, quantidade, valor));
            }
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return lista;
    }

    public static Pedido getPedidoPorID(int idPesquisa) {
        Pedido retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_ID, idPesquisa);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String nome = rs.getString("nomeProduto");
            int quantidade = rs.getInt("quantidade");
            int valor = rs.getInt("valor");
            retorno = new Pedido(id, nome, quantidade, valor);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }

    public static boolean existeProdutoPedido(String nomeProduto) {
        boolean retorno = false;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeProduto);
            ResultSet rs = con.createStatement().executeQuery(sql);
            if (rs.next() == true) {
                String nome = rs.getString("nomeProduto");
                if (nome.equals(nomeProduto)) {
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

    public static Pedido getPedidoPorNomeProduto(String nomeCliente) {
        Pedido retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeCliente);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String nome = rs.getString("nomeProduto");
            int quantidade = rs.getInt("quantidade");
            int valor = rs.getInt("valor");
            retorno = new Pedido(id, nome, quantidade, valor);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }

    public static int valorTotal() {
        int retorno = 0;
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(SOMA_VALORES);
            rs.next();
            int valorTotal = rs.getInt("sum(valor)");
            retorno = valorTotal;

            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }

        return retorno;
    }
    
    public static void limparPedidos(){
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(LIMPAR_TABELA);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }

    }
}
