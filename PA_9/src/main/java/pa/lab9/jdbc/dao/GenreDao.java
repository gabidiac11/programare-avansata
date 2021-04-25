package pa.lab9.jdbc.dao;

import pa.lab9.jdbc.connection.Connection;
import pa.lab9.jdbc.models.GenreModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {
    private static java.sql.Connection con = Connection.getInstance().getCon();

    public static GenreModel findById(int id) throws SQLException {
        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM genre where genre_id=%d", id));

        /* if next() return false it means ResultSet is empty */
        if(rs.next()) {
            return new GenreModel(id, rs.getString("name"));
        }

        return null;
    }

    public static List<GenreModel> findByName(String name) throws SQLException {
        List<GenreModel> genreList = new ArrayList<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM genre where name='%s'", name));

        while (rs.next()) {
            genreList.add(new GenreModel(
                    Integer.parseInt(rs.getString("genre_id")),
                    rs.getString("name"))
            );
        }

        return genreList;
    }

    public static GenreModel createGenre(String name) throws SQLException {
        PreparedStatement preparedStmt  = con.prepareStatement(String.format("INSERT INTO genre(name) VALUES('%s')", name), Statement.RETURN_GENERATED_KEYS);
        preparedStmt.executeUpdate();

        ResultSet resultSet = preparedStmt.getGeneratedKeys();
        if(!resultSet.next()) {
            throw new SQLException("Creating movie failed, nothing inserted.");
        }

        return findById(Integer.parseInt(resultSet.getString(1)));
    }
}
