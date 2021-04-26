package pa.lab9.cinema.factory;

import pa.lab9.cinema.jpa.entities.GenreEntity;
import pa.lab9.cinema.jpa.entities.MovieEntity;
import pa.lab9.cinema.repository.Repository;

/**
 * manage the same code for a implementation choice (jpa or jdbc)
 * creates repository for movie and genre tables (dao or jpa implementations)
 */
public interface AbstractFactory {
    Repository<MovieEntity> createMovieRepository();
    Repository<GenreEntity> createGenreRepository();
}
