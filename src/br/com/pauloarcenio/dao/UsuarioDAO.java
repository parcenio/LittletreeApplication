package br.com.pauloarcenio.dao;

import br.com.pauloarcenio.base.Base;
import br.com.pauloarcenio.bd.LittletreeBD;
import br.com.pauloarcenio.entidades.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsuarioDAO {

    private static final String INSERT_SQL = "Insert into usuarios "
            + "(login, senha) "
            + "values ('%s', '%s')";
    private static final String UPDATE_SQL = "Update usuarios "
            + "set login = '%s', senha = '%s' "
            + "where id = %d";
    private static final String DELETE_SQL = "Delete from usuarios "
            + "where id = %d";
    private static final String SELECT_TODOS = "Select * from usuarios";
    private static final String SELECT_POR_ID = "Select * from usuarios "
            + "where id = %d;";
    private static final String SELECT_POR_NOME = "Select * from usuarios "
            + "where login = '%s';";

    public static void inserir(Usuario user) {
        String sql = String.format(INSERT_SQL, user.getLogin(), user.getSenha());
        LittletreeBD.execute(sql, true);
    }

    public static void alterar(Usuario user) {
        String sql = String.format(UPDATE_SQL, user.getLogin(), user.getSenha(), user.getId());
        LittletreeBD.execute(sql, true);
    }

    public static void remover(Usuario user) {
        String sql = String.format(DELETE_SQL, user.getId());
        LittletreeBD.execute(sql, true);
    }

    public static List<Usuario> selecionarTodos() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Connection con = LittletreeBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(SELECT_TODOS);
            while (rs.next()) {
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                listaUsuarios.add(new Usuario(id, login, senha));
            }
            LittletreeBD.desconectar(con);

        } catch (SQLException e) {
            Base.mensagem("Erro" + e);
            System.exit(1);
        }

        return listaUsuarios;
    }

    public static Usuario selecionaPeloLogin(String nome) {
        Usuario user = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nome);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String senha = rs.getString("senha");
            user = new Usuario(id, login, senha);

            LittletreeBD.desconectar(con);

        } catch (SQLException e) {
            Base.mensagem("Erro" + e);
            System.exit(1);
        }

        return user;
    }

    public static boolean userExiste(String nome) {
        boolean retorno = false;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_NOME, nome);
            ResultSet rs = con.createStatement().executeQuery(sql);
            if (rs.next() == true) {
                retorno = true;
            }
            
            LittletreeBD.desconectar(con);

        } catch (SQLException e) {
            Base.mensagem("Erro" + e);
            System.exit(1);
        }

        return retorno;
    }

    public static Usuario selecionaPeloID(int idUser) {
        Usuario user = null;
        Connection con = LittletreeBD.conectar();
        try {
            String sql = String.format(SELECT_POR_ID, idUser);
            ResultSet rs = con.createStatement().executeQuery(sql);
            rs.next();
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String senha = rs.getString("senha");
            user = new Usuario(id, login, senha);

            LittletreeBD.desconectar(con);

        } catch (SQLException e) {
            Base.mensagem("Erro" + e);
            System.exit(1);
        }

        return user;
    }

}
