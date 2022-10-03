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

public class ProdutoDAO {

    private static final String INSERT_SQL = "Insert into produtos "
            + "(nome, tipo, valor, quantidade) "
            + "values ('%s', '%s', %d, %d)";
    private static final String UPDATE_SQL = "Update produtos "
            + "set nome = '%s', tipo = '%s', valor = %d, quantidade = %d "
            + "where id = %d";
    private static final String DELETE_SQL = "Delete from produtos "
            + "where id = %d";
    private static final String SELECT_TODOS = "Select * from produtos";
    private static final String SELECT_TODOS_PEDIDO = "Select * from pedido";
    private static final String SELECT_POR_ID = "Select * from produtos "
            + "where id = %d;";
    private static final String SELECT_POR_NOME = "Select * from produtos "
            + "where nome = '%s';";

    public static void inserir(Produto produto) {
        String sql = String.format(INSERT_SQL,
                produto.getNome(),
                produto.getTipo().toString(),
                produto.getValor(),
                produto.getQuantidade());
        LittletreeBD.execute(sql, true);
    }

    public static void alterar(Produto produto) {
        String sql = String.format(UPDATE_SQL,
                produto.getNome(),
                produto.getTipo().getNomeLittle(),
                produto.getValor(),
                produto.getQuantidade(),
                produto.getId());
        LittletreeBD.execute(sql, true);
    }

    public static void apagar(Produto produto) {
        String sql = String.format(DELETE_SQL, produto.getId());
        LittletreeBD.execute(sql, true);
    }

    public static List<Littletree> selecionarTodos() {
        List<Littletree> lista = new ArrayList<>();
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(SELECT_TODOS);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                TipoLittle tipo = TipoLittle.getTipoPorNome(rs.getString("tipo"));
                int valor = rs.getInt("valor");
                int quantidade = rs.getInt("quantidade");
                lista.add(new Littletree(id, nome, tipo, valor, quantidade));
            }
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return lista;
    }

    public static boolean existeProduto(String nomeProduto) {
        boolean retorno = false;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeProduto);
            ResultSet rs = con.createStatement().executeQuery(sql);
            if (rs.next() == true) {
                String nome = rs.getString("nome");
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

    public static Littletree getLittletreePorID(int idPesquisa) {
        Littletree retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_ID, idPesquisa);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            TipoLittle tipo = TipoLittle.getTipoPorNome(rs.getString("tipo"));
            int valor = rs.getInt("valor");
            int quantidade = rs.getInt("quantidade");
            retorno = new Littletree(id, nome, tipo, valor, quantidade);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }

    public static Littletree getLittletreePorNome(String nomeLittle) {
        Littletree retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeLittle);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            TipoLittle tipo = TipoLittle.getTipoPorNome(rs.getString("tipo"));
            int valor = rs.getInt("valor");
            int quantidade = rs.getInt("quantidade");
            retorno = new Littletree(id, nome, tipo, valor, quantidade);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }

    public static String getNomeLittletreePorNome(String nomeLittle) {
        String retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nomeLittle);
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

    public static void baixaEstoque(List<Pedido> lista) {
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(SELECT_TODOS_PEDIDO);
            while (rs.next()) {
                String nomeProduto = rs.getString("nomeProduto");
                int quantidade = rs.getInt("quantidade");
                Littletree little = ProdutoDAO.getLittletreePorNome(nomeProduto);
                little.setQuantidade(little.getQuantidade() - quantidade);
                ProdutoDAO.alterar(little);
            }
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
    }
    
    //IMPLEMENTANDO CONSULTA ESTOQUE, PARA PODER ADICIONAR MAIS PRODUTO OU NÃO!
    public static boolean consultaEstoque(Produto produto){
        boolean retorno = false;
        int qntdPedido = PedidoDAO.qtdProdutoPedido(produto.getNome());
        Littletree littleEstoque = ProdutoDAO.getLittletreePorNome(produto.getNome());       
        if (littleEstoque.getQuantidade() > qntdPedido){
            retorno = true;
        }        
        return retorno;
    }
}
