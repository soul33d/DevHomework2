package dao;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityDAO<T> {
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
}
