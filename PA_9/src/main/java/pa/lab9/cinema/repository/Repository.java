package pa.lab9.cinema.repository;

import pa.lab9.cinema.chart.ChartType;

import java.util.List;

public interface Repository<T> {
    void create(T object);
    T findById(String id);
    List<T> findByName(String name);
    List<T> fetchOrderedBy(ChartType chartType);
}
