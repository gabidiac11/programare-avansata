package pa.lab9.factory;

import pa.lab9.repository.Repository;

/**
 * manage the same code for a implementation choice (jpa or jdbc)
 * creates repository for movie and genre tables (dao or jpa implementations)
 */
public interface AbstractFactory {
    Object createMovieRepository();
    Object createGenreRepository();
}
