package dao;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public interface EntityDAO<T> {
    T read(int id) throws SQLException;
    void write(@NotNull T t) throws SQLException;
    void update(@NotNull T t) throws SQLException;
}
