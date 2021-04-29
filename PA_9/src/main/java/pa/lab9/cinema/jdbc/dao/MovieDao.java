package pa.lab9.cinema.jdbc.dao;

import pa.lab9.cinema.chart.ChartType;
import pa.lab9.cinema.jdbc.connection.Connection;
import pa.lab9.cinema.jpa.entities.GenreEntity;
import pa.lab9.cinema.jpa.entities.MovieEntity;
import pa.lab9.cinema.jpa.entities.PersonsEntity;
import pa.lab9.cinema.repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieDao implements Repository<MovieEntity> {
    private static final java.sql.Connection con = Connection.getInstance().getCon();

    public MovieEntity findById(String id)  {
        try {
            ResultSet rs = con.createStatement()
                    .executeQuery(String.format("SELECT * FROM movie where movie_id = '%s'", id));

            /* if next() return false it means ResultSet is empty */
            if(rs.next()) {
                return createMovieEntityFromResult(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<MovieEntity> findByName(String name) {
        List<MovieEntity> movieList = new ArrayList<>();
        try {
            ResultSet rs = con.createStatement()
                    .executeQuery(String.format("SELECT * FROM movie where title='%s'", name));

            while (rs.next()) {
                movieList.add(createMovieEntityFromResult(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return movieList;
    }

    public void create(MovieEntity movieEntity) {
        try {
            insertMovie(movieEntity);
            associateGenreList(movieEntity.getMovieId(), movieEntity.getGenreEntities());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //====================================================================
    private static void insertMovie(MovieEntity movieEntity) throws SQLException {
        PreparedStatement preparedStmt = con.prepareStatement(
                String.format("INSERT INTO movie(movie_id, title, release_date, duration, score) VALUES ('%s', '%s', '%s', %d, %d)",
                        movieEntity.getMovieId(),
                        movieEntity.getTitle(),
                        movieEntity.getReleaseDate(),
                        movieEntity.getDuration(),
                        movieEntity.getScore()

                ), Statement.RETURN_GENERATED_KEYS);

        preparedStmt.executeUpdate();
    }

    private static void associateGenreList(String movieId, Set<GenreEntity> genreList) throws SQLException {
        for(GenreEntity genre : genreList) {
            associateGenreById(movieId, genre.getGenreId());
        }
    }

    private static void associateGenreById(String movieId, int genreId) throws SQLException {
        PreparedStatement preparedStmt  = con.prepareStatement(
                String.format(
                        "INSERT INTO movie_genre(MovieEntity_movie_id, genreEntities_genre_id ) VALUES ('%s', '%d')",
                        movieId,
                        genreId
                ), Statement.RETURN_GENERATED_KEYS);

        preparedStmt.executeUpdate();
    }

    /**
     * retrieve genres of a movie
     * @param movieId
     * @return
     * @throws SQLException
     */
    private static Set<GenreEntity> getGenreListOfMovieById(String movieId) throws SQLException {
        Set<GenreEntity> genreList = new HashSet<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format(
                        "SELECT " +
                                "persons.*" +
                                "FROM movie_persons " +
                                "INNER JOIN persons " +
                                "ON movie_persons.persons_imdb_name_id = persons.imdb_name_id " +
                                "where movie_genre.MovieEntity_movie_id = '%s'",
                        movieId));

        while (rs.next()) {
            GenreEntity genreEntity = new GenreEntity();
            genreEntity.setGenreId(Integer.parseInt(rs.getString("genre_id")));
            genreEntity.setName(rs.getString("name"));

            genreList.add(genreEntity);
        }

        return genreList;
    }

    private static Set<PersonsEntity> getPersonListOfMovieById(String movieId) throws SQLException {
        Set<PersonsEntity> personsEntities = new HashSet<>();

        ResultSet rs = con.createStatement()
                .executeQuery(String.format(
                        "SELECT " +
                                "genre.name, genre.genre_id " +
                                "FROM movie_genre " +
                                "INNER JOIN genre " +
                                "ON genre.genre_id = movie_genre.genreEntities_genre_id " +
                                "where movie_genre.MovieEntity_movie_id = '%s'",

                        movieId));

        while (rs.next()) {
            personsEntities.add(personEntityFromResult(rs));
        }

        return personsEntities;
    }

    private static PersonsEntity personEntityFromResult(ResultSet rs) throws SQLException {
        PersonsEntity personsEntity = new PersonsEntity();
        personsEntity.setImdbNameId(rs.getString("imdb_name_id"));
        personsEntity.setName(rs.getString("name"));
        personsEntity.setBirthDetails(rs.getString("birth_details"));
        personsEntity.setDateOfBirth(rs.getString("date_of_birth"));
        personsEntity.setDeathDetails(rs.getString("death_details"));

        return personsEntity;
    }



    private static MovieEntity createMovieEntityFromResult(ResultSet rs) throws SQLException {
        final String movieId = rs.getString("movie_id");

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieId(movieId);
        movieEntity.setTitle(rs.getString("title"));
        movieEntity.setReleaseDate(rs.getString("release_date"));
        movieEntity.setDuration(Integer.parseInt(rs.getString("duration")));
        movieEntity.setScore(Integer.parseInt(rs.getString("score")));
        movieEntity.setGenreEntities(getGenreListOfMovieById(movieId));
        movieEntity.setPersons(getPersonListOfMovieById(movieId));

        return movieEntity;
    }

    private static String orderByBasedOnChartType(ChartType chartType) {
        switch (chartType) {
            case BY_RATING:
                return "score";
            case BY_RELEASE_DATE:
                return "release_date";

            default:
                return "movie_id";
        }
    }

    @Override
    public List<MovieEntity> fetchOrderedBy(ChartType chartType) {
        List<MovieEntity> movieList = new ArrayList<>();
        try {
            ResultSet rs = con.createStatement()
                    .executeQuery(String.format("SELECT * FROM movie ORDER BY %s LIMIT 100", orderByBasedOnChartType(chartType)));

            while (rs.next()) {
                movieList.add(createMovieEntityFromResult(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return movieList;
    }
}
