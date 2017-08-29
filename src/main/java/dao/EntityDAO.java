package dao;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityDAO<T> {

    private static final String FAILED_TO_DELETE_MSG = "Failed to delete, no rows affected";

    public abstract List<T> readAll() throws SQLException;

    protected abstract void readAllRelationalIds(T t, Connection connection) throws SQLException;

    @NotNull
    public abstract T read(int id) throws SQLException;

    protected List<Integer> readIds(String sql, int id, Connection connection) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt(1));
        }
        return ids;
    }

    public abstract void write(@NotNull T t) throws SQLException;

    protected void setRelationships
            (String sql, int id, boolean entityIdFirst, List<Integer> ids, Connection connection) throws SQLException {
        for (Integer relationId : ids) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, entityIdFirst ? id : relationId);
            ps.setInt(2, entityIdFirst ? relationId : id);
            ps.executeUpdate();
        }
    }

    public abstract void update(@NotNull T t) throws SQLException;

    protected void clearRelationships(String sql, int id, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(deleteQuery());
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(FAILED_TO_DELETE_MSG);
            }
        }
    }

    protected abstract String deleteQuery();

    protected abstract String deleteAllQuery();

    protected abstract void clearRelationships(int id, Connection connection) throws SQLException;

    public void deleteAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(deleteAllQuery());
            if (affectedRows == 0) {
                throw new SQLException(FAILED_TO_DELETE_MSG);
            }

        }
    }
}
