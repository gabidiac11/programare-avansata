package pa.lab9.jpa.repository;

import pa.lab9.jpa.entities.MovieEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * https://www.baeldung.com/hibernate-named-query
 *
 * - implements base class find by id, and find by name using the jpa queries
 */
public class MovieRepository extends AbstractRepository<MovieEntity> {
    @Override
    public MovieEntity findById(String id) {
        TypedQuery<MovieEntity> query = session.createNamedQuery("Movie_findById",
                MovieEntity.class);
        query.setParameter("movieId", id);
        return query.getSingleResult();
    }

    @Override
    public List<MovieEntity> findByName(String name) {
        TypedQuery<MovieEntity> query = session.createNamedQuery("Movie_findByName",
                MovieEntity.class);
        query.setParameter("title", name);
        return query.getResultList();
    }
}
