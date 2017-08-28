package dao;

import model.Skill;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO implements EntityDAO<Skill> {

    @NotNull
    public Skill read(int id) throws SQLException {
        Skill skill = new Skill();
        skill.setId(id);
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM skills WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                skill.setName(rs.getString("name"));
            }
            skill.setDevelopersIds(readDevelopersIds(skill, connection));
        }
        return skill;
    }

    private List<Integer> readDevelopersIds(Skill skill, Connection connection) throws SQLException {
        List<Integer> developersIds = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT developer_id FROM developers_skills WHERE skill_id = ?");
        ps.setInt(1, skill.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            developersIds.add(rs.getInt(1));
        }
        return developersIds;
    }

    public void write(@NotNull Skill skill) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO skills (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, skill.getName());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating skill failed, no rows affected");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                skill.setId(generatedKeys.getInt(1));
            } else throw new SQLException("Creating skill failed, no id obtained");

            setSkillRelationShips(skill, connection);
        }
    }

    private void setSkillRelationShips(@NotNull Skill skill, Connection connection) throws SQLException {
        for (Integer developerId : skill.getDevelopersIds()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?)");
            ps.setInt(developerId, skill.getId());
        }
    }

    public void update(@NotNull Skill skill) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement
                    ("UPDATE skills SET name = ? WHERE id = ?");
            ps.setString(1, skill.getName());
            ps.setInt(2, skill.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update skill, no rows affected");
            }

            clearDevelopersRelationships(skill, connection);
            setSkillRelationShips(skill, connection);
        }
    }

    private void clearDevelopersRelationships(@NotNull Skill skill, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE * FROM developers_skills WHERE skill_id = ?");
        ps.setInt(1, skill.getId());
        ps.executeUpdate();
    }
}
