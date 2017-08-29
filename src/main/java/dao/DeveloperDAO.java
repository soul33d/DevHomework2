package dao;

import model.Developer;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDAO extends EntityDAO<Developer> {

    @Override
    public List<Developer> readAll() throws SQLException {
        List<Developer> developerList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM developers");
            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getInt("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setLastName(rs.getString("last_name"));
                developer.setSalary(rs.getBigDecimal("salary"));
                readAllRelationalIds(developer, connection);
                developerList.add(developer);
            }
        }
        return developerList;
    }

    @Override
    protected void readAllRelationalIds(Developer developer, Connection connection) throws SQLException {
        developer.setSkillsIds(readIds("SELECT skill_id FROM developers_skills WHERE developer_id = ?",
                developer.getId(), connection));
        developer.setCompaniesIds(readIds("SELECT company_id FROM companies_developers WHERE developer_id = ?",
                developer.getId(), connection));
        developer.setProjectsIds(readIds("SELECT project_id FROM projects_developers WHERE developer_id = ?",
                developer.getId(), connection));
    }

    @NotNull
    public Developer read(int id) throws SQLException {
        Developer developer = new Developer();
        developer.setId(id);
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM developers WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                developer.setFirstName(rs.getString("first_name"));
                developer.setLastName(rs.getString("last_name"));
                developer.setSalary(rs.getBigDecimal("salary"));
            }
            readAllRelationalIds(developer, connection);
            return developer;
        }
    }

    public void write(@NotNull Developer developer) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO developers (first_name, last_name, salary) VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setDouble(3, developer.getSalary().doubleValue());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating developer failed, no rows affected");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                developer.setId(generatedKeys.getInt(1));
            } else throw new SQLException("Creating developer failed, no id obtained");

            setRelationships(developer, connection);
        }
    }

    public void update(@NotNull Developer developer) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE developers SET first_name = ?, last_name = ?, salary = ? WHERE id = ?");
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setDouble(3, developer.getSalary().doubleValue());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating developer failed, no rows affected");
            }

            clearRelationships(developer.getId(), connection);
            setRelationships(developer, connection);
        }
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM developers WHERE id = ?";
    }

    @Override
    protected String deleteAllQuery() {
        return "DELETE FROM developers";
    }

    @Override
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM developers_skills WHERE developer_id = ?", id, connection);
        clearRelationships("DELETE FROM projects_developers WHERE developer_id = ?", id, connection);
        clearRelationships("DELETE FROM companies_developers WHERE developer_id = ?", id, connection);
    }

    @Override
    public void deleteAll() throws SQLException {

    }

    private void setRelationships(Developer developer, Connection connection) throws SQLException {
        setRelationships("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?)",
                developer.getId(), true, developer.getSkillsIds(), connection);
        setRelationships("INSERT INTO projects_developers (project_id, developer_id) VALUES (?, ?)",
                developer.getId(), false, developer.getProjectsIds(), connection);
        setRelationships("INSERT INTO companies_developers (company_id, developer_id) VALUES (?, ?)",
                developer.getId(), false, developer.getCompaniesIds(), connection);
    }
}
