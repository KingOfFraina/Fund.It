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
        if (id <= 0) {
            throw new IllegalArgumentException("Id <= 0");
        } else {
            try (Connection con = ConPool.getInstance().getConnection()) {
                try (PreparedStatement stmt =
                             con.prepareStatement("SELECT * FROM categoria "
                                     + "WHERE idCategoria = ?")) {
                    stmt.setInt(1, id);
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        c = extract(resultSet);
                    }
                }

                return c;
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Categoria> getAll() {
        List<Categoria> cList = null;
        try (Connection con = ConPool.getInstance().getConnection();
             PreparedStatement stmt =
                     con.prepareStatement("SELECT * FROM categoria")) {

            ResultSet resultSet = stmt.executeQuery();
            cList = new ArrayList<>();

            while (resultSet.next()) {
                cList.add(extract(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
        }
        return cList;
    }

    @Override
    public boolean save(final Categoria entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection con = ConPool.getInstance().getConnection()) {
                try (PreparedStatement stmt =
                             con.prepareStatement("INSERT INTO "
                                             + "categoria (nomeCategoria)"
                                             + " VALUES (?)",
                                     PreparedStatement
                                             .RETURN_GENERATED_KEYS)) {

                    stmt.setString(1, entity.getNome());
                    boolean flag = stmt.executeUpdate() > 0;

                    ResultSet set = stmt.getGeneratedKeys();
                    if (set.next()) {
                        entity.setIdCategoria(set.getInt(1));
                    }
                    return flag;
                }
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean update(final Categoria entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection con = ConPool.getInstance().getConnection()) {
                try (PreparedStatement stmt =
                             con.prepareStatement("UPDATE categoria "
                                     + "SET nomeCategoria = ? "
                                     + "WHERE idCategoria = ?")) {
                    int index = 1;
                    stmt.setString(index++, entity.getNome());
                    stmt.setInt(index, entity.getIdCategoria());
                    return stmt.executeUpdate() > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean delete(final Categoria entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Null object");
        } else {
            try (Connection con = ConPool.getInstance().getConnection()) {
                try (PreparedStatement stmt =
                             con.prepareStatement("DELETE FROM categoria "
                                     + "WHERE idCategoria = ?")) {
                    stmt.setInt(1, entity.getIdCategoria());
                    return stmt.executeUpdate() > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException("SQL error: " + e.getMessage());
            }
        }
    }

    @Override
    public Categoria extract(final ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            Categoria c = new Categoria();

            c.setNome(resultSet.getString("nomeCategoria"));
            c.setIdCategoria(resultSet.getInt("idCategoria"));
            return c;
        } else {
            throw new IllegalArgumentException("Null object");
        }
    }
}
