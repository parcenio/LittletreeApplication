package br.com.pauloarcenio.dao;

import br.com.pauloarcenio.base.Base;
import br.com.pauloarcenio.bd.LittletreeBD;
import br.com.pauloarcenio.entidades.Cliente;
import br.com.pauloarcenio.entidades.Littletree;
import br.com.pauloarcenio.entidades.Pedido;
import br.com.pauloarcenio.entidades.Venda;
import br.com.pauloarcenio.enums.TipoLittle;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VendasDAO {

    private static final String INSERT_SQL = "Insert into vendas "
            + "(idCliente, nomeCliente, descricao, valorTotal, data) "
            + "values (%d, '%s', '%s', %d, '%s')";

    private static final String DELETE_SQL = "Delete from vendas "
            + "where id = %d";
    private static final String SELECT_TODOS = "Select * from vendas";
    private static final String SELECT_POR_ID = "Select * from vendas "
            + "where id = %d;";
    private static final String SELECT_POR_CLIENTE = "Select * from vendas "
            + "where nomeCliente = '%s';";

    public static void inserir(Venda venda) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd");
        String sql = String.format(INSERT_SQL,
                venda.getCliente().getId(),
                venda.getCliente().getNome(),
                venda.getDescricao(),
                venda.getValorTotal(),
                venda.getData().format(dtf));
        LittletreeBD.execute(sql, true);
    }

    public static void apagar(Venda venda) {
        String sql = String.format(DELETE_SQL, venda.getId());
        LittletreeBD.execute(sql, true);
    }

    public static List<Venda> selecionarTodos() {
        List<Venda> lista = new ArrayList<>();
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(SELECT_TODOS);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idCliente = rs.getInt("idCliente");
                Cliente cliente = ClienteDAO.getClientePorID(idCliente);
                String descricao = rs.getString("descricao");
                int valorTotal = rs.getInt("valorTotal");
                LocalDate data = new Date(rs.getDate("data")
                        .getTime()).toLocalDate();
                lista.add(new Venda(id, cliente, descricao, valorTotal, data));
            }
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return lista;
    }

    public static Venda getVendaPorID(int idPesquisa) {
        Venda retorno = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_ID, idPesquisa);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            int idCliente = rs.getInt("idCliente");
            Cliente cliente = ClienteDAO.getClientePorID(idCliente);
            String descricao = rs.getString("descricao");
            int valorTotal = rs.getInt("valorTotal");
            LocalDate data = new Date(rs.getDate("data")
                    .getTime()).toLocalDate();
            retorno = new Venda(id, cliente, descricao, valorTotal, data);
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return retorno;
    }

    public static List<Venda> selecionarPorNomeCliente(String nome) {
        List<Venda> lista = new ArrayList<>();
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_CLIENTE, nome);
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idCliente = rs.getInt("idCliente");
                Cliente cliente = ClienteDAO.getClientePorID(idCliente);
                String nomeCliente = cliente.getNome();
                String descricao = rs.getString("descricao");
                int valorTotal = rs.getInt("valorTotal");
                LocalDate data = new Date(rs.getDate("data")
                        .getTime()).toLocalDate();
                if (nome.equals(nomeCliente)) {
                    lista.add(new Venda(id, cliente, descricao, valorTotal, data));
                }
            }
            LittletreeBD.desconectar(con);
        } catch (SQLException e) {
//            System.out.println(e.getLocalizedMessage());
            Base.mensagem("Erro" + e);
            System.exit(1);
        }
        return lista;
    }
   
}
