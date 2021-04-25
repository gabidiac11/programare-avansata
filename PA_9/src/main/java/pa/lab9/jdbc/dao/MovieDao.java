package pa.lab9.jdbc.dao;

import pa.lab9.jdbc.connection.Connection;
import pa.lab9.jdbc.models.GenreModel;
import pa.lab9.jdbc.models.MovieModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDao {
    private static java.sql.Connection con = Connection.getInstance().getCon();

    public static MovieModel findById(String id) throws SQLException {
        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM movie where movie_id = '%s'", id));

        /* if next() return false it means ResultSet is empty */
        if(rs.next()) {
            return new MovieModel(
                    id,
                    rs.getString("title"),
                    rs.getString("release_date"),
                    Integer.parseInt(rs.getString("duration")),
                    Integer.parseInt(rs.getString("score")),
                    getGenreListOfMovieById(id)
            );
        }

        return null;
    }

    public static List<MovieModel> findByName(int name) throws SQLException {
        List<MovieModel> movieList = new ArrayList<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM movie where title='%s'", name));

        while (rs.next()) {
            final String ID = rs.getString("movie_id");

            movieList.add(new MovieModel(
                    ID,
                    rs.getString("title"),
                    rs.getString("releaseDate"),
                    Integer.parseInt(rs.getString("duration")),
                    Integer.parseInt(rs.getString("score")),
                    getGenreListOfMovieById(ID)
            ));
        }

        return movieList;
    }


    /**
     * creates a db record of a movie
     *
     * @param title
     * @param releaseDate
     * @param duration
     * @param score
     * @throws SQLException
     */
    public static MovieModel createMovie(String id, String title, String releaseDate, int duration, int score, List<GenreModel> genreList) throws SQLException {
        insertMovie(id, title, releaseDate, duration, score);
        associateGenreList(id, genreList);

        return findById(id);
    }


    public static void insertMovie(
            String id, String title, String releaseDate, int duration, int score
    ) throws SQLException {
        PreparedStatement preparedStmt = con.prepareStatement(
                String.format("INSERT INTO movie(movie_id, title, release_date, duration, score) VALUES ('%s', '%s', '%s', %d, %d)",
                        id,
                        title,
                        releaseDate,
                        duration,
                        score

                ), Statement.RETURN_GENERATED_KEYS);

        preparedStmt.executeUpdate();
    }

    public static MovieModel createMovie(
            String id, String title, String releaseDate, int duration, int score,
            GenreModel genre
    ) throws SQLException {
        insertMovie(id, title, releaseDate, duration, score);
        associateGenreById(id, genre.getId());

        return findById(id);
    }

    private static void associateGenreList(String movieId, List<GenreModel> genreList) throws SQLException {
        for(GenreModel genre : genreList) {
            associateGenreById(movieId, genre.getId());
        }
    }

    private static void associateGenreById(String movieId, int genreId) throws SQLException {
        PreparedStatement preparedStmt  = con.prepareStatement(
                String.format(
                        "INSERT INTO movie_genre(movie_id, genre_id) VALUES ('%s', '%d')",
                        movieId,
                        genreId
                ), Statement.RETURN_GENERATED_KEYS);

        preparedStmt.executeUpdate();
    }

    public static List<GenreModel> getGenreListOfMovieById(String movieId) throws SQLException {
        List<GenreModel> genreList = new ArrayList<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format(
                        "SELECT " +
                                "movie_genre.*, " +
                                "genre.name " +
                                "FROM movie_genre " +
                                "INNER JOIN genre " +
                                "ON genre.genre_id = movie_genre.genre_id " +

                                "where movie_genre.movie_id = '%s'",

                        movieId));

        while (rs.next()) {
            genreList.add(new GenreModel(
                    Integer.parseInt(rs.getString("genre_id")),
                    rs.getString("name"))
            );
        }

        return genreList;
    }
}
