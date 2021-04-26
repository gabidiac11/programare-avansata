package pa.lab9.cinema.jpa.factory;

import pa.lab9.cinema.jpa.entities.GenreEntity;
import pa.lab9.cinema.jpa.entities.MovieEntity;
import pa.lab9.cinema.jpa.repository.GenreRepository;
import pa.lab9.cinema.jpa.repository.MovieRepository;
import pa.lab9.cinema.factory.AbstractFactory;
import pa.lab9.cinema.repository.Repository;

/**
 * provides repositories instances for the JPA implementation (intended to work as a option between JPA & JDBC)
 */
public class FactoryJpa implements AbstractFactory {
    @Override
    public Repository<MovieEntity> createMovieRepository() {
        return new MovieRepository();
    }

    @Override
    public Repository<GenreEntity> createGenreRepository() {
        return new GenreRepository();
    }
}
