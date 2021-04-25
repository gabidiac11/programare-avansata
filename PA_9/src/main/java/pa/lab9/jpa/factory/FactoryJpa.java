package pa.lab9.jpa.factory;

import pa.lab9.jpa.entities.GenreEntity;
import pa.lab9.jpa.entities.MovieEntity;
import pa.lab9.jpa.repository.GenreRepository;
import pa.lab9.jpa.repository.MovieRepository;
import pa.lab9.factory.AbstractFactory;
import pa.lab9.repository.Repository;

/**
 * provides repositories instances for the JPA implementation (intended to work as a option between JPA & JDBC)
 */
public class FactoryJpa implements AbstractFactory {
    private final static Repository<MovieEntity> movieRepository = new MovieRepository();
    private final static Repository<GenreEntity> genreEntityRepository = new GenreRepository();

    @Override
    public Repository<MovieEntity> createMovieRepository() {
        return movieRepository;
    }

    @Override
    public Repository<GenreEntity> createGenreRepository() {
        return genreEntityRepository;
    }
}
