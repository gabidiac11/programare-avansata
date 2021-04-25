package pa.lab8.compulsory.cinema.controllers;

import pa.lab8.compulsory.cinema.models.Genre;

import pa.lab8.compulsory.cinema.connection.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreController {
    private static java.sql.Connection con = Connection.getInstance().getCon();

    public static Genre findById(int id) throws SQLException {
        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM genre where genre_id=%d", id));

        /* if next() return false it means ResultSet is empty */
        if(rs.next()) {
            return new Genre(id, rs.getString("name"));
        }

        return null;
    }

    public static List<Genre> findByName(String name) throws SQLException {
        List<Genre> genreList = new ArrayList<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM genre where name='%s'", name));

        while (rs.next()) {
            genreList.add(new Genre(
                    Integer.parseInt(rs.getString("genre_id")),
                    rs.getString("name"))
            );
        }

        return genreList;
    }

    public static Genre createGenre(String name) throws SQLException {
        PreparedStatement preparedStmt  = con.prepareStatement(String.format("INSERT INTO genre(name) VALUES('%s')", name), Statement.RETURN_GENERATED_KEYS);
        preparedStmt.executeUpdate();

        ResultSet resultSet = preparedStmt.getGeneratedKeys();
        if(!resultSet.next()) {
            throw new SQLException("Creating movie failed, nothing inserted.");
        }

        int genreId = resultSet.getInt(1);

        return findById(genreId);
    }
}
