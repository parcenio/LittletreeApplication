package br.com.pauloarcenio.bd;

import br.com.pauloarcenio.base.Base;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LittletreeBD {

    public static void inicializarBD() {
        String sql;
        Connection con = conectar();
        sql = "Create table if not exists cliente "
                + "(id int not null auto_increment primary key, "
                + "nome varchar(20) not null, "
                + "contato varchar(15) not null,"
                + "vendas int)";
        execute(con, sql);
        sql = "Create table if not exists produtos "
                + "(id int not null auto_increment primary key, "
                + "nome varchar(15) not null,"
                + "tipo varchar(15) not null,"
                + "valor int not null,"
                + "quantidade int)";
        execute(con, sql);
        sql = "Create table if not exists vendas"
                + "(id int not null auto_increment primary key, "
                + "idCliente int not null, "
                + "nomeCliente varchar(20) not null,"
                + "descricao varchar(50) not null,"
                + "valorTotal int not null,"
                + "data date not null,"
                + "foreign key (idCliente) references cliente(id))";
        execute(con, sql);
        sql = "Create table if not exists pedido"
                + "(id int not null auto_increment primary key, "
                + "nomeProduto varchar(20) not null,"
                + "quantidade int not null,"
                + "valor int not null)";
        execute(con, sql);
        desconectar(con);
    }

    public static Connection conectar() {
        Connection con = null;
        final String USUARIO = "root";
        final String SENHA = "arcenio1213";
        final String URL = "jdbc:mysql://localhost/littletree";
        try {
            con = DriverManager.getConnection(URL,
                    USUARIO, SENHA);
        } catch (SQLException ex) {
            Base.mensagemDeErro("Não foi possível conectar ao banco de dados. "
                    + "Verifique e tente posteriormente");
            System.exit(1);
        }
        return con;
    }

    public static void desconectar(Connection c) {
        try {
            c.close();
        } catch (SQLException ex) {
        }
    }

    public static void execute(String sql, boolean continuaNoErro) {
        Connection con = conectar();
        try {
            con.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            if (!continuaNoErro) {
                System.exit(1);
            }
        }
        desconectar(con);
    }

    private static void execute(Connection con, String sql) {
        try {
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
//            System.out.println(ex.getLocalizedMessage());
            Base.mensagemDeErro("Não foi possível executar\n" + sql);
            System.exit(1);
        }
    }

    public static void limparTabelaPedido() {
        Connection con = conectar();
        String sql = "truncate table pedido";
        try {
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
//            System.out.println(ex.getLocalizedMessage());
            Base.mensagemDeErro("Não foi possível executar\n" + sql);
            System.exit(1);
        }
    }
}
