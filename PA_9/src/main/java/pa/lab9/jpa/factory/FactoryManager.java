package pa.lab9.jpa.factory;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * a singleton that provides the session that is used by the repositories that extend AbstractRepository
 */
public class FactoryManager {
    private static FactoryManager instance;
    @Getter
    public EntityManager session;
    private EntityManagerFactory entityManagerFactory;

    private FactoryManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        session = entityManagerFactory.createEntityManager();
    }

    public static FactoryManager getInstance() {
        if(instance == null) {
            instance = new FactoryManager();
        }
        return instance;
    }
}
