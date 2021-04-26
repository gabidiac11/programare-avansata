package pa.lab9.jpa.repository;

import pa.lab9.jpa.entities.GenreEntity;
import pa.lab9.repository.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * https://www.baeldung.com/hibernate-named-query
 *
 * - implements base class find by id, and find by name using the jpa queries
 */
public class GenreRepository extends AbstractRepository<GenreEntity> {
    @Override
    public GenreEntity findById(String id) {
        TypedQuery<GenreEntity> query = session.createNamedQuery("Genre_findById",
                GenreEntity.class);
        query.setParameter("genreId", Integer.parseInt(id));

        return query.getSingleResult();
    }

    @Override
    public List<GenreEntity> findByName(String name) {
        TypedQuery<GenreEntity> query = session.createNamedQuery("Genre_findByName",
                GenreEntity.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
