package pa.lab9.cinema.jpa.repository;

import pa.lab9.cinema.jpa.factory.FactoryManager;
import pa.lab9.cinema.repository.Repository;

import javax.persistence.EntityManager;

/**
 * base class for repository:
 * - provides the universal method of creating a table record from a entity
 * - provides the session
 *
 * @param <T> - one of the entity (Movie, Genre)
 */
public abstract class AbstractRepository <T> implements Repository<T> {
    protected EntityManager session;

    AbstractRepository() {
        session = FactoryManager.getInstance().getSession();
    }

    @Override
    public void create(T object) {
        session.getTransaction().begin();
        session.persist(object);
        session.getTransaction().commit();
    }
}
