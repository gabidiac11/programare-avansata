package pa.lab9.cinema.jdbc.dao;

import pa.lab9.cinema.jdbc.connection.Connection;
import pa.lab9.cinema.jpa.entities.GenreEntity;
import pa.lab9.cinema.repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDao implements Repository<GenreEntity> {
    private static final java.sql.Connection con = Connection.getInstance().getCon();

    public GenreEntity findById(String id) {
        try {
            ResultSet rs = con.createStatement()
                    .executeQuery(String.format("SELECT * FROM genre where genre_id=%d", id));

            /* if next() return false it means ResultSet is empty */
            if(rs.next()) {
                GenreEntity genreEntity = new GenreEntity();
                genreEntity.setGenreId(Integer.parseInt(id));
                genreEntity.setName(rs.getString("name"));

                return genreEntity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<GenreEntity> findByName(String name) {
        List<GenreEntity> genreList = new ArrayList<>();
        try {
            ResultSet rs = con.createStatement()
                    .executeQuery(String.format("SELECT * FROM genre where name='%s'", name));

            while (rs.next()) {
                GenreEntity genreEntity = new GenreEntity();
                genreEntity.setGenreId(Integer.parseInt(rs.getString("genre_id")));
                genreEntity.setName(rs.getString("name"));

                genreList.add(genreEntity);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return genreList;
    }

    public void create(GenreEntity genreEntity) {
        try {
            PreparedStatement preparedStmt  = con.prepareStatement(String.format("INSERT INTO genre(name) VALUES('%s')", genreEntity.getName()), Statement.RETURN_GENERATED_KEYS);
            preparedStmt.executeUpdate();

            ResultSet resultSet = preparedStmt.getGeneratedKeys();
            if(!resultSet.next()) {
                throw new SQLException("Creating movie failed, nothing inserted.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
