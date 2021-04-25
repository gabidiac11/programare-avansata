package pa.lab8.compulsory.cinema.controllers;

import pa.lab8.compulsory.cinema.connection.Connection;
import pa.lab8.compulsory.cinema.models.Genre;
import pa.lab8.compulsory.cinema.models.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieController {
    private static java.sql.Connection con = Connection.getInstance().getCon();

    public static Movie findById(int id) throws SQLException {
        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM movie where movie_id = %d", id));

        /* if next() return false it means ResultSet is empty */
        if(rs.next()) {
            return new Movie(
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

    public static List<Movie> findByName(int name) throws SQLException {
        List<Movie> movieList = new ArrayList<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format("SELECT * FROM movie where title='%s'", name));

        while (rs.next()) {
            final int ID = Integer.parseInt(rs.getString("movie_id"));

            movieList.add(new Movie(
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
    public static Movie createMovie(String title, String releaseDate, int duration, int score, List<Genre> genreList) throws SQLException {
        int id = insertMovie(title, releaseDate, duration, score);
        associateGenreList(id, genreList);

        return findById(id);
    }

    public static int insertMovie(
            String title, String releaseDate, int duration, int score
    ) throws SQLException {
        PreparedStatement preparedStmt = con.prepareStatement(
                String.format("INSERT INTO movie(title, release_date, duration, score) VALUES ('%s', '%s', %d, %d)",
                        title,
                        releaseDate,
                        duration,
                        score

                ), Statement.RETURN_GENERATED_KEYS);

        preparedStmt.executeUpdate();

        ResultSet resultSet = preparedStmt.getGeneratedKeys();

        if (!resultSet.next()) {
            throw new SQLException("Creating movie failed, nothing inserted.");
        }


        return resultSet.getInt(1);
    }

    public static Movie createMovie(
            String title, String releaseDate, int duration, int score,
            Genre genre
    ) throws SQLException {
        int id = insertMovie(title, releaseDate, duration, score);
        associateGenreById(id, genre.getId());

        return findById(id);
    }

    private static void associateGenreList(int movieId, List<Genre> genreList) throws SQLException {
        for(Genre genre : genreList) {
            associateGenreById(movieId, genre.getId());
        }
    }

    private static void associateGenreById(int movieId, int genreId) throws SQLException {
        PreparedStatement preparedStmt  = con.prepareStatement(
                String.format(
                        "INSERT INTO movie_genre(movie_id, genre_id) VALUES (%d, %d)",
                        movieId,
                        genreId
                ), Statement.RETURN_GENERATED_KEYS);

        preparedStmt.executeUpdate();
    }

    public static List<Genre> getGenreListOfMovieById(int movieId) throws SQLException {
        List<Genre> genreList = new ArrayList<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format(
                        "SELECT " +
                            "movie_genre.*, " +
                            "genre.name " +
                        "FROM movie_genre " +
                            "INNER JOIN genre " +
                            "ON genre.genre_id = movie_genre.genre_id " +

                        "where movie_genre.movie_id = %d",

                        movieId));

        while (rs.next()) {
            genreList.add(new Genre(
                    Integer.parseInt(rs.getString("genre_id")),
                    rs.getString("name"))
            );
        }

        return genreList;
    }
}
