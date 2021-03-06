package service;

import java.util.List;

public interface IService<T> {
    List<T> findAll();
    T findById(Long id);
    void update(T model);
    void save(T Model);
    void remove(Long id);
}
