package pa.lab9.cinema.jdbc;

import pa.lab9.cinema.factory.AbstractFactory;
import pa.lab9.cinema.jdbc.dao.GenreDao;
import pa.lab9.cinema.jdbc.dao.MovieDao;
import pa.lab9.cinema.jpa.entities.GenreEntity;
import pa.lab9.cinema.jpa.entities.MovieEntity;
import pa.lab9.cinema.repository.Repository;

public class FactoryJdbc implements AbstractFactory {
    @Override
    public Repository<MovieEntity> createMovieRepository() {
        return new MovieDao();
    }

    @Override
    public Repository<GenreEntity> createGenreRepository() {
        return new GenreDao();
    }
}
