package pa.lab9.cinema.repository;

import java.util.List;

public interface Repository<T> {
    void create(T object);
    T findById(String id);
    List<T> findByName(String name);
}
