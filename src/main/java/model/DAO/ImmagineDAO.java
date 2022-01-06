package model.DAO;

import model.beans.Immagine;
import model.persistence.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ImmagineDAO implements DAO<Immagine> {
    @Override
    public Immagine getById(final int id) {
        Immagine immagine = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM immagine WHERE idImmagine = ?";

                try (PreparedStatement preparedStatement =
                             connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        immagine = extract(resultSet, null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return immagine;
    }

    @Override
    public List<Immagine> getAll() {
        List<Immagine> immagineList = null;

        try (Connection connection = ConPool.getInstance().getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM immagine";
                immagineList = new ArrayList<>();

                try (PreparedStatement preparedStatement =
                             connection.prepareStatement(query)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        immagineList.add(extract(resultSet, null));
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return immagineList;
    }

    @Override
    public boolean save(final Immagine entity) {
        if (entity != null) {
            try (Connection connection =
                         ConPool.getInstance().getConnection()) {
                if (connection != null) {
                    String query = "INSERT";

                    try (PreparedStatement preparedStatement =
                                 connection.prepareStatement(query)) {
                        ResultSet resultSet = preparedStatement.executeQuery();


                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean update(final Immagine entity) {
        return false;
    }

    @Override
    public boolean delete(final Immagine entity) {
        return false;
    }

    @Override
    public Immagine extract(final ResultSet resultSet, final String alias) {
        return null;
    }
}
