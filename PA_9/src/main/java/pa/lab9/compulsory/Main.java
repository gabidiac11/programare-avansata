package pa.lab9.compulsory;

import pa.lab9.factory.AbstractFactory;
import pa.lab9.jpa.entities.MovieEntity;
import pa.lab9.jpa.factory.FactoryJpa;
import pa.lab9.jpa.repository.MovieRepository;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    static Logger logger = Logger.getLogger(String.valueOf(Main.class));

    private static void testInsertMovie() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieId(String.format("1234567-%s", (int) new Date().getTime()));
        movieEntity.setTitle("Test movie #1");
        movieEntity.setDuration(200);
        movieEntity.setReleaseDate("1999-05-05");
        movieEntity.setScore(3);

        MovieRepository movieRepository = new MovieRepository();
        movieRepository.create(movieEntity);
    }

    private static void testSelectMovieById() {
        MovieRepository movieRepository = new MovieRepository();

        for(String id: new String[]{
                "tt0000009",
                "tt0002130",
                "tt0003657"
        }) {
            MovieEntity movieEntity = movieRepository.findById(id);

            logger.log(Level.INFO, movieEntity.toString());
        }
    }

    private static void testSelectMovieByName() {
        MovieRepository movieRepository = new MovieRepository();

        for(String name: new String[]{
                "The Story of the Kelly Gang",
                "Miss Jerry"
        }) {
            List<MovieEntity> movieEntityList = movieRepository.findByName(name);

            logger.log(Level.INFO, movieEntityList.stream().map(MovieEntity::toString).collect(Collectors.joining("\n")));
        }
    }

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);

        testSelectMovieById();
        testInsertMovie();
        testSelectMovieByName();
    }
}