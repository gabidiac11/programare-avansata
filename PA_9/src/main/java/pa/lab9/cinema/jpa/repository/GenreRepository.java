package pa.lab9.cinema.jpa.repository;

import pa.lab9.cinema.chart.ChartType;
import pa.lab9.cinema.jpa.entities.GenreEntity;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
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

    @Override
    public List<GenreEntity> fetchOrderedBy(ChartType chartType) {
        return new ArrayList<>();
    }
}
