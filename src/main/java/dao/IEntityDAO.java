package dao;

import java.sql.SQLException;
import java.util.Set;

public interface IEntityDAO<T> {
    Set<T> readAll() throws SQLException;

    T read(int id) throws SQLException;

    void write(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(int id) throws SQLException;

    void deleteAll() throws SQLException;
}
