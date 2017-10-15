package dao;

import java.util.Set;

public interface IEntityDAO<T> {
    Set<T> readAll() throws Exception;

    T read(int id) throws Exception;

    void write(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(int id) throws Exception;

    void deleteAll() throws Exception;
}
