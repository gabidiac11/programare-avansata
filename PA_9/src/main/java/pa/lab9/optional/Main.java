package pa.lab9.optional;

import pa.lab9.cinema.factory.AbstractFactory;
import pa.lab9.cinema.factory.FactoryProvider;
import pa.lab9.cinema.jpa.entities.GenreEntity;
import pa.lab9.cinema.jpa.entities.MovieEntity;
import pa.lab9.cinema.repository.Repository;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    static Logger logger = Logger.getLogger(String.valueOf(pa.lab9.compulsory.Main.class));

    private static AbstractFactory factory;
    private static Repository<MovieEntity> movieRepository;
    private static Repository<GenreEntity> genreEntityRepository;

    static {
        try {
            establishFactory();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(0);
        }
    }

    private static void establishFactory() throws Exception {
        factory = FactoryProvider.getConfiguredFactory();
        movieRepository = factory.createMovieRepository();
        genreEntityRepository = factory.createGenreRepository();
    }

    private static void testInsertMovie() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieId(String.format("1234567-%s", (int) new Date().getTime()));
        movieEntity.setTitle("Test movie #1");
        movieEntity.setDuration(200);
        movieEntity.setReleaseDate("1999-05-05");
        movieEntity.setScore(3);

        factory.createMovieRepository().create(movieEntity);
    }

    private static void testSelectMovieById() {
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

        //testInsertMovie();
        testSelectMovieById();
        testSelectMovieByName();
    }
}
