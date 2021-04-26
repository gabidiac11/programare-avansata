package pa.lab9.factory;

import pa.lab9.jpa.entities.GenreEntity;
import pa.lab9.jpa.entities.MovieEntity;
import pa.lab9.jpa.repository.GenreRepository;
import pa.lab9.jpa.repository.MovieRepository;
import pa.lab9.repository.Repository;

/**
 * manage the same code for a implementation choice (jpa or jdbc)
 * creates repository for movie and genre tables (dao or jpa implementations)
 */
public interface AbstractFactory {
    Repository<MovieEntity> createMovieRepository();
    Repository<GenreEntity> createGenreRepository();
}
