package model.DAO;

import model.beans.Categoria;
import model.storage.ConPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CategoriaDAO implements DAO<Categoria> {
    @Override
    public Categoria getById(final int id) {
        Categoria c = null;
        try (Connection con = ConPool.getInstance().getConnection()) {
            if (con != null) {
                try (PreparedStatement stmt =
                             con.prepareStatement("SELECT * FROM categoria "
                                     + "WHERE idCategoria = ?")) {
                    stmt.setInt(1, id);
                    ResultSet resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        c = extract(resultSet, null);

                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public List<Categoria> getAll() {
        List<Categoria> cList = null;
        try (Connection con = ConPool.getInstance().getConnection()) {
            if (con != null) {
                cList = new ArrayList<>();

                try (PreparedStatement stmt =
                             con.prepareStatement("SELECT * FROM categoria")) {
                    ResultSet resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        cList.add(extract(resultSet, null));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cList;
    }

    @Override
    public boolean save(final Categoria entity) {
        if (entity != null) {
            try (Connection con = ConPool.getInstance().getConnection()) {
                if (con != null) {
                    String nomeCat = entity.getNome();
                    try (PreparedStatement stmt =
                             con.prepareStatement("INSERT INTO "
                                 + "categoria (nomeCategoria) VALUES (?)")) {
                        stmt.setString(1, nomeCat);
                        return stmt.executeUpdate() > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(final Categoria entity) {
        if (entity != null) {
            try (Connection con = ConPool.getInstance().getConnection()) {
                if (con != null) {
                    try (PreparedStatement stmt =
                             con.prepareStatement("UPDATE categoria "
                                 + "SET nomeCategoria = ? WHERE id = ?")) {
                        stmt.setString(1, entity.getNome());
                        stmt.setInt(2, entity.getIdCategoria());
                        return stmt.executeUpdate() > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(final Categoria entity) {
        if (entity != null) {
            try (Connection con = ConPool.getInstance().getConnection()) {
                if (con != null) {
                    try (PreparedStatement stmt =
                         con.prepareStatement("DELETE FROM categoria "
                             + "WHERE idCategoria = ?")) {
                        stmt.setInt(1, entity.getIdCategoria());
                        return stmt.executeUpdate() > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Categoria extract(final ResultSet resultSet,
                             final String alias) throws SQLException {
        if (resultSet != null) {
            Categoria c = new Categoria();
            String alias2 = "";

            if (!alias.isEmpty()) {
                alias2 = alias + ".";
            }

            c.setNome(resultSet.getString(alias2 + "nomeCategoria"));
            c.setIdCategoria(resultSet.getInt(alias2 + "idCategoria"));
            return c;
        }
        return null;
    }
}
