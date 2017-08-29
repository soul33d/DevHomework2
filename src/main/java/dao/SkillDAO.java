package dao;

import model.Skill;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO extends EntityDAO<Skill> {

    @Override
    public List<Skill> readAll() throws SQLException {
        List<Skill> skillList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM skills");
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getInt("id"));
                skill.setName(rs.getString("name"));
            }
        }
        return skillList;
    }

    @Override
    protected void readAllRelationalIds(Skill skill, Connection connection) throws SQLException {
        skill.setDevelopersIds(readIds("SELECT developer_id FROM developers_skills WHERE skill_id = ?",
                skill.getId(), connection));
    }

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
            readAllRelationalIds(skill, connection);
        }
        return skill;
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

            setRelationships(skill, connection);
        }
    }

    private void setRelationships(@NotNull Skill skill, Connection connection) throws SQLException {
        setRelationships("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?)", skill.getId(),
                false, skill.getDevelopersIds(), connection);
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

            clearRelationships(skill.getId(), connection);
            setRelationships(skill, connection);
        }
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM skills WHERE id = ?";
    }

    @Override
    protected String deleteAllQuery() {
        return "DELETE FROM skills";
    }

    @Override
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM developers_skills WHERE skill_id = ?", id, connection);
    }

    @Override
    public void deleteAll() throws SQLException {

    }
}
