package pa.lab9.jdbc;

import pa.lab9.factory.AbstractFactory;
import pa.lab9.jdbc.dao.GenreDao;
import pa.lab9.jdbc.dao.MovieDao;
import pa.lab9.jpa.entities.GenreEntity;
import pa.lab9.jpa.entities.MovieEntity;
import pa.lab9.repository.Repository;

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
