package dao;

import model.Developer;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDAO implements EntityDAO<Developer> {

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
            readSkills(developer, connection);
            readCompanies(developer, connection);
            readProjects(developer, connection);
            return developer;
        }
    }

    private void readSkills(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("SELECT skill_id FROM developers_skills WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ResultSet rs = ps.executeQuery();
        List<Integer> skillsIds = new ArrayList<>();
        while (rs.next()) {
            skillsIds.add(rs.getInt(1));
        }
        developer.setSkillsIds(skillsIds);
    }

    private void readCompanies(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("SELECT company_id FROM companies_developers WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ResultSet rs = ps.executeQuery();
        List<Integer> companiesIds = new ArrayList<>();
        while (rs.next()) {
            companiesIds.add(rs.getInt(1));
        }
        developer.setCompaniesIds(companiesIds);
    }

    private void readProjects(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("SELECT project_id FROM projects_developers WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ResultSet rs = ps.executeQuery();
        List<Integer> projectsIds = new ArrayList<>();
        while (rs.next()) {
            projectsIds.add(rs.getInt(1));
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

            setDeveloperRelationships(developer, connection);
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

            clearSkillsRelationships(developer, connection);
            clearProjectsRelationships(developer, connection);
            clearCompaniesRelationships(developer, connection);

            setDeveloperRelationships(developer, connection);
        }

    }

    private void setDeveloperRelationships(Developer developer, Connection connection) throws SQLException {
        for (Integer skillId : developer.getSkillsIds()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?)");
            ps.setInt(1, developer.getId());
            ps.setInt(2, skillId);
            ps.executeUpdate();
        }

        for (Integer projectId : developer.getProjectsIds()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO projects_developers (project_id, developer_id) VALUES (?, ?)");
            ps.setInt(1, projectId);
            ps.setInt(2, developer.getId());
            ps.executeUpdate();
        }

        for (Integer companyId : developer.getCompaniesIds()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO companies_developers (company_id, developer_id) VALUES (?, ?)");
            ps.setInt(1, companyId);
            ps.setInt(2, developer.getId());
            ps.executeUpdate();
        }
    }

    private void clearSkillsRelationships(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("DELETE * FROM developers_skills WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ps.executeUpdate();
    }

    private void clearProjectsRelationships(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("DELETE * FROM projects_developers WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ps.executeUpdate();
    }

    private void clearCompaniesRelationships(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("DELETE  * FROM companies_developers WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ps.executeUpdate();
    }
}
