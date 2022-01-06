package model.DAO;

import model.beans.Categoria;
import model.beans.FAQ;
import model.beans.Utente;
import model.persistence.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CategoriaDAO implements DAO<Categoria> {

    /**
     * @param id rappresenta l'identificativo dell'entity.
     * @return l'oggetto categoria dal db.
     */
    @Override
    public Categoria getById(final int id) {
        Categoria c = null;
        try (Connection con = ConPool.getInstance().getConnection()){
            if(con != null){
                try(PreparedStatement stmt = con.prepareStatement("SELECT * FROM categoria WHERE idCategoria = ?")){
                    stmt.setInt(1, id);
                    ResultSet resultSet = stmt.executeQuery();
                    while(resultSet.next()){
                        c = extract(resultSet, null);

                    }
                }

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    /**
     * @return la lista di tutte le categorie nel db.
     */
    @Override
    public List<Categoria> getAll() {
        List<Categoria> cList = null;
        try(Connection con = ConPool.getInstance().getConnection()){
            if(con != null){
                cList = new ArrayList<>();

                try(PreparedStatement stmt = con.prepareStatement("SELECT * FROM categoria")){
                    ResultSet resultSet = stmt.executeQuery();
                    while (resultSet.next()){
                        cList.add(extract(resultSet, null));
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cList;
    }

    @Override
    public boolean save(final Categoria entity) {
        if(entity != null){
            try(Connection con = ConPool.getInstance().getConnection()){
                if(con != null){
                    try(PreparedStatement stmt = con.prepareStatement("INSERT INTO "))
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(Categoria entity) {
        return false;
    }

    @Override
    public boolean delete(Categoria entity) {
        return false;
    }

    @Override
    public Categoria extract(ResultSet resultSet, String alias) throws SQLException {
        if(resultSet != null) {
            Categoria c = new Categoria();

            if(!alias.isEmpty())
                alias+=".";

            c.setNome(resultSet.getString(alias+"nomeCategoria"));
            c.setIdCategoria(resultSet.getInt(alias+"idCategoria"));
            return c;
        }
        return null;
    }
}
